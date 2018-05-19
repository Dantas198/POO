package moradas;

import java.io.Serializable;
import java.util.HashMap;

public class Localidades implements Serializable {
	private HashMap<String, Localidade> localidades;
	
	public void addLocalidade(Localidade a) {
		localidades.put(a.getLocalidade(), a.clone());
	}
}