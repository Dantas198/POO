package AtividadesEconomicas;
//Pode vir a ser Imutavel
public class AtividadeEconomica {
	private Deducao d;
	
	public AtividadeEconomica(AtividadeEconomica a) {
		// TODO Auto-generated constructor stub
	}
	
	public AtividadeEconomica(Deducao d) {
		this.d = d;
	}

	public Integer getKey() {
		return 0;
	}
	
	public AtividadeEconomica clone() {
		return new AtividadeEconomica(this);
	}
}
