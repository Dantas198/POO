package Fatura;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import Contribuintes.ContribuinteEmpresarial;

public class Faturas implements Serializable {
	private HashMap<Integer, Fatura> faturas;
	private HashMap<Integer, Fatura> faturasPendentes;
	
	public List<Fatura> getFaturasFromContribuinte(int nif){
		return this.faturas.values().stream().filter(p -> p.getNifCliente()==nif).map(Fatura::clone).collect(Collectors.toList());
	}
	public List<Fatura> getFaturaPendentesFromContribuinte(int nif){
		return this.faturasPendentes.values().stream().filter(p -> p.getNifCliente()==nif).map(Fatura::clone).collect(Collectors.toList());
	}
	public List<Fatura> getFaturasFromEmitente(int nif){
		List<Fatura> x = this.faturas.values().stream().filter(p -> p.getNifEmitente()==nif).map(Fatura::clone).collect(Collectors.toList());
		this.faturasPendentes.values().stream().filter(p -> p.getNifCliente()==nif).map(Fatura::clone).map(p -> x.add(p));
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
	
	public float totalFaturado(ContribuinteEmpresarial emitente) {
		List<Fatura> x = this.getFaturasFromEmitente(emitente.getNif());
		return (float) x.stream().mapToDouble(Fatura::getDespesa).sum();
	}
}
