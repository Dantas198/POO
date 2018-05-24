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
import contribuintes.ContribuinteEmpresarial;
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
    
    
    public static String listToString(List<?> list) {
    String result = "////////////////////////////////////\n";
    for (int i = 0; i < list.size(); i++) {
        result += list.get(i).toString();
        result += "////////////////////////////////////\n";
    }
    return result;
}
    
    
    /**
     * Devolve o número associado a uma determinada fatura.
     */
    public int getNumFaturas(){
        return numFaturas;
    }
    
    /**
     * Devolve uma fatura ao receber o seu numero
     */
    public Fatura getFatura(int num) throws FaturaNaoExisteException{
        if(!this.faturas.containsKey(num))
            throw new FaturaNaoExisteException(Integer.toString(num));
        return this.faturas.get(num);
    }
    
    /**
     * @param f, fatura a adicionar
     * Método que adiciona uma fatura ao HashMap faturasPendentes, caso haja uma
     * AtividadeEconomica na fatura
     */
    public void addFatura(Fatura f) {
        Fatura i = f.clone();
        i.setNumFatura(this.numFaturas);
        this.numFaturas++;
        if(i.getNaturezaDespesa()==null) {
            this.faturasPendentes.put(i.getNumFatura(), i);            
        }
        else {
            this.faturas.put(i.getNumFatura(), i);
        }
    }
    
    /**
     * @param nif do contribuinte
     * Devolve uma lista de faturas
     * @returns List<Fatura>
     */
    public List<Fatura> getFaturasFromContribuinte(int nif){
        return this.faturas.values().stream().filter(p -> p.getNifCliente()==nif).map(Fatura::clone).collect(Collectors.toList());
    }
    
    /**
     * @param nif do contribuinte
     * Devolve uma lista de faturas pendentes
     * @returns List<Fatura>
     */
    public List<Fatura> getFaturaPendentesFromContribuinte(int nif){
        return this.faturasPendentes.values().stream().filter(p -> p.getNifCliente()==nif).map(Fatura::clone).collect(Collectors.toList());
    }
    
    /**
     * @param nif do contribuinte
     * Devolve uma lista de faturas que foram emitidas por o contribuinte com aquele nif
     * @returns List<Fatura> 
     */
    public List<Fatura> getFaturasFromEmitente(int nif){
        List<Fatura> x = this.faturas.values().stream().filter(p -> p.getNifEmitente()==nif).map(Fatura::clone).collect(Collectors.toList());
        this.faturasPendentes.values().stream().filter(p -> p.getNifEmitente()==nif).map(Fatura::clone).map(p -> x.add(p));
        return x;
    }
    
    /**
     * @param faturas, uma lista de faturas 
     * Calcula a despesa deduzida de uma lista de faturas.
     * @returns count, total da despesa deduzida daquelas faturas
     */
    public float getDeducao(List<Fatura> faturas){
        float count=0;
        for(Fatura f : faturas)
            count+=f.getValorDeduzivel();
        
        return count;
    }
    
    /**
     * @param nifsAgregado, nifs do agregado familiar de um contribuinte
     * @param nif, nif do Contribuinte
     * Calcula a despesa deduzida de todos no agregado familiar.
     * @returns Supercount, total da despesa deduzida do agregado 
     */
    public float getNFAcumuladoAgregado(ContribuinteIndividual c){
        float Supercount=0;
        
        for(Integer i : c.getNifsAgregado()){
             List<Fatura> tmp = getFaturasFromContribuinte(i);
                Supercount+=getDeducao(tmp);
        }
        
        return Supercount;
    }
    
    /**
     * @param c, contribuinte
     * Devolve um par com a lista de faturas de um contribuinte assim como a sua despesa
     * deduzida, juntamente com a do agregado
     * @returns res, Pair<faturas, despesa deduzida>
     */
    public Pair<List<Fatura>,Float> getDespesasAndDFAcumulado(ContribuinteIndividual c){
        int nif = c.getNif();
        
        List<Fatura> x = getFaturasFromContribuinte(nif);
        float acumulado = getNFAcumuladoAgregado(c);
        
        Pair <List<Fatura>, Float> res = new Pair <List<Fatura>, Float> (x,acumulado);
        return res;
    }
    
    /**
     * @param c, contribuinte
     * Calcula o montante de deducoes fiscais fornecidas de todas as despesas emitidas por uma empresa
     * @returns count, total de deducoes
     */
    public float getDFEmpresa(ContribuinteEmpresarial c){
        int nif = c.getNif();
        float count=0;
        //getFaturasFromContribuinte ou FromEmitente, embora tenha quase a certeza que as por emitir não entram para a deducao
        List<Fatura> x = getFaturasFromContribuinte(nif);
            for(Fatura f : x) {
                AtividadeEconomica a = f.getNaturezaDespesa();
                count+= f.getDespesa() * (a.getCoef() + c.reducaoImposto());
            }
        return count;
    }       

    
    /**
     * Devolve uma lista com as faturas que o cliente e a empresa tem em comum
     */
    public List<Fatura> getFaturasFromEmpresa(int nifCliente, int nifEmpresa){
        return this.getFaturasFromContribuinte(nifCliente).stream().filter(f -> f.getEmitente().getNif() == nifEmpresa).collect(Collectors.toList());
    }
    
     /**
     * Devolve um HashMap temporário com pares de nif e despesa desse contribuinte, caso queiramos
     * ir buscar a despesa de um contribuinte individual
     */
    public void makeHashIndividual(HashMap<Integer, Pair <Integer,Float>> tmp){
        for(Fatura a : faturas.values()){
            if(a.isClientIndividual()){
                int nif = a.getNifCliente();
                float despesa = a.getDespesa();
                if(tmp.containsKey(nif)){
                    Pair <Integer, Float> older = tmp.get(nif);
                    despesa += older.getValue();
                 }
                else {
                    Pair <Integer, Float> newer = new Pair <Integer, Float>(nif, despesa);
                    tmp.put(nif,newer);}  
        }}
     }
     
     /**
      * Devolve um HashMap temporário com pares de nif e despesa desse contribuinte, caso queiramos
      * ir buscar a faturacao de uma impresa
      */
    public void makeHashMostSpender(HashMap<Integer, Pair <Integer,Float>> tmp, HashMap<Integer, Fatura> faturas){
       for(Fatura a : faturas.values()){
           int nif = a.getNifCliente();
           float despesa = a.getDespesa();
               if(tmp.containsKey(nif)){
                   Pair <Integer, Float> older = tmp.get(nif);
                   despesa += older.getValue();
               }
               else {
                   Pair <Integer, Float> newer = new Pair <Integer, Float>(nif, despesa);
                   tmp.put(nif,newer);}  
       }
    }
     
    /**
     * @param x, tamanho da lista pretendida
     * @param type, 1 se queremos os contribuintes com mais despesa, 2
     * se queremos as empresas com maior faturacao 
     * Devolve uma lista com os contribuintes com mais despesas.
     * @returns Lista<Pair<nif, despesa>>
     */
    public List<Pair<Integer, Float>> getMostSpenders(int x){
        HashMap<Integer, Pair <Integer,Float>> tmp = new HashMap<>();
        
        makeHashMostSpender(tmp, this.faturas);
        makeHashMostSpender(tmp, this.faturasPendentes);

        List<Pair <Integer, Float>> l = tmp.values().stream().collect(Collectors.toList());
        l.sort(new ComparePairDespesa());
        int size = l.size() > x-1 ? x-1 : l.size();
        return l.subList(0, size);
    }
    
    /**
     * @param nifn nif do contribuinte
     * @param beg, data inicial
     * @param end, data final
     * Apresenta a lista de todas as faturas entre beg e end
     * @returns List<Fatura>
     */
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
    
    /**
     * @param c, contribuinte
     * @param numFatura, numero de cada fatura
     * @param a, atividade economica
     * Associa uma atividade economica a uma fatura caso ela não esteja ja emitida
     */
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
        //f.setDeducaoGlobal(c);
        return;
    }
    
    public List<Pair<Integer,Pair<AtividadeEconomica,AtividadeEconomica>>> getChangestoFatura(int numfatura) {
		List<Pair<Integer,Pair<AtividadeEconomica,AtividadeEconomica>>>res = new ArrayList<>();
		this.correcoes.stream().filter(p -> p.getKey()==numfatura).forEach(p -> res.add(p));
    	return res;
	}
    
    /**
     * @param c, contribuinte
     * @param numFatura, numero de cada fatura
     * @param nova, atividade economica
     * Corrige uma atividade economica, ou seja, atualiza a atividade nas faturas pendentes
     */
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
        //f.setDeducaoGlobal(c);
        this.correcoes.add(change);
    }
    
    /**
     * @param nifEmitente, nif do emitente
     * Devolve o total faturado por um contribuinte
     * @returns total faturado
     */
    public float totalFaturado(ContribuinteEmpresarial c, LocalDateTime beg, LocalDateTime end) {
        int nif = c.getNif();
        List<Fatura> x = this.getFaturasFromEmitenteBetweenDate(nif, beg, end);
        return (float) x.stream().mapToDouble(Fatura::getDespesa).sum();
    }
    
    //Tem de ser uma empresa
    /**
     * @param nif, nif contribuinte
     * Devolve uma lista de faturas ordendas por data
     * @returns x, List<Fatura>
     */
    public List<Fatura> getFaturasByDate(ContribuinteEmpresarial c){
        int nif = c.getNif();
        List<Fatura> x = getFaturasFromEmitente(nif);
        x.sort(new CompareFaturasByDate());
        return x;
    }
    
    //Tem de ser um empresa
    /**
     * @param nif, nif contribuinte
     * Devolve uma lista de faturas ordenadas por valor
     * @returns x, List<Fatura>
     */
    public List<Fatura> getFaturasByValor(ContribuinteEmpresarial c){
        int nif = c.getNif();
        List<Fatura> x = getFaturasFromEmitente(nif);
        x.sort(new CompareFaturasByValor());
        return x;
    }
    
    //tem de ser nif de empresa.
    //A lista está disposta da seguinte maneira:
    //      Se tivermos 3 contribuintes: A,B e C e cada um com 3 faturas com valores: x > y > z, 
    //      O resultado vai ser {Ax, Ay, Az, Bx, By, Bz, Cx, Cy, Cz}
    /**
     * @param nif, nif contribuinte
     * Devolve uma lista de faturas ordenadas por valor decrescente
     * mas tambem agupadas por nif
     * @returns x, List<Fatura>
     */
    public List<Fatura> getFaturasByValorDecrescente(ContribuinteEmpresarial c){
        int nif = c.getNif();
        List<Fatura> x = getFaturasFromEmitente(nif);
        x.sort(Comparator.comparing(Fatura::getNifCliente)
                         .thenComparing(Fatura::getDespesa).reversed()); 
        return x;
    }
    
    /**
     * Devolve um ArrayList com todas os pares numFatura, AtividadeAntiga -> Atividade nova
     * */
    private ArrayList<Pair<Integer, Pair<AtividadeEconomica, AtividadeEconomica>>> getCorrecoes() {
		ArrayList<Pair<Integer, Pair<AtividadeEconomica, AtividadeEconomica>>> res = new ArrayList<>();
		for (Pair<Integer, Pair<AtividadeEconomica, AtividadeEconomica>> pair : correcoes) {
			AtividadeEconomica p1 = pair.getValue().getKey().clone();
			AtividadeEconomica p2 = pair.getValue().getValue().clone();
			Pair<Integer, Pair<AtividadeEconomica, AtividadeEconomica>> r= new Pair<>(pair.getKey(), new Pair<>(p1,p2));
			res.add(r);
		}
		return res;
	}

    /**
     * Devolve todas as faturas nao pendentes
     * */
	private HashMap<Integer, Fatura> getFaturasValidas() {
		HashMap<Integer, Fatura> res = new HashMap<>();
		for(Fatura f : this.faturas.values()) {
			res.put(f.getNumFatura(), f.clone());
		}
		return res;
	}
	
	/**
     * Devolve todas as faturas pendentes
     * */
	private HashMap<Integer, Fatura> getFaturasPendentes() {
		HashMap<Integer, Fatura> res = new HashMap<>();
		for(Fatura f : this.faturasPendentes.values()) {
			res.put(f.getNumFatura(), f.clone());
		}
		return res;
	}
	
    /**
     * Construtor vazio de Faturas
     */
    public Faturas() {
        this.faturas = new HashMap<>();
        this.faturasPendentes = new HashMap<>();
        this.correcoes = new ArrayList<>();
        this.numFaturas = 0;
    }

    public Faturas(Faturas f) {
    	super();
		this.faturas = getFaturasPendentes();
		this.faturasPendentes = getFaturasValidas();
		this.correcoes = getCorrecoes();
		this.numFaturas = getNumFaturas();
	}

    public Faturas clone() {
		return new Faturas(this);
	}
	

}
