package comparators;
import java.util.Comparator;

import fatura.Fatura;
public class CompareFaturasByValor implements Comparator<Fatura>
{   
    public int compare(Fatura f1, Fatura f2){
        float d1 = f1.getDespesa();
        float d2 = f2.getDespesa();
        
        if(d1==d2) return 0;
        if(d1-d2 >0) return 1;
        return -1;
    
    }
}
