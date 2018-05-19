package moradas;

import javafx.util.Pair;

public class Morada{
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
	
	public Morada(Morada morada) {
		// TODO Auto-generated constructor stub
	}
	

	public Morada clone(){
		// TODO Auto-generated method stub
		return new Morada(this);
	}
}
