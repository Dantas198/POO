
package comparators;
import java.util.Comparator;

import fatura.Fatura;

import java.time.LocalDateTime;
public class CompareFaturasByDate implements Comparator<Fatura>
{   
    public int compare(Fatura f1, Fatura f2){
        LocalDateTime d1 = f1.getDataDespesa();
        LocalDateTime d2 = f2.getDataDespesa();
        
        if(d1==d2) return 0;
        if(d1.isAfter(d2)) return 1;
        return -1;
    
    }
}
