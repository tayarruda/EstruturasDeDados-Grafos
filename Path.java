import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.LinkedList;

public class Path {
 
  Graph graph;
  int start;
  int end;
  double[] dist;
  int[] prev;
  PriorityQueue<Pair> queue;
  Comparator<Pair> comparator;

  public void Path(Graph graph){
    this.graph = graph;   
  }

	public void setGraph(Graph graph){
	    this.graph = graph;
	}

  private void init(){
    dist = new double[graph.nVertices];
    prev = new int[graph.nVertices];

	comparator = new PairComparator();
	queue = new PriorityQueue<Pair>(graph.nVertices, comparator);

	for(int i=0; i<graph.nVertices; i++){
		dist[i] = Double.MAX_VALUE;
		prev[i] = -1;
	}
  }

  public double findDistance(int start, int end){
    this.start = start;
    this.end = end;
    init();	
    return run();
  }

 	public void uptadePair(Pair pair){
		Iterator<Pair> it = queue.iterator();

		Pair temp = it.next();
		while(temp.label != pair.label){
			temp = it.next();
		}
		queue.remove(temp);
		queue.add(pair);
	}

  private double run(){
	dist[start] = 0;
	for(int i=0; i<graph.nVertices; i++){
		queue.add(new Pair(i,dist[i]));
	}

	while(queue.size()!=0){
		Pair pair = queue.poll();
		int u = pair.label;
		LinkedList<Edge> neighbours = graph.getNeighbours(u);

		for(Edge e : neighbours){
			int v = e.getV();
			double aux = dist[u] + e.getWeight();
			if(aux < dist[v]){
				dist[v] = aux;
				prev[v] = u;
				uptadePair(new Pair(v, dist[v]));
			}			
		}
	}
	return dist[end];
  }
}
