package contribuintes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import atividadesEconomicas.AtividadeEconomica;
import fatura.Fatura;
import moradas.Localidade;
import moradas.LocalidadeCentro;
import moradas.Morada;

public class ContribuinteEmpresarial extends Contribuinte implements Serializable,BeneficioFiscal {
    private HashMap<String,AtividadeEconomica> atividadesEmpresa;
    private int countFaturas;
    private float lucro;
    
    /**
     * Construtor vazio
     */
    public ContribuinteEmpresarial(){
        super();
        this.atividadesEmpresa = new HashMap<String, AtividadeEconomica>();
        this.countFaturas = 0;
    }
    
    /**
     * Construtor parametrizado
     */
    public ContribuinteEmpresarial(ContribuinteEmpresarial c) {
        super(c);
        this.setAtividadesEmpresa(c.getAtividadesEmpresa());
        this.countFaturas = c.getCountFaturas();
    }
    
    /**
     * @param a, atividades economicas a inserir
     * Atualiza as atividades economicas de uma empresa
     */
    private void setAtividadesEmpresa(Map<String, AtividadeEconomica> a) {
        for(AtividadeEconomica v : a.values())
            this.atividadesEmpresa.put(v.getNomeAtividade(), v.clone());
    }
    
    /**
     * Devolve as atividades economicas de uma empresa
     * @returns Map de atividadesEconomicas
     */
    private Map<String, AtividadeEconomica> getAtividadesEmpresa() {
        Map<String, AtividadeEconomica> res = new HashMap<>();
        for(Entry<String, AtividadeEconomica> e : this.atividadesEmpresa.entrySet())
            res.put(e.getKey(), e.getValue().clone());
            
        return res;
    }
    
    /**
     * Devolve o contador de faturas emitidas pela empresa
     */
    public int getCountFaturas(){
        return this.countFaturas;
    }
    
    /**
     * Devolve o contador de lucro coletado pela empresa
     */
    public float getLucro(){
        return this.lucro;
    }
    
    /**
     * Atualiza o lucro da empresa
     */
    public void setLucro(float lucro){
        this.lucro = lucro;
    }
    
    /**
     * @param cliente, quem efetuou a despesa
     * @param descricao, descricao da despesa
     * @param despesa, valor da despesa
     * Emite uma fatura por parte da empresa
     * @returns res, a fatura emitida
     */
    public Fatura emiteFatura(Contribuinte cliente,String descricao,float despesa) {
        Fatura res = new Fatura(this, LocalDateTime.now(), cliente, descricao, null, despesa);
        if(atividadesEmpresa.size()==1) {
            //Garantimos que nao ocorre excessao
            AtividadeEconomica a = atividadesEmpresa.values().stream().findFirst().get();
            res.setNaturezaDespesa(a);
        }
        this.countFaturas++;
        this.lucro += despesa;
        return(res);
    }
    
    
    /**
     * Clone
     */
    public ContribuinteEmpresarial clone() {
        return new ContribuinteEmpresarial(this);
    }
    
    /**
     * Calcula a reducao de imposto a implementar.
     */
    public double reducaoImposto() {
        Morada m = this.getMorada();
        Localidade x = m.getLocalidade();
        if (x instanceof LocalidadeCentro) {
            LocalidadeCentro n = (LocalidadeCentro) x;
            return n.getBeneficioPercentagem();
        }
        return 0;
    }
    

}
