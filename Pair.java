public class Pair{
	public int label;
	public double distance;

	public Pair(int label, double distance){
		this.label = label;
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "("+this.label+"|"+this.distance+")";
	}
}
