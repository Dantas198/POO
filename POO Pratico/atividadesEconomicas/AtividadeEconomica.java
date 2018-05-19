package atividadesEconomicas;

import java.io.Serializable;

//Pode vir a ser Imutavel
public class AtividadeEconomica implements Serializable{
	
	private float coef;
	private String nomeAtividade;
	
	public AtividadeEconomica(float coef, String nomeAtividade) {
		super();
		this.coef = coef;
		this.nomeAtividade = nomeAtividade;
	}

	public AtividadeEconomica(AtividadeEconomica a) {
		this.coef = a.getCoef();
	}

	public AtividadeEconomica() {
		super();
		this.coef = 0;
		this.nomeAtividade = null;
	}

	public Integer getKey() {
		return 0;
	}
	
	public AtividadeEconomica clone() {
		return new AtividadeEconomica(this);
	}

	public float getCoef() {
		return coef;
	}

	public void setCoef(float coef) {
		this.coef = coef;
	}

	public String getNomeAtividade() {
		return nomeAtividade;
	}

	public void setNomeAtividade(String nomeAtividade) {
		this.nomeAtividade = nomeAtividade;
	}
}
