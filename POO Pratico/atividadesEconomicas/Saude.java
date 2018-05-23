package atividadesEconomicas;

public class Saude extends AtividadeEconomica {

	public Saude() {
		this.setNomeAtividade("Saude");
		this.setCoef((float) 0.15);
	}

	public Saude(Saude a) {
		super(a);
	}
}
