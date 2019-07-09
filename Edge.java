public class Edge {
	private int u;
	private int v;
	private double weight;
	
	public Edge(int u, int v, double weight) {
		this.u = u;
		this.v = v;
		this.weight = weight;
	}

	public int getU() {
		return u;
	}

	public int getV() {
		return v;
	}
	
	public double getWeight() {
		return weight;
	}

	@Override
	public boolean equals(Object obj) {
		Edge other = (Edge)obj;
		if(this.u == other.getU() && this.v == other.getV()){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "("+this.u+","+this.v+"|"+this.weight+") ";
	}
}
