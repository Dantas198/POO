package deductors;

import java.io.Serializable;

import atividadesEconomicas.AtividadeEconomica;
import contribuintes.ContribuinteEmpresarial;

public class DeductorEmpresarial implements Deductor,Serializable {

	private double deduzivel;
	
	public DeductorEmpresarial(double deduzivel) {
		this.deduzivel = deduzivel;
	}

	public DeductorEmpresarial(ContribuinteEmpresarial contribuinteEmpresarial) {
		this.deduzivel = contribuinteEmpresarial.reducaoImposto();
	}

	@Override
	public void setAtividade(AtividadeEconomica n) {
		return;
	}

	@Override
	public double calculateDeduzivel() {
		return 0.05+this.deduzivel;
	}

	@Override
	public Deductor clone() {
		return new DeductorEmpresarial(deduzivel);
	}
}
