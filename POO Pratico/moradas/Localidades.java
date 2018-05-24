package moradas;

import java.io.Serializable;
import java.util.HashMap;

public class Localidades implements Serializable {
    private HashMap<String, Localidade> localidades;
    
    /**
     * Adiciona uma localidade
     */
    public void addLocalidade(Localidade a) {
        localidades.put(a.getLocalidade(), a.clone());
    }
    
    /**
     * Construtor vazio
     */
    public Localidades(){
        localidades = new HashMap<>();
    }
    
    /**
     * Construtor parametrizado
     */
    public Localidades(HashMap<String, Localidade> loc){
        super();
    	for (Localidade l : loc.values()) {
			this.localidades.put(l.getLocalidade(), l.clone());
		}
    }
    
    public Localidades clone() {
    	return new Localidades(this.localidades);
    }
    
}