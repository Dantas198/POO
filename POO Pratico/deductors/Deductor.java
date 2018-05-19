package deductors;

import atividadesEconomicas.AtividadeEconomica;

public interface Deductor {
	public void setAtividade(AtividadeEconomica n);
	public double calculateDeduzivel();
	public Deductor clone();
}
