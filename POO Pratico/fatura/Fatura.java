package fatura;

import contribuintes.Contribuinte;
import contribuintes.ContribuinteDedutor;
import contribuintes.ContribuinteEmpresarial;
import contribuintes.ContribuinteIndividual;
import deductors.DeducNull;
import deductors.Deductor;
import deductors.DeductorIndividual;

import java.io.Serializable;
import java.time.LocalDateTime;

import atividadesEconomicas.AtividadeEconomica;

public class Fatura implements Serializable{
    private int nifEmitente;
    private String designacaoEmitente;
    private LocalDateTime dataDespesa; //imutavel
    private int nifCliente;
    private String descricao;
    private AtividadeEconomica naturezaDespesa;//Ver como implementar
    private float despesa;
    private int numFatura;
    private float deducaoGlobal;
    private Deductor dedutor;
    
    /**
     * Devolve a deducaoGlobal da Fatura, que é a percentagem total de deducao, contendo o coeficiente do 
     * contribuinte individual e a deducao oferecida pela empresa
     * 
     * @returns deducaoGlobal
     */
    public float getDeducaoGlobal(){
        return this.deducaoGlobal;
    }
    
    /**
     * @param deducao Global da Fatura
     * 
     * Atualiza o valor da variável deducaoGlobal
     */
    public void setDeducaoGlobal(float deducaoGlobal){
        this.deducaoGlobal = deducaoGlobal;
    }
    
    /**
     * @param Contribuinte c
     * 
     * Método que Atualiza o valor da variável deducaoGlobal de acordo com o Coeficiente Fiscal de um contribuinte
     * e do Coeficiente Fiscal da Natureza da Despesa, ou seja, a Atividade Económica
     */
    public void setDeducaoGlobal(Contribuinte c){
        this.deducaoGlobal = ((ContribuinteIndividual) c).getCoefFiscal() * this.naturezaDespesa.getCoef();
    }
    
    /** 
     * Devolve o nif do Emitente
     * 
     * @returns nifEmitente
     */
    public int getNifEmitente() {
        return nifEmitente;
    }
    
    /**
     * @param nifEmitente
     * 
     * Atualiza o valor da variável nifEmitente
     */
    public void setNifEmitente(int nifEmitente) {
        this.nifEmitente = nifEmitente;
    }
    
    /**
     * Devolve o nome do Emitente
     * 
     * @returns designacaoEmitente
     */
    public String getDesignacaoEmitente() {
        return designacaoEmitente;
    }
    
    /**
     * @param designacaoEmitente
     * 
     * Atualiza o nome do emitente
     */
    public void setDesignacaoEmitente(String designacaoEmitente) {
        this.designacaoEmitente = designacaoEmitente;
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
        return nifCliente;
    }
    
    /**
     * @param nifCliente
     * 
     * Atualiza o nif do Cliente
     */
    public void setNifCliente(int nifCliente) {
        this.nifCliente = nifCliente;
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
        return naturezaDespesa.clone();
    }
    
    /**
     * @param naturezaDespesa, que é uma atividade económica
     * 
     * Atualiza o valor da natureza da despesa
     */
    public void setNaturezaDespesa(AtividadeEconomica naturezaDespesa) {
        this.naturezaDespesa = naturezaDespesa.clone();
        this.dedutor.setAtividade(naturezaDespesa);
    }
    
    /**
     * Devolve a despesa da fatura
     * 
     * @returns despesa
     */
    public float getDespesa() {
        return despesa;
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
        float deduzivel = (float) dedutor.calculateDeduzivel();
        float res;
        res = this.despesa * deduzivel;
        return res;
    }
    
    /**
     * Devolve o dedutor
     * 
     * @returns dedutor, clonado
     */
    public Deductor getDedutor() {
        return dedutor.clone();
    }
<<<<<<< HEAD
=======
    
    /**
     * @param novo dedutor
     * 
     * Atualiza a variável dedutor
     */
>>>>>>> b58349cc0243a450c6cd1907ae4a27ff6266e218
    public void setDedutor(Deductor dedutor) {
        this.dedutor = dedutor.clone();
    }
    
<<<<<<< HEAD
=======
    /**
     * Construtor vazio de Fatura
     */
>>>>>>> b58349cc0243a450c6cd1907ae4a27ff6266e218
    public Fatura(){

    }
    
    /**
     * Construtor parametrizado de Fatura
     */
    public Fatura(int nEmitente, String designacaoEmitente, LocalDateTime data, int nifCliente, String descricao, AtividadeEconomica naturezaDespesa, float despesa) {
        this.nifEmitente = nEmitente;
        this.nifCliente =nifCliente;
        this.designacaoEmitente = designacaoEmitente;
        this.despesa = despesa;
        this.descricao = descricao;
        this.naturezaDespesa = naturezaDespesa;
        this.dataDespesa = data;
        this.dedutor = new DeducNull();
    }
    
    /**
     * Construtor de copia de Fatura
     */
    public Fatura(Fatura f) {
        this.nifEmitente = f.getNifEmitente();
        this.nifCliente = f.getNifCliente();
        this.designacaoEmitente = f.getDesignacaoEmitente();
        this.despesa = f.getDespesa();
        this.descricao = f.getDescricao();
        this.naturezaDespesa = f.getNaturezaDespesa();
        this.dataDespesa = f.getDataDespesa();
        this.numFatura = f.getNumFatura();
        this.dedutor = this.getDedutor();
    }
<<<<<<< HEAD
    
    public Fatura(ContribuinteEmpresarial emitente, LocalDateTime date, Contribuinte cliente,
=======
   
    /**
     * Construtor parametrizado de Fatura, neste caso recebe como parametros diferentes os
     * Contribuintes do emitente e do cliente
     */
    public Fatura(ContribuinteEmpresarial emitente, LocalDateTime now, Contribuinte cliente,
>>>>>>> b58349cc0243a450c6cd1907ae4a27ff6266e218
            String descricao, AtividadeEconomica naturezaDespesa, float despesa) {
        this.nifEmitente = emitente.getNif();
        this.nifCliente = cliente.getNif();
        this.designacaoEmitente = emitente.getNome();
        this.despesa = despesa;
        this.descricao = descricao;
        this.naturezaDespesa = naturezaDespesa;
<<<<<<< HEAD
        this.dataDespesa = date;
=======
        this.dataDespesa = now;
>>>>>>> b58349cc0243a450c6cd1907ae4a27ff6266e218
        if (cliente instanceof ContribuinteDedutor) {
            ContribuinteDedutor new_name = (ContribuinteDedutor) cliente;
            this.dedutor = this.getDedutor();
            this.dedutor.setAtividade(naturezaDespesa);
        }
        else
            this.dedutor = new DeducNull();     
    }
    
<<<<<<< HEAD
    public Fatura clone() {
        return new Fatura(this);
    }
    
=======
    /**
     * Clone 
     */
    public Fatura clone() {
        return new Fatura(this);
    }
>>>>>>> b58349cc0243a450c6cd1907ae4a27ff6266e218
}
