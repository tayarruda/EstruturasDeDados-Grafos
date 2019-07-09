import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;

public class GraphicsUtils extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage bimg;
	private JFrame frame;
	private int oldVertice;

	private	ArrayList<ColoredPoint2D> nodes;
	private ConcurrentHashMap<String,ColoredSegment2D> edgesMap;

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if (this.bimg != null) {
			displayImage(g2d, this.bimg, 0, 0);
		}
		
		for(ColoredSegment2D e : edgesMap.values()){
			g2d.setColor(e.color);
			g2d.draw(new Line2D.Double(e.x1+2, e.y1+2, e.x2+2, e.y2+2));
		}

		int i = 0;
		for(ColoredPoint2D point : nodes){
			g2d.setColor(point.color);
			g2d.fill(new Ellipse2D.Double(point.x, point.y, 6, 6));
			g2d.setFont(g2d.getFont().deriveFont(20f));
		    g2d.drawString(Integer.toString(i++), (int)point.x-5, (int)point.y-5);
		}	
	}

	public void displayImage (Graphics2D g, BufferedImage bimg, int x, int y) {
		g.drawImage(bimg, null, x, y);
	}

	public static BufferedImage loadImage(String fileName) {
		BufferedImage bimg = null;  
		try {bimg = ImageIO.read(new File(fileName));} catch (Exception e) {e.printStackTrace();}
		return bimg;  
	} 

	public void setNodeColor(int index, Color newColor){
		nodes.get(index).color = newColor;
		ColoredSegment2D e;
		if(oldVertice <= index){
			e = edgesMap.get(oldVertice+","+index);
		}else{
			e = edgesMap.get(index+","+oldVertice);
		}

		if(e != null){
			e.color = newColor;
		}
		oldVertice = index;
		this.repaint();
	}

	public void addNode(int x, int y){
		nodes.add(new ColoredPoint2D(x, y, Color.BLACK));
		this.repaint();
	}

	public void addSegment(int vertice1, int vertice2, int x1, int y1, int x2, int y2){
		if(vertice1 <= vertice2){
			if(!edgesMap.containsKey(vertice1+","+vertice2)){
				edgesMap.put(vertice1+","+vertice2, new ColoredSegment2D(x1, y1, x2, y2, 									Color.BLACK));
			}
		}else{
			if(!edgesMap.containsKey(vertice2+","+vertice1)){
				edgesMap.put(vertice2+","+vertice1, new ColoredSegment2D(x1, y1, x2, y2, 									Color.BLACK));
			}
		}
	}

	public ArrayList<ColoredPoint2D> getNodes(){
		return nodes;
	}

	public ConcurrentHashMap<String,ColoredSegment2D> getEdgesMap(){
		return edgesMap;
	}

	public GraphicsUtils(String imageName, String title) {
		nodes = new ArrayList<ColoredPoint2D>();
		edgesMap = new ConcurrentHashMap<String,ColoredSegment2D>();
		oldVertice = 0;

		this.bimg = loadImage(imageName);
		int w = bimg.getWidth();
		int h = bimg.getHeight();

		frame = new JFrame(title);
		frame.add(this);
		frame.setSize(w, h+50);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public GraphicsUtils(GraphicsUtils otherGraphicsUtils, String imageName, String title) {
		nodes = otherGraphicsUtils.getNodes();
		edgesMap = otherGraphicsUtils.getEdgesMap();
		oldVertice = 0;

		this.bimg = loadImage(imageName);
		int w = bimg.getWidth();
		int h = bimg.getHeight();

		frame = new JFrame(title);
		frame.add(this);
		frame.setSize(w, h+50);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		for(ColoredPoint2D node : nodes){
			node.color = Color.BLACK;
		}

		Iterator it = edgesMap.entrySet().iterator();
    	while (it.hasNext()) {
       		ConcurrentHashMap.Entry pair = (ConcurrentHashMap.Entry)it.next();
        	((ColoredSegment2D)(pair.getValue())).color = Color.BLACK;        
	    }
	}
}
