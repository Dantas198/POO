package comparators;

import javafx.util.Pair;
import java.util.Comparator;

public class ComparePairDespesa implements Comparator<Pair<Integer,Float>>
{   
    /**
     * @param p1, primeiro par
     * @param p2, segundo par
     * Compara dois pares de acordo com a despesa contida, Pair<nif, despesa>
     * Queremos uma ordenacao inversa
     * @returns 0, caso tenham a mesma data
     * @returns -1, caso f1 > f2
     * @returns 1,caso f1 < f2
     */
    public int compare(Pair<Integer,Float> p1, Pair<Integer,Float> p2){
       float d1 =(float) p1.getValue();
       float d2 =(float) p2.getValue();
       
       if(d1==d2) return 0;
       if(d1>d2) return -1;
       return 1;
    }
}
