package Contribuintes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class Contribuintes implements Serializable{
	HashMap<Integer, Contribuinte> contribuintes;//Keys Nifs dos contribuintes
	
	public void addContribuinte(Contribuinte c) {
		contribuintes.put(c.getNif(), c.clone());
	}
	
	public void addContribuintes(Set<Contribuinte> c){
		c.stream().map(p -> contribuintes.put(p.getNif(), p.clone()));		
	}
	
	public Contribuinte login(int nif, String password) {
		if (contribuintes.containsKey(nif)){
			Contribuinte c = contribuintes.get(nif);
			if(c.isPassword(password))
				return c;
		}
		return null;
	}
	
	public Contribuintes() {
		// TODO Auto-generated constructor stub
		contribuintes = new HashMap<Integer,Contribuinte>();
	}
}
