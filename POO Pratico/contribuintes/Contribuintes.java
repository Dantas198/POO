package contribuintes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Comparator;

public class Contribuintes implements Serializable{
    HashMap<Integer, Contribuinte> contribuintes;//Keys Nifs dos contribuintes
    
    public boolean existeContribuinte(Contribuinte c){
        return contribuintes.keySet().stream().anyMatch((nif) -> c.getNif() == nif);
    }
    
    public void addContribuinte(Contribuinte c) {
        contribuintes.put(c.getNif(), c.clone());
    }
    
    public void addContribuintes(Set<Contribuinte> c){
        c.stream().map(p -> contribuintes.put(p.getNif(), p.clone()));      
    }
    
    public Contribuinte login(int nif, String password) {
        if (contribuintes.containsKey(nif)){
            Contribuinte c = contribuintes.get(nif);
            if(c.isPassword(password))
                return c;
        }
        return null;
    }
    
    public List<ContribuinteEmpresarial> getXMostFaturas(int x){
        List<ContribuinteEmpresarial> tmp;
        tmp = this.contribuintes.values().stream()
                  .filter(c -> c instanceof ContribuinteEmpresarial)
                  .map(p -> (ContribuinteEmpresarial)p.clone())
                  .collect(Collectors.toList());
        tmp.sort(Comparator.comparing(ContribuinteEmpresarial::getCountFaturas).reversed());
        
        List<ContribuinteEmpresarial> result =tmp.subList(0, x-1);
        return result;
    }
    
    public Contribuintes() {
        // TODO Auto-generated constructor stub
        contribuintes = new HashMap<Integer,Contribuinte>();
    }
}
