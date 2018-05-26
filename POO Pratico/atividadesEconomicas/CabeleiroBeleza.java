package atividadesEconomicas;

public class CabeleiroBeleza extends AtividadeEconomica {

	public CabeleiroBeleza() {
		this.setNomeAtividade("Cabeleiro e Beleza");
		this.setCoef((float) 0.1);
	}

	public CabeleiroBeleza(CabeleiroBeleza a) {
		super(a);
	}

	@Override
	public CabeleiroBeleza clone() {
		return new CabeleiroBeleza(this);
	}
}
