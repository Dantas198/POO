package contribuintes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import atividadesEconomicas.AtividadeEconomica;
import deductors.DeducNull;
import deductors.Deductor;
import deductors.DeductorEmpresarial;
import fatura.Fatura;
import moradas.Localidade;
import moradas.LocalidadeCentro;
import moradas.Morada;

public class ContribuinteEmpresarial extends Contribuinte implements Serializable,BeneficioFiscal,ContribuinteDedutor {
    private HashMap<String,AtividadeEconomica> atividadesEmpresa;
    private int countFaturas;
    
    public ContribuinteEmpresarial(){
        super();
        this.atividadesEmpresa = new HashMap<String, AtividadeEconomica>();
        this.countFaturas = 0;
    }
    
    public ContribuinteEmpresarial(ContribuinteEmpresarial c) {
        super(c);
        this.setAtividadesEmpresa(c.getAtividadesEmpresa());
        this.countFaturas = c.getCountFaturas();
    }
    
    private void setAtividadesEmpresa(Map<String, AtividadeEconomica> a) {
        for(AtividadeEconomica v : a.values())
            this.atividadesEmpresa.put(v.getNomeAtividade(), v.clone());
    }
    
    private Map<String, AtividadeEconomica> getAtividadesEmpresa() {
        Map<String, AtividadeEconomica> res = new HashMap<>();
        for(Entry<String, AtividadeEconomica> e : this.atividadesEmpresa.entrySet())
            res.put(e.getKey(), e.getValue().clone());
            
        return res;
    }
    
    public int getCountFaturas(){
        return this.countFaturas;
    }
    
    public Fatura emiteFatura(ContribuinteIndividual cliente,String descricao,float despesa) {
        Fatura res = new Fatura(this, LocalDateTime.now(), cliente, descricao, null, despesa);
        if(atividadesEmpresa.size()==1) {
            //Garantimos que nao ocorre excessao
            AtividadeEconomica a = atividadesEmpresa.values().stream().findFirst().get();
            res.setNaturezaDespesa(a);
        }
        this.countFaturas++;
        return(res);
    }
    
    public Fatura emiteFatura(int nifCliente,String descricao,float despesa) {
        Fatura res = new Fatura(this.getNif(), this.getNome(), LocalDateTime.now(), nifCliente, descricao, null, despesa);
        if(atividadesEmpresa.size()==1) {
            //Garantimos que nao ocorre excessao
            AtividadeEconomica a = atividadesEmpresa.values().stream().findFirst().get();
            res.setNaturezaDespesa(a);
        }
        return(res);
    }
    
    @Override
    public ContribuinteEmpresarial clone() {
        return new ContribuinteEmpresarial(this);
    }

	@Override
	public double reducaoImposto() {
		Morada m = this.getMorada();
		Localidade x = m.getLocalidade();
		if (x instanceof LocalidadeCentro) {
			LocalidadeCentro n = (LocalidadeCentro) x;
			return n.getBeneficioPercentagem();
		}
		return 0;
	}

	@Override
	public Deductor getDeductor() {
		return new DeductorEmpresarial(this);
	}
}
