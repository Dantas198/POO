package atividadesEconomicas;

import java.io.Serializable;
import java.lang.StringBuilder;

//Pode vir a ser Imutavel
public class AtividadeEconomica implements Serializable{
    
    private float coef;
    private String nomeAtividade;
    
    /**
     * Construtor parametrizado
     */
    public AtividadeEconomica(float coef, String nomeAtividade) {
        super();
        this.coef = coef;
        this.nomeAtividade = nomeAtividade;
    }
    
    /**
     * Construtor copia
     */
    public AtividadeEconomica(AtividadeEconomica a) {
        this.coef = a.getCoef();
        this.nomeAtividade = a.getNomeAtividade();
    }

    /**
     * Construtor vazio
     */
    public AtividadeEconomica() {
        super();
        this.coef = 0;
        this.nomeAtividade = null;
    }
    
    /**
     * Clone de uma atividade economica
     */
    public AtividadeEconomica clone() {
        return new AtividadeEconomica(this);
    }
    
    /**
     * Devolve o coeficiente fiscal
     */
    public float getCoef() {
        return coef;
    }
    
    /**
     * Atualiza o coeficiente fiscal
     */
    public void setCoef(float coef) {
        this.coef = coef;
    }
    
    /**
     * Devolve o nome da atividade
     */
    public String getNomeAtividade() {
        return nomeAtividade;
        }
    
    /**
     * Atualiza o nome da atividade
     */
    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getNomeAtividade());
        return sb.toString();
    }
    
}
