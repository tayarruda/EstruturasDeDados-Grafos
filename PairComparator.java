import java.util.Comparator;

public class PairComparator implements Comparator<Pair>
{
    @Override
    public int compare(Pair x, Pair y)
    {
        if (x.distance < y.distance){
            return -1;
        }else if (x.distance > y.distance){
            return 1;
        }else{
        	return 0;
		}
    }
}
