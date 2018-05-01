package Contribuintes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import AtividadesEconomicas.AtividadeEconomica;
import Fatura.Fatura;

public class ContribuinteEmpresarial extends Contribuinte {
	private HashMap<Integer,AtividadeEconomica> atividadesEmpresa;
	private float fatorDeducao;
	
	public ContribuinteEmpresarial(ContribuinteEmpresarial c) {
		super(c);
		this.setAtividadesEmpresa(c.getAtividadesEmpresa());
		this.fatorDeducao = c.getFatorDeducao();
	}
	
	private void setAtividadesEmpresa(Map<Integer, AtividadeEconomica> a) {
		// TODO Auto-generated method stub
		for(AtividadeEconomica v : a.values())
			this.atividadesEmpresa.put(v.getKey(), v.clone());
	}
	
	private HashMap<Integer, AtividadeEconomica> getAtividadesEmpresa() {
		// TODO Auto-generated method stub
		return null;
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
			//Garantimos que não ocorre excessao
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
