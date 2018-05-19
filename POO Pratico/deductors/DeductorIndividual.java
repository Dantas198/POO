package deductors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import atividadesEconomicas.AtividadeEconomica;
import contribuintes.Contribuinte;
import contribuintes.ContribuinteIndividual;

public class DeductorIndividual implements Deductor,Serializable {
	Map<String,AtividadeEconomica> actDeduziveis;
	private AtividadeEconomica atividade;
	private double fatorAtividade;
	private double reducaoContribuinte;
	
	public float percentagemDeduzivel() {
		return 0;
	}
	
	private void updateFatorAtividade() {
		if(this.atividade != null && actDeduziveis.containsKey(this.atividade.getNomeAtividade())) {
			this.fatorAtividade = this.atividade.getCoef(); 
		}
		else
			this.fatorAtividade = 0;
	}
	
	public void setAtividade(AtividadeEconomica n) {
		this.atividade = n.clone();
		updateFatorAtividade();
	}
	
	
	public double calculateDeduzivel() {
		return fatorAtividade+reducaoContribuinte;
	}

	public Map<String, AtividadeEconomica> getActDeduziveis() {
		return actDeduziveis;
	}

	public void setActDeduziveis(Map<String, AtividadeEconomica> actDeduziveis) {
		HashMap<String, AtividadeEconomica> n = new HashMap<String,AtividadeEconomica>();
		for (AtividadeEconomica f : actDeduziveis.values()) {
			n.put(f.getNomeAtividade(), f.clone());
		}
		this.actDeduziveis = n;
		updateFatorAtividade();
	}

	public double getReducaoContribuinte() {
		return reducaoContribuinte;
	}

	public void setReducaoContribuinte(double reducaoContribuinte) {
		this.reducaoContribuinte = reducaoContribuinte;
	}

	public AtividadeEconomica getAtividade() {
		return atividade;
	}
	
	public DeductorIndividual() {
		this.actDeduziveis = new HashMap<>();
		this.atividade = null;
		this.fatorAtividade = 0;
		this.reducaoContribuinte = 0;
	}
	
	public DeductorIndividual(Map<String, AtividadeEconomica> actDeduziveis, AtividadeEconomica atividade,
			double reducaoContribuinte) {
		this.setActDeduziveis(actDeduziveis);
		this.reducaoContribuinte = reducaoContribuinte;
		this.setAtividade(atividade);
	}
	
	public DeductorIndividual(ContribuinteIndividual cliente, AtividadeEconomica naturezaDespesa) {
		this.actDeduziveis = cliente.getActDeduziveis();
		this.reducaoContribuinte = cliente.reducaoImposto();
		this.setAtividade(naturezaDespesa);
	}
	
	public DeductorIndividual clone() {
		return new DeductorIndividual(this.actDeduziveis, this.atividade, this.reducaoContribuinte);
	}
}
