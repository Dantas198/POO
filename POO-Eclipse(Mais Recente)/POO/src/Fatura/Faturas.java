package Fatura;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Faturas {
	HashMap<Integer, Fatura> faturas;
	HashMap<Integer, Fatura> faturasPendentes;
	
	List<Fatura> getFaturasFromContribuinte(int nif){
		return this.faturas.values().stream().filter(p -> p.getNifCliente()==nif).map(Fatura::clone).collect(Collectors.toList());
	}
	
	List<Fatura> getFaturaPendentesFromContribuinte(int nif){
		return this.faturas.values().stream().filter(p -> p.getNifCliente()==nif).map(Fatura::clone).collect(Collectors.toList());
	}
}
