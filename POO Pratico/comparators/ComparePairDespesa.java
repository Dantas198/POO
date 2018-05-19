package comparators;

import javafx.util.Pair;
import java.util.Comparator;

public class ComparePairDespesa implements Comparator<Pair<Integer,Float>>
{
    public int compare(Pair<Integer,Float> p1, Pair<Integer,Float> p2){
       float d1 =(float) p1.getValue();
       float d2 =(float) p2.getValue();
       
       if(d1==d2) return 0;
       if(d1>d2) return -1;
       return 1;
    }
}
