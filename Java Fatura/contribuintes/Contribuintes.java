package contribuintes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Comparator;

import exceptions.ContribuinteDoesntExistException;
import exceptions.ContribuinteNaoIndividualException;
import moradas.Morada;

public class Contribuintes implements Serializable{
    HashMap<Integer, Contribuinte> contribuintes;//Keys Nifs dos contribuintes
    
    /**
     * Verifica a existencia de um contribuinte 
     */
    public boolean existeContribuinte(Contribuinte c){
        return contribuintes.containsKey(c.getNif());
    }
    
    
    /**
     * Adiciona um contribuinte 
     */
    public Contribuinte getContribuinte(int nif) throws ContribuinteDoesntExistException{
        if(!contribuintes.containsKey(nif)){
            throw new ContribuinteDoesntExistException(Integer.toString(nif));
        }
        return contribuintes.get(nif);
    }
    
    /**
     * Adiciona um Set de contribuintes
     */
    public void addContribuintes(Set<? extends Contribuinte> c){
        
        c.stream().map(p -> contribuintes.put(p.getNif(), p.clone()));      
    }
    
    public void addContribuinte(Contribuinte c){
        if(c instanceof ContribuinteIndividual)
                ((ContribuinteIndividual) c).setCoefFiscal( 0.20f);
        contribuintes.put(c.getNif(), c.clone());
    }
    
    /**
     * Efetua o login, ou seja, verifica se o contribuinte existe na estrutura de dados
     * @returns, contribiunte, caso existe
     */
    public Contribuinte login(int nif, String password) {
        if (contribuintes.containsKey(nif)){
            Contribuinte c = contribuintes.get(nif);
            if(c.isPassword(password))
                return c.clone();
        }
        return null;
    }
    
    /**
     * @param x, tamanho da lista
     * Devolve a lista das empresas com maior numero de faturas emitidas
     */
    /*
    public List<ContribuinteEmpresarial> getXMostFaturas(int x){
        List<ContribuinteEmpresarial> tmp;
        tmp = this.contribuintes.values().stream()
                  .filter(c -> c instanceof ContribuinteEmpresarial)
                  .map(p -> (ContribuinteEmpresarial)p.clone())
                  .collect(Collectors.toList());
        tmp.sort(Comparator.comparing(ContribuinteEmpresarial::getCountFaturas).reversed());
        
        
        int size = tmp.size() > x-1 ? x-1 : tmp.size();
        List<ContribuinteEmpresarial> result =tmp.subList(0, size);
        return result;
    }
    */
    
    /**
     * @param x, tamanho da lista
     * Devolve a lista das empresas com maior lucro.
     */
    public List<ContribuinteEmpresarial> getXMostFaturado(int x){
        List<ContribuinteEmpresarial> tmp;
        tmp = this.contribuintes.values().stream()
                  .filter(c -> c instanceof ContribuinteEmpresarial)
                  .map(p ->(ContribuinteEmpresarial)p.clone())
                  .collect(Collectors.toList());
        tmp.sort(Comparator.comparing(ContribuinteEmpresarial::getLucro).reversed());
        
        int size = tmp.size() > x ? x : tmp.size();
        List<ContribuinteEmpresarial> result = tmp.subList(0, size);
        return result;
    }
    
    public void addNewContribuinteToAgregado(int nifdeAgregado, String nome, int nif, String email, String password) throws ContribuinteDoesntExistException, ContribuinteNaoIndividualException {
        if(!this.contribuintes.containsKey(nifdeAgregado))
            throw new ContribuinteDoesntExistException(Integer.toString(nifdeAgregado));
        Contribuinte s = this.contribuintes.get(nifdeAgregado);
        if (!(s instanceof ContribuinteIndividual)) {
            throw new ContribuinteNaoIndividualException(Integer.toString(nifdeAgregado));  
        }
        ContribuinteIndividual novo = (ContribuinteIndividual) s.clone();
        novo.setNome(nome);
        novo.setNif(nif);
        novo.setEmail(email);
        novo.setPassword(password);
        List<Integer> x = novo.getNifsAgregado();
        for (Integer i : x) {
            Contribuinte c = (ContribuinteIndividual) this.contribuintes.get(i);
            if (c instanceof ContribuinteIndividual) {
                ContribuinteIndividual n = (ContribuinteIndividual) c;
                n.addAgregado(nif);
            }
        }
        novo.addAgregado(nif);
        this.contribuintes.put(nif, novo);
    }
    
    public void addDependenteToAgregado(int nifdeAgregado, int numDependentes) throws ContribuinteNaoIndividualException, ContribuinteDoesntExistException {
        if(!this.contribuintes.containsKey(nifdeAgregado))
            throw new ContribuinteDoesntExistException(Integer.toString(nifdeAgregado));
        Contribuinte s = this.contribuintes.get(nifdeAgregado);
        if (!(s instanceof ContribuinteIndividual)) {
            throw new ContribuinteNaoIndividualException(Integer.toString(nifdeAgregado));  
        }
        List<Integer> x;
        if (s instanceof ContribuinteIndividual) {
            ContribuinteIndividual n = (ContribuinteIndividual) s;
            x = n.getNifsAgregado();
        }
        else
            x = new ArrayList<>();
        for (Integer i : x) {
            Contribuinte c = (ContribuinteIndividual) this.contribuintes.get(i);
            if (c instanceof ContribuinteIndividual) {
                ContribuinteIndividual n = (ContribuinteIndividual) c;
                n.setNumDependentesAgregado(n.getNumDependentesAgregado()+numDependentes);
            }
        }
        
    }
    
    /**
     * Retorna uma copia do HashMap dos contribuintes
     * */
    public HashMap<Integer, Contribuinte> getContribuintes() {
        HashMap<Integer,Contribuinte> res = new HashMap<>();
        for(Contribuinte c : this.contribuintes.values()) {
            res.put(c.getNif(),c.clone());
        }
        return res;
    }
    
    /**
     * Construtor vazio
     */
    public Contribuintes() {
        contribuintes = new HashMap<Integer,Contribuinte>();
    }
    
    public Contribuintes(Contribuintes c) {
        super();
        this.contribuintes = c.getContribuintes();
    }


    @Override
    public Contribuintes clone(){
        return new Contribuintes(this);
    }
}
