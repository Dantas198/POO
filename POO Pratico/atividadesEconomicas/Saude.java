package atividadesEconomicas;

public class Saude extends AtividadeEconomica {

	public Saude() {
		String s = "Saude";
		this.setNomeAtividade(s);
		this.setCoef(0.15f);
	}

	public Saude(Saude a) {
		super(a);
	}
}
