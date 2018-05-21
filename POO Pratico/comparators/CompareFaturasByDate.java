
package comparators;
import java.util.Comparator;

import fatura.Fatura;

import java.time.LocalDateTime;
public class CompareFaturasByDate implements Comparator<Fatura>
{   
    /**
     * @param f1, primeira fatura
     * @param f2, segunda fatura
     * Compara duas faturas de acordo com a sua data de criacao
     * @returns 0, caso tenham a mesma data
     * @returns 1, caso f1 > f2
     * @returns -1,caso f1 < f2
     */
    public int compare(Fatura f1, Fatura f2){
        LocalDateTime d1 = f1.getDataDespesa();
        LocalDateTime d2 = f2.getDataDespesa();
        
        if(d1==d2) return 0;
        if(d1.isAfter(d2)) return 1;
        return -1;
    
    }
}
