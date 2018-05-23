package atividadesEconomicas;


public class Imoveis extends AtividadeEconomica {

	public Imoveis() {
		this.setNomeAtividade("Imoveis");
		this.setCoef((float) 0.25);
	}

	public Imoveis(Imoveis a) {
		super(a);
	}
}
