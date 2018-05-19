package deductors;

import java.io.Serializable;

import atividadesEconomicas.AtividadeEconomica;

public class DeducNull implements Deductor,Serializable {

	@Override
	public void setAtividade(AtividadeEconomica n) {
		return;
	}

	@Override
	public double calculateDeduzivel() {
		return 0;
	}

	@Override
	public Deductor clone() {
		return this;
	}
}
