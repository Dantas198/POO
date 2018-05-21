package moradas;

import java.io.Serializable;
import java.util.HashMap;

public class Localidades implements Serializable {
    private HashMap<String, Localidade> localidades;
    
    /**
     * Construtor vazio
     */
    public Localidades(){
        localidades = new HashMap<>();
    }
    
    /**
     * Construtor parametrizado
     */
    public Localidades(HashMap<String, Localidade> localidades){
        this.localidades = localidades;
    }
    
    /**
     * Adiciona uma localidade
     */
    public void addLocalidade(Localidade a) {
        localidades.put(a.getLocalidade(), a.clone());
        }
}