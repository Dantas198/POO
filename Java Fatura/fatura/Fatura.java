package fatura;

 

import contribuintes.BeneficioFiscal;
import contribuintes.Contribuinte;
import contribuintes.ContribuinteEmpresarial;
import contribuintes.ContribuinteIndividual;



import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.lang.StringBuilder; 

import atividadesEconomicas.AtividadeEconomica;

public class Fatura implements Serializable{
    private ContribuinteEmpresarial emitente;
    private LocalDateTime dataDespesa; //imutavel
    private Contribuinte cliente;
    private String descricao;
    private AtividadeEconomica naturezaDespesa;//Ver como implementar
    private float despesa;
    private int numFatura;
    private float deducaoGlobal;
    

    /**
     * Verifica se o cliente � um contribuinte individual
     */
    public boolean isClientIndividual(){
        return (cliente instanceof ContribuinteIndividual);
    }
    
    
    /** 
     * Devolve o nif do Emitente
     * 
     * @returns nifEmitente
     */
    public int getNifEmitente() {
        return emitente.getNif();
    }
    
    /**
     * Devolve uma lista com as atividades economicas da empresa
     * */
    public List<AtividadeEconomica> getNaturezasPossiveis() {
        return this.emitente.getListAtividadesEmpresa();
    }
    /** 
     * Devolve copia do Emitente quando a fatura foi criada
     * 
     * @returns nifEmitente
     */
    public ContribuinteEmpresarial getEmitente() {
        return emitente.clone();
    }
    
    /**
     * @param nifEmitente
     * 
     * Atualiza o valor da variável Emitente
     */
    public void setEmitente(ContribuinteEmpresarial e) {
        this.emitente = e.clone();
    }
    
    /**
     * Devolve o nome do Emitente
     * 
     * @returns designacaoEmitente
     */
    public String getDesignacaoEmitente() {
        return emitente.getNome();
    }
    
    /**
     * Devolve a data da despesa
     * 
     * @returns dataDespesa
     */
    public LocalDateTime getDataDespesa() {
        return dataDespesa;
    }
    
    /**
     * @param dataDespesa
     * 
     * Atualiza o valor da variável dataDespesa
     */
    public void setDataDespesa(LocalDateTime dataDespesa) {
        this.dataDespesa = dataDespesa;
    }
    
    /**
     * Devolve o nif do cliente 
     * 
     * @returns nifCliente
     */
    public int getNifCliente() {
        return this.cliente.getNif();
    }
    
    /**
     * Devolve o cliente quando criada a fatura
     * 
     * @returns nifCliente
     */
    public Contribuinte getCliente() {
        return cliente.clone();
    }
    
    /**
     * @param nifCliente
     * 
     * Atualiza o Cliente
     */
    public void setCliente(Contribuinte c) {
        this.cliente = c.clone();
    }
    
    /**
     * Devolve a descricao da fatura
     * 
     * @returns descricao
     */
    public String getDescricao() {
        return descricao;
    }
    
    /**
     * @param descricao
     * 
     * Atualiza a descricao da fatura
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    /**
     * Devolve a natureza da despesa
     * 
     * @returns naturezaDespesa
     */
    public AtividadeEconomica getNaturezaDespesa() {
    	if(this.naturezaDespesa != null)
    		return naturezaDespesa.clone();
    	return null;
    }
    
    /**
     * @param naturezaDespesa, que é uma atividade económica
     * 
     * Atualiza o valor da natureza da despesa
     */
    public void setNaturezaDespesa(AtividadeEconomica naturezaDespesa) {
        this.naturezaDespesa = naturezaDespesa.clone();
    }
    
    /**
     * Devolve a despesa da fatura
     * 
     * @returns despesa
     */
    public float getDespesa() {
        return this.despesa;
    }
    
    /**
     * @param despesa da fatura
     * 
     * Atualiza o valor da variável despesa
     */
    public void setDespesa(float despesa) {
        this.despesa = despesa;
    }
   
    /**
     * Devolve o numero da fatura
     * 
     * @returns numFatura
     */
     public int getNumFatura() {
        return numFatura;
    }
   
    /**
     * @param numFatura
     * 
     * Atualiza o numero da Fatura 
     */
    public void setNumFatura(int numFatura) {
        this.numFatura = numFatura;
    }
    
    /**
     * Devolve o valor deduzivel 
     * 
     * @returns res, valor da deducao   
    */
    public float getValorDeduzivel() {
        double deduzivel = 0;
        if (cliente instanceof BeneficioFiscal) {
            BeneficioFiscal n = (BeneficioFiscal) cliente;
            deduzivel = n.reducaoImposto();         
        }
        if (cliente instanceof ContribuinteIndividual) {
            ContribuinteIndividual n2 = (ContribuinteIndividual) cliente;
            if(this.naturezaDespesa != null && n2.isActDeduzivel(this.naturezaDespesa))
                deduzivel += this.naturezaDespesa.getCoef();
            deduzivel += n2.getCoefFiscal();
        }
        float res = this.despesa * (float)deduzivel;
        return res;
    }
    
    /**
     * Construtor vazio de Fatura
     */
    public Fatura(){

    }
    
    
    /**
     * Construtor de copia de Fatura
     */
    public Fatura(Fatura f) {
        this.cliente = f.getCliente();
        this.emitente = f.getEmitente();
        this.despesa = f.getDespesa();
        this.descricao = f.getDescricao();
        this.naturezaDespesa = f.getNaturezaDespesa();
        this.dataDespesa = f.getDataDespesa();
        this.numFatura = f.getNumFatura();
    }

    /**
     * Construtor parametrizado de Fatura, neste caso recebe como parametros diferentes os
     * Contribuintes do emitente e do cliente
     */
    public Fatura(ContribuinteEmpresarial emitente, LocalDateTime date, Contribuinte cliente,
            String descricao, float despesa) {
        this.cliente= cliente.clone();
        this.emitente = emitente.clone();
        this.despesa = despesa;
        this.descricao = descricao;
        this.dataDespesa = date;
        this.naturezaDespesa = null;
    }
    
    /**
     * Clone 
     */
    public Fatura clone() {
        return new Fatura(this);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Numero de fatura: " + this.getNumFatura() + " - " + this.getDataDespesa() + "\n");
        sb.append("Nif emitente : " + this.getEmitente().getNif() + "\n");
        sb.append("Nif cliente : " + this.getCliente().getNif() + "\n");
        sb.append("Descricao: " + this.getDescricao() + "\n");
        sb.append("Despesa : " + this.getDespesa() + "\n");
        if(this.getNaturezaDespesa() != null)
            sb.append("Natureza da despesa: " + this.getNaturezaDespesa() + "\n");
        return sb.toString();
    }
}
