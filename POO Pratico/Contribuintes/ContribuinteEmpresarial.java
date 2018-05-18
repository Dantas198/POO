package Contribuintes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import AtividadesEconomicas.AtividadeEconomica;
import Fatura.Fatura;

public class ContribuinteEmpresarial extends Contribuinte implements Serializable {
    private HashMap<Integer,AtividadeEconomica> atividadesEmpresa;
    private float fatorDeducao;
    private int countFaturas;
    
    public ContribuinteEmpresarial(){
        super();
        this.fatorDeducao = 0;
        this.atividadesEmpresa = new HashMap<Integer, AtividadeEconomica>();
        this.countFaturas = 0;
    }
    
    public ContribuinteEmpresarial(ContribuinteEmpresarial c) {
        super(c);
        this.setAtividadesEmpresa(c.getAtividadesEmpresa());
        this.fatorDeducao = c.getFatorDeducao();
        this.countFaturas = c.getCountFaturas();
    }
    
    private void setAtividadesEmpresa(Map<Integer, AtividadeEconomica> a) {
        // TODO Auto-generated method stub
        for(AtividadeEconomica v : a.values())
            this.atividadesEmpresa.put(v.getKey(), v.clone());
    }
    
    private HashMap<Integer, AtividadeEconomica> getAtividadesEmpresa() {
        HashMap<Integer, AtividadeEconomica> res = new HashMap<>();
        for(Map.Entry<Integer, AtividadeEconomica> e : this.atividadesEmpresa.entrySet())
            res.put(e.getKey(), e.getValue().clone());
            
        return res;
    }
    
    public int getCountFaturas(){
        return this.countFaturas;
    }
    
    public void setCountFaturas(int countFaturas){
        this.countFaturas = countFaturas;
    }
    
    public float getFatorDeducao() {
        return fatorDeducao;
    }
    
    public void setFatorDeducao(float fatorDeducao) {
        this.fatorDeducao = fatorDeducao;
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
        // TODO Auto-generated method stub
        return new ContribuinteEmpresarial(this);
    }
}
