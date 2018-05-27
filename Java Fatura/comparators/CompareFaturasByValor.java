package comparators;
import java.util.Comparator;

import fatura.Fatura;
public class CompareFaturasByValor implements Comparator<Fatura>
{   
    /**
     * @param f1, primeira fatura
     * @param f2, segunda fatura
     * Compara duas faturas de acordo com o seu valor
     * @returns 0, caso tenham o mesmo valor
     * @returns 1, caso despesa f1 > despesa f2
     * @returns -1,caso despesa f1 < despesa f2
     */
    public int compare(Fatura f1, Fatura f2){
        float d1 = f1.getDespesa();
        float d2 = f2.getDespesa();
        
        if(d1==d2) return 0;
        if(d1-d2 >0) return 1;
        return -1;
    
    }
}
