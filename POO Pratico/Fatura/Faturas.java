package Fatura;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import AtividadesEconomicas.AtividadeEconomica;
import Contribuintes.ContribuinteEmpresarial;
import Exceptions.FaturaNaoExisteException;
import Exceptions.FaturaNaoPendenteException;
import Exceptions.FaturaPendenteException;
import javafx.util.Pair;

public class Faturas implements Serializable {
	private HashMap<Integer, Fatura> faturas;
	private HashMap<Integer, Fatura> faturasPendentes;
	private ArrayList<Pair<Integer,Pair<AtividadeEconomica,AtividadeEconomica>>> correcoes;
	private int numFaturas;
	
	public void addFatura(Fatura f) {
		Fatura i = f.clone();
		i.setNumFatura(this.numFaturas);
		this.numFaturas++;
		if(i.getNaturezaDespesa()==null) {
			this.faturas.put(i.getNumFatura(), i);
		}
		else {
			this.faturasPendentes.put(i.getNumFatura(), i);
		}
	}
	
	public List<Fatura> getFaturasFromContribuinte(int nif){
		return this.faturas.values().stream().filter(p -> p.getNifCliente()==nif).map(Fatura::clone).collect(Collectors.toList());
	}
	
	public List<Fatura> getFaturaPendentesFromContribuinte(int nif){
		return this.faturasPendentes.values().stream().filter(p -> p.getNifCliente()==nif).map(Fatura::clone).collect(Collectors.toList());
	}
	
	public List<Fatura> getFaturasFromEmitente(int nif){
		List<Fatura> x = this.faturas.values().stream().filter(p -> p.getNifEmitente()==nif).map(Fatura::clone).collect(Collectors.toList());
		this.faturasPendentes.values().stream().filter(p -> p.getNifEmitente()==nif).map(Fatura::clone).map(p -> x.add(p));
		return x;
	}
	
	public List<Fatura> getFaturasFromEmitenteBetweenDate(int nif,LocalDateTime beg, LocalDateTime end){
		List<Fatura> x = this.faturas.values().stream().
				filter(p -> p.getNifEmitente()==nif).
				filter(p -> p.getDataDespesa().isAfter(beg) && p.getDataDespesa().isBefore(end)).
				map(Fatura::clone).
				collect(Collectors.toList());
		this.faturasPendentes.values().stream().
				filter(p -> p.getNifCliente()==nif).
				filter(p -> p.getDataDespesa().isAfter(beg) && p.getDataDespesa().isBefore(end)).
				map(Fatura::clone).
				map(p -> x.add(p));
		return x;
	}
	
	public void associaAtividadeEconcomica(int numFatura, AtividadeEconomica a) throws FaturaNaoPendenteException, FaturaNaoExisteException{
		if(!this.faturasPendentes.containsKey(numFatura)) {
			if (this.faturas.containsKey(numFatura)) {
				throw(new FaturaNaoPendenteException(Integer.toString(numFatura)));				
			} else {
				throw(new FaturaNaoExisteException(Integer.toString(numFatura)));
			}
		}
		Fatura f = this.faturasPendentes.get(numFatura);
		f.setNaturezaDespesa(a);
		return;
	}
	
	public void corrigeAtividadeFatura(int numFatura, AtividadeEconomica nova) throws FaturaNaoExisteException, FaturaPendenteException {
		if(!this.faturas.containsKey(numFatura)) {
			if (this.faturasPendentes.containsKey(numFatura)) {
				throw (new FaturaPendenteException(Integer.toString(numFatura)));
			} else {
				throw(new FaturaNaoExisteException(Integer.toString(numFatura)));
			}
		}
		Fatura f = this.faturas.get(numFatura);
		AtividadeEconomica old = f.getNaturezaDespesa();
		Pair <AtividadeEconomica,AtividadeEconomica> atividades = new Pair<AtividadeEconomica, AtividadeEconomica>(old,nova);
		Pair <Integer,Pair<AtividadeEconomica,AtividadeEconomica>> change = new Pair<Integer, Pair<AtividadeEconomica,AtividadeEconomica>>(f.getNumFatura(), atividades);
		this.correcoes.add(change);
	}
	
	public float totalFaturado(int nifEmitente) {
		List<Fatura> x = this.getFaturasFromEmitente(nifEmitente);
		return (float) x.stream().mapToDouble(Fatura::getDespesa).sum();
	}
	
	public Faturas() {
		this.faturas = new HashMap<>();
		this.faturasPendentes = new HashMap<>();
		this.correcoes = new ArrayList<>();
		this.numFaturas = 0;
	}
}
