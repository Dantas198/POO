package atividadesEconomicas;


public class PassesTransportes extends AtividadeEconomica {

	public PassesTransportes() {
		this.setNomeAtividade("Passes Transportes");
		this.setCoef((float) 0.05);
	}

	public PassesTransportes(PassesTransportes a) {
		super(a);
	}
}
