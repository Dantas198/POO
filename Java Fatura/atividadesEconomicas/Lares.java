package atividadesEconomicas;

public class Lares extends AtividadeEconomica {

	public Lares() {
		this.setNomeAtividade("Lares");
		this.setCoef((float) 0.20);
	}

	public Lares(Lares a) {
		super(a);
	}
	
	@Override
	public Lares clone() {
		return new Lares(this);
	}
}
