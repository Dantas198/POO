package moradas;

import javafx.util.Pair;
import java.io.Serializable;

public class Morada implements Serializable{
    private int numeroPorta;
    private Pair<Integer, Integer> codigoPostal;
    private Localidade localidade;
    
    /**
     * Devolve o numero da porta
     */
    public int getNumeroPorta() {
        return numeroPorta;
    }
    
    /**
     * Atualiza o numero da porta
     */
    public void setNumeroPorta(int numeroPorta) {
        this.numeroPorta = numeroPorta;
    }
    
    /**
     * Devolve o codigo postal, em forma de par
     */
    public Pair<Integer, Integer> getCodigoPostal() {
        return codigoPostal;
    }
    
    /**
     * Atualiza o codigo postal
     */
    public void setCodigoPostal(Pair<Integer, Integer> codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    
    /**
     * Devolve a localidade
     */
    public Localidade getLocalidade() {
        return localidade.clone();
    }
    
    /**
     * Atualiza a localidade
     */
    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade.clone();
    }
    
    /**
     * Construtor vazio
     */
    public Morada() {
        this.numeroPorta = 0;
        this.codigoPostal = null;
        this.localidade = null;
    }
    
    /**
     * Construtor parametrizado
     */
    public Morada(int numeroPorta, Pair<Integer, Integer> codigoPostal, Localidade localidade) {
        this.numeroPorta = numeroPorta;
        this.codigoPostal = codigoPostal;
        this.localidade = localidade.clone();
    }
    
    /**
     * Construtor copia
     */
    public Morada(Morada morada) {
        this.numeroPorta = morada.getNumeroPorta();
        this.codigoPostal = morada.getCodigoPostal();
        this.localidade = morada.getLocalidade();
    }
    
    /**
     * Clone de Morada
     */
    public Morada clone(){
        return new Morada(this);
    }
}
