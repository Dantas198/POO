package moradas;

import javafx.util.Pair;
import java.io.Serializable;

public class Morada implements Serializable{
    private int numeroPorta;
    private Pair<Integer, Integer> codigoPostal;
    private Localidade localidade;
    
    public int getNumeroPorta() {
        return numeroPorta;
    }
    public void setNumeroPorta(int numeroPorta) {
        this.numeroPorta = numeroPorta;
    }
    public Pair<Integer, Integer> getCodigoPostal() {
        return codigoPostal;
    }
    public void setCodigoPostal(Pair<Integer, Integer> codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    public Localidade getLocalidade() {
        return localidade.clone();
    }
    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade.clone();
    }
    
    public Morada() {
        this.numeroPorta = 0;
        this.codigoPostal = null;
        this.localidade = null;
    }
    
    public Morada(int numeroPorta, Pair<Integer, Integer> codigoPostal, Localidade localidade) {
        this.numeroPorta = numeroPorta;
        this.codigoPostal = codigoPostal;
        this.localidade = localidade.clone();
    }
    
    public Morada(Morada morada) {
        this.numeroPorta = this.getNumeroPorta();
        this.codigoPostal = this.getCodigoPostal();
        this.localidade = this.getLocalidade();
    }

    public Morada clone(){
        Morada nova = new Morada();
        nova.setNumeroPorta(this.getNumeroPorta());
        nova.setCodigoPostal(this.getCodigoPostal());
        nova.setLocalidade(this.getLocalidade());
        return nova;
        //return new Morada(this);
    }
}
