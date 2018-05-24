package atividadesEconomicas;

public class Educacao extends AtividadeEconomica {

	public Educacao() {
		this.setNomeAtividade(new String("Educacao"));
		this.setCoef((float) 0.30);
	}

	public Educacao(Educacao a) {
		super(a);
	}
}
