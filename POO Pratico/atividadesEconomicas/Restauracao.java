package atividadesEconomicas;

public class Restauracao extends AtividadeEconomica {

	public Restauracao () {
		this.setNomeAtividade("Restauracao");
		this.setCoef((float) 0.15);
	}

	public Restauracao(Restauracao a) {
		super(a);
	}
}
