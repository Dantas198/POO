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
    
    public float getDeducaoGlobal(){
        return this.deducaoGlobal;
    }
    public void setDeducaoGlobal(float deducaoGlobal){
        this.deducaoGlobal = deducaoGlobal;
    }
    public void setDeducaoGlobal(Contribuinte c){
        this.deducaoGlobal = ((ContribuinteIndividual) c).getCoefFiscal() * this.naturezaDespesa.getCoef();
    }
    public int getNifEmitente() {
        return nifEmitente;
    }
    public void setNifEmitente(int nifEmitente) {
        this.nifEmitente = nifEmitente;
    }
    public String getDesignacaoEmitente() {
        return designacaoEmitente;
    }
    public void setDesignacaoEmitente(String designacaoEmitente) {
        this.designacaoEmitente = designacaoEmitente;
    }
    public LocalDateTime getDataDespesa() {
        return dataDespesa;
    }
    public void setDataDespesa(LocalDateTime dataDespesa) {
        this.dataDespesa = dataDespesa;
    }
    public int getNifCliente() {
        return nifCliente;
    }
    public void setNifCliente(int nifCliente) {
        this.nifCliente = nifCliente;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public AtividadeEconomica getNaturezaDespesa() {
        return naturezaDespesa.clone();
    }
    public void setNaturezaDespesa(AtividadeEconomica naturezaDespesa) {
        this.naturezaDespesa = naturezaDespesa.clone();
        this.dedutor.setAtividade(naturezaDespesa);
    }
    public float getDespesa() {
        return despesa;
    }
    public void setDespesa(float despesa) {
        this.despesa = despesa;
    }
    
    public int getNumFatura() {
        return numFatura;
    }
    public void setNumFatura(int numFatura) {
        this.numFatura = numFatura;
    }
    
    public float getValorDeduzivel() {
    	float deduzivel = (float) dedutor.calculateDeduzivel();
    	float res;
    	res = this.despesa * deduzivel;
    	return res;
    }
    
    public Fatura(){

    }
    
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
    
    public Fatura(ContribuinteEmpresarial emitente, LocalDateTime now, Contribuinte cliente,
			String descricao, AtividadeEconomica naturezaDespesa, float despesa) {
    	this.nifEmitente = emitente.getNif();
        this.nifCliente = cliente.getNif();
        this.designacaoEmitente = emitente.getNome();
        this.despesa = despesa;
        this.descricao = descricao;
        this.naturezaDespesa = naturezaDespesa;
        this.dataDespesa = now;
		if (cliente instanceof ContribuinteDedutor) {
			ContribuinteDedutor new_name = (ContribuinteDedutor) cliente;
			this.dedutor = this.getDedutor();
			this.dedutor.setAtividade(naturezaDespesa);
		}
		else
			this.dedutor = new DeducNull();		
	}
    
	public Fatura clone() {
        return new Fatura(this);
    }
	public Deductor getDedutor() {
		return dedutor.clone();
	}
	public void setDedutor(Deductor dedutor) {
		this.dedutor = dedutor.clone();
	}
}
