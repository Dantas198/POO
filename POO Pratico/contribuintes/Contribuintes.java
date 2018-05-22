package contribuintes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Comparator;

import exceptions.ContribuinteDoesntExistException;

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
                return c;
        }
        return null;
    }
    
    /**
     * @param x, tamanho da lista
     * Devolve a lista das empresas com maior numero de faturas emitidas
     */
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
    
    /**
     * Construtor vazio
     */
    public Contribuintes() {
        // TODO Auto-generated constructor stub
        contribuintes = new HashMap<Integer,Contribuinte>();
    }
}
