package atividadesEconomicas;

public class ReparacaoManutencaoMotociclos extends AtividadeEconomica {

	public ReparacaoManutencaoMotociclos() {
		this.setNomeAtividade("Reparacao e manutencao de motociclos");
		this.setCoef((float) 0.1);
	}

	public ReparacaoManutencaoMotociclos(ReparacaoManutencaoMotociclos a) {
		super(a);
	}
	
	@Override
	public ReparacaoManutencaoMotociclos clone() {
		return new ReparacaoManutencaoMotociclos(this);
	}
}
