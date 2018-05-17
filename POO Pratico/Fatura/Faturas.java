package Fatura;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import Comparators.CompareFaturasByValor;
import Comparators.CompareFaturasByDate;
import Comparators.ComparePairDespesa;
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
    
    public int getNumFaturas(){
        return numFaturas;
    }
    
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
    
    //Pair<Nif, Despesa>
    //Pode estar uma merda, mas fds....ver se � preciso dar clone.....
    public Set<Pair <Integer,Float>> getTenContribuintesMostDespesa(){
       HashMap<Integer, Pair <Integer,Float>> tmp = new HashMap<>();
           for(Fatura a : faturas.values()){
               int nif = a.getNifCliente();
               if(tmp.containsKey(nif)){
                   Pair <Integer, Float> older = tmp.get(nif);
                   Pair <Integer, Float> newer = new Pair <Integer, Float>(nif, older.getValue() + a.getDespesa());
                   tmp.put(nif,newer);
               }
               else {
                   Pair <Integer, Float> newer = new Pair <Integer, Float>(nif, a.getDespesa());
                   tmp.put(nif,newer);
               }
           }
            
       TreeSet< Pair <Integer, Float>> x;
       x = tmp.values().stream().collect(Collectors.toCollection(()-> new TreeSet< Pair <Integer, Float>> (new ComparePairDespesa()))); 
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
    
    //Tem de ser uma empresa
    public List<Fatura> getFaturasByDate(int nif){
        List<Fatura> x = getFaturasFromEmitente(nif);
        x.sort(new CompareFaturasByDate());
        return x;
    }
    //Tem de ser um empresa
    public List<Fatura> getFaturasByValor(int nif){
        List<Fatura> x = getFaturasFromEmitente(nif);
        x.sort(new CompareFaturasByValor());
        return x;
    }
    
    
    //tem de ser nif de empresa.
    //A lista está disposta da seguinte maneira:
    //      Se tivermos 3 contribuintes: A,B e C e cada um com 3 faturas com valores: x > y > z, 
    //      O resultado vai ser {Ax, Ay, Az, Bx, By, Bz, Cx, Cy, Cz}
    public List<Fatura> getFaturasByValorDecrescente(int nif){
        List<Fatura> x = getFaturasFromEmitente(nif);
        x.sort(Comparator.comparing(Fatura::getNifCliente)
                         .thenComparing(Fatura::getDespesa).reversed()); 
        return x;
    
    }
    
    public Faturas() {
        this.faturas = new HashMap<>();
        this.faturasPendentes = new HashMap<>();
        this.correcoes = new ArrayList<>();
        this.numFaturas = 0;
    }
}
