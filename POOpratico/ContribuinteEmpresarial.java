import java.util.ArrayList;
/**
 * Write a description of class ContribuinteEmpresarial here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ContribuinteEmpresarial extends Contribuinte
{
    private ArrayList<String> atividadesEconomicas; //Alterar tipo maybe class
    private int coefFiscal;
    /**
     * Constructor for objects of class ContribuinteEmpresarial
     */
    public ContribuinteEmpresarial()
    {
        // initialise instance variables
        // x = 0;
    }
    
    /**
     * Construtor de objetos da classe ContribuinteEmpresarial. Recebe como argumentos as variáveis.
     */
    public ContribuinteEmpresarial(String nome, String email, int nif, String morada, String password)
    {
        // initialise instance variables
        this.setName(nome);
        this.setEmail(email);
        this.setNIF(nif);
        this.setMorada(morada);
        this.setPassword(password);
    }
    
    /**
     * Construtor de cópia de objetos da classe ContribuinteEmpresarial.
     */
    public ContribuinteEmpresarial(ContribuinteEmpresarial c){
        this.setName(c.getName());
        this.setEmail(c.getEmail());
        this.setNIF(c.getNIF());
        this.setMorada(c.getMorada());
        //this.setPassword(c.getPassword()); --perguntar ao stor.
        this.atividadesEconomicas = (c.getAtividadesEconomicas());
        this.setCoefFiscal(c.getCoefFiscal());
    
    }
    
    /**
     * Retorna as atividades economicas.
     * @return atividadesEconomicas.
     */
    public ArrayList<String> getAtividadesEconomicas(){
        return new ArrayList<>(this.atividadesEconomicas);
    }
    
    /**
     * Adiciona uma atividadeEconomica.
     * @param a String.
     */
    public void addAtividadesEconomicas(String a){
        this.atividadesEconomicas.add(a);
    }
    
    /**
     * Remove uma atividadeEconomica.
     * @param a String.
     */
    public void removeAtividadesEconomicas(String a){
        this.atividadesEconomicas.remove(a);
    }
    
    /**
     * Retorna o coeficiente fiscal.
     * @return coefFiscal.
     */
    public int getCoefFiscal (){
        return this.coefFiscal;
    }
    
    /**
     * Altera o coeficiente fiscal.
     * @param coefFiscal coeficiente fiscal.
     */
    public void setCoefFiscal (int coefFiscal){
        this.coefFiscal = coefFiscal;
    }
    
    /**
     * Método equals para os objetos da classe ContribuinteEmpresarial.
     */
    public boolean equals(Object o){
       if(o==this) return true;
       if(o==null || (o.getClass() != this.getClass())) return false;
       
       ContribuinteEmpresarial c = (ContribuinteEmpresarial) o;
       return this.getNIF() == c.getNIF();
              //this.coefFiscal == c.getCoefFiscal() &&
              //this.atividadesEconomicas.equals(c.getAtividadesEconomicas()))      
    }
    
    /**
     * Clonagem do objeto da classe ContribuinteEmpresarial.
     */
    public ContribuinteEmpresarial clone(){
        return new ContribuinteEmpresarial(this);
    } 
    
    /**
     * Passagem das variáveis para Strings.
     */
    public String toString(){
        StringBuilder sb = new StringBuilder("Empresarial...Nif: ");
        sb.append(this.getNIF());
        sb.append(", Nome: ");
        sb.append(this.getName());
        sb.append(", Email: ");
        sb.append(this.getEmail());
        sb.append(", Morada: ");
        sb.append(this.getMorada());
        //sb.append(" ,Password: ");
        //sb.append(" ,Atividades Economicas: ");
        //sb.append(this.atividadesEconomicas.toString());
        sb.append(", Coeficiente Fiscal: ");
        sb.append(this.coefFiscal);
        
        return sb.toString();
    }
}
