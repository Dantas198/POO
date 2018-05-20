package contribuintes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;
import java.util.stream.Collectors;

import atividadesEconomicas.AtividadeEconomica;
import deductors.Deductor;
import deductors.DeductorIndividual;
import moradas.Morada;

public class ContribuinteIndividual extends Contribuinte implements Serializable,BeneficioFiscal,ContribuinteDedutor {
    private int numDependentesAgregado;
    private ArrayList<Integer> nifsAgregado; //Fazer Getters e Setters
    private float coefFiscal;
    private static double benificioFamNumerosas = 0;
    //AtividadesEconomicas 
    private HashMap<String,AtividadeEconomica> actDeduziveis; //Fazer Getters e Setters
    
    public int getNumDependentesAgregado() {
        return numDependentesAgregado;
    }
    public void setNumDependentesAgregado(int numDependentesAgregado) {
        this.numDependentesAgregado = numDependentesAgregado;
    }

    public float getCoefFiscal() {
        return coefFiscal;
    }
    public void setCoefFiscal(float coefFiscal) {
        this.coefFiscal = coefFiscal;
    }
    public void setNifsAgregado(List<Integer> nifs) {
        for(Integer nif : nifs){
            this.nifsAgregado.add(nif);
        }
    }
    public List<Integer> getNifsAgregado() {
        return this.nifsAgregado.stream().collect(Collectors.toList());
    }
    public Map<String, AtividadeEconomica> getActDeduziveis() {
        Map<String, AtividadeEconomica> res = new HashMap<String,AtividadeEconomica>();
        for(AtividadeEconomica v : this.actDeduziveis.values())
            res.put(v.getNomeAtividade(), v.clone());
        return res;
    }
    private void setActDeduziveis(Map<String, AtividadeEconomica> actDeduziveis2) {
        this.actDeduziveis = new HashMap<String, AtividadeEconomica>();
        for(Entry<String, AtividadeEconomica> e : actDeduziveis.entrySet())
            this.actDeduziveis.put(e.getKey(), e.getValue().clone());
    }
    public ContribuinteIndividual() {
        // TODO Auto-generated constructor stub
        super();
        this.actDeduziveis = new HashMap<>();
        this.nifsAgregado = new ArrayList<Integer>();
        this.coefFiscal = 0;
        this.numDependentesAgregado = 0;
    }
    
    public ContribuinteIndividual(String nome, int nif, String email, Morada morada, String password,float coefFiscal) {
        super(nome,nif,email,morada,password);
        this.coefFiscal = coefFiscal; 
        this.actDeduziveis = new HashMap<>();
    }
    
    public ContribuinteIndividual(ContribuinteIndividual a){
        super(a);
        this.setNifsAgregado(a.getNifsAgregado());
        this.numDependentesAgregado = a.getNumDependentesAgregado();
        this.coefFiscal = a.getCoefFiscal();
        this.setActDeduziveis(a.getActDeduziveis());
    }
    
    @Override
    public Contribuinte clone() {
        // TODO Auto-generated method stub
        return new ContribuinteIndividual(this);
    }
	@Override
	public double reducaoImposto() {
		if (this.getNumDependentesAgregado()>=5)
			return ContribuinteIndividual.getBenificioFamNumerosas();
		return 0;
	}
	
	public static double getBenificioFamNumerosas() {
		return benificioFamNumerosas;
	}
	
	public static void setBenificioFamNumerosas(double benificioFamNumerosas) {
		ContribuinteIndividual.benificioFamNumerosas = benificioFamNumerosas;
	}
	
	@Override
	public Deductor getDeductor() {
		return new DeductorIndividual(this,null);
	}
}
