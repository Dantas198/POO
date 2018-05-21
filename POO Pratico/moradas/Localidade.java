package moradas;

import java.io.Serializable;


public abstract class Localidade implements Serializable{
    private String localidade;
    
    /**
     * Devolve a localidade
     */
    public String getLocalidade() {
        return localidade;
    }
    
    /**
     * Atualiza a localidade
     */
    public Localidade(String localidade) {
        this.localidade = localidade;
    }
    
    /**
     * Clone abstrato
     */
    public abstract Localidade clone();
}
