package fatura;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import atividadesEconomicas.AtividadeEconomica;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import contribuintes.Contribuinte;
import contribuintes.ContribuinteIndividual;
import comparators.CompareFaturasByDate;
import comparators.CompareFaturasByValor;
import comparators.ComparePairDespesa;
import exceptions.FaturaNaoExisteException;
import exceptions.FaturaNaoPendenteException;
import exceptions.FaturaPendenteException;
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
    
    //Calcula a despesa deduzida de uma lista de faturas.
    public float getDeducao(List<Fatura> faturas){
        float count=0;
        for(Fatura f : faturas)
            count+=f.getDeducaoGlobal() * f.getDespesa(); 
        
        return count;
    }
    
    //Calcula a despesa deduzida de todos no agregado familiar.
    public float getNFAcumuladoAgregado(List<Integer> nifsAgregado, int nif){
        float Supercount=0;
        
        //meto o nif do contribuinte principal para não andar a repetir ciclos, embora chame de novo uma funcao.
        nifsAgregado.add(nif);
        for(Integer i : nifsAgregado){
             List<Fatura> tmp = getFaturasFromContribuinte(i);
                Supercount+=getDeducao(tmp);
        }
        return Supercount;
    }
    
    //Ponto 4. Devolve um Par<Faturas do c, despesa deduzida do c e agregado>.
    public Pair<List<Fatura>,Float> getDespesasAndDFAcumulado(Contribuinte c){
        int nif = c.getNif();
        
        List<Fatura> x = getFaturasFromContribuinte(nif);
        ContribuinteIndividual ci = (ContribuinteIndividual) c;
        float acumulado = getNFAcumuladoAgregado(ci.getNifsAgregado(), nif);
        
        Pair <List<Fatura>, Float> res = new Pair <List<Fatura>, Float> (x,acumulado);
        return res;
    }
    
    //Segundo o que entendi:
    //Na atividade económica da fatura está o coeficiente gerado por uma empresa e que vai entrar para a deducao.
    //O que diferencia este método do getDespesasAndDFAcumulado é o facto deste último eu ir buscar o campo deducaoGlobal à fatura
    //este campo tem o fator de deducao da empresa emitente assim como o coeficiente do contribuinte individual.
    //Só para administrador. Ponto 12
    public float getDFEmpresa(Contribuinte c){
        int nif = c.getNif();
        float count=0;
        //Contribuinte terá de ser Empresarial.
        //getFaturasFromContribuinte ou FromEmitente, embora tenha quase a certeza que as por emitir não entram para a deducao
        List<Fatura> x = getFaturasFromContribuinte(nif);
            for(Fatura f : x) {
                AtividadeEconomica a = f.getNaturezaDespesa();
                count+= f.getDespesa() * a.getCoef();
            }
        return count;
    }
    
    
    public List<Pair<Integer, Float>> getTenContribuintesMostDespesa(){
       HashMap<Integer, Pair <Integer,Float>> tmp = new HashMap<>();
       for(Fatura a : faturas.values()){
           int nif = a.getNifCliente();
           float despesa = a.getDespesa();
           if(tmp.containsKey(nif)){
               Pair <Integer, Float> older = tmp.get(nif);
               despesa += older.getValue();
           }
           Pair <Integer, Float> newer = new Pair <Integer, Float>(nif, despesa);
           tmp.put(nif,newer);  
       }    
       List<Pair <Integer, Float>> l = tmp.values().stream().collect(Collectors.toList());
       l.sort(new ComparePairDespesa());
       int size = l.size() > 10 ? 10 : l.size();
       return l.subList(0, size);
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
    
    public void associaAtividadeEconcomica(Contribuinte c, int numFatura, AtividadeEconomica a) throws FaturaNaoPendenteException, FaturaNaoExisteException{
        if(!this.faturasPendentes.containsKey(numFatura)) {
            if (this.faturas.containsKey(numFatura)) {
                throw(new FaturaNaoPendenteException(Integer.toString(numFatura)));             
            } else {
                throw(new FaturaNaoExisteException(Integer.toString(numFatura)));
            }
        }
        Fatura f = this.faturasPendentes.get(numFatura);
        f.setNaturezaDespesa(a);
        //Contribuinte tem de ser Individual
        f.setDeducaoGlobal(c);
        return;
    }
    
    public void corrigeAtividadeFatura(Contribuinte c, int numFatura, AtividadeEconomica nova) throws FaturaNaoExisteException, FaturaPendenteException {
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
        //O Zé esqueceu-se.
        f.setNaturezaDespesa(nova);
        //Contribuinte tem de ser individual. Vejam isto pff!!
        f.setDeducaoGlobal(c);
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
