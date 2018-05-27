package atividadesEconomicas;

public class ReparacaoManutencaoVeiculos extends AtividadeEconomica {

	public ReparacaoManutencaoVeiculos() {
		this.setNomeAtividade("Reparacao e Manutencao de Veiculos");
		this.setCoef((float) 0.1);
	}

	public ReparacaoManutencaoVeiculos(ReparacaoManutencaoVeiculos a) {
		super(a);
	}
	
	@Override
	public ReparacaoManutencaoVeiculos clone() {
		// TODO Auto-generated method stub
		return new ReparacaoManutencaoVeiculos(this);
	}
}
