package Comparators;

import javafx.util.Pair;
import java.util.Comparator;

public class ComparePairDespesa implements Comparator<Pair>
{
    public int compare(Pair p1, Pair p2){
       float d1 =(float) p1.getValue();
       float d2 =(float) p2.getValue();
       
        
       if(d1==d2) return 0;
       if(d1>d2) return -1;
       return 1;
       
    }
}
