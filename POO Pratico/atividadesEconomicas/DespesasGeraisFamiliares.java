package atividadesEconomicas;

public class DespesasGeraisFamiliares extends AtividadeEconomica {

	public DespesasGeraisFamiliares() {
		this.setNomeAtividade("Despesas Gerais Familiares");
		this.setCoef((float) 0.35);
	}

	public DespesasGeraisFamiliares(DespesasGeraisFamiliares a) {
		super(a);
	}
	
	@Override
	public DespesasGeraisFamiliares clone() {
		return new DespesasGeraisFamiliares(this);
	}
}

