
/**
 * Write a description of class ContribuinteIndividual here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.Arrays;
public class ContribuinteIndividual extends Contribuinte
{
    
    private int numAgregado;
    private int[] nifsAgregado;
    private float coefFiscal;
    private int[] atividadesEconomicas; //classe para realizar operações???
    
    /**
     * Construtor de objetos. Este construtor recebe as variáveis como argumentos. 
     */
    public ContribuinteIndividual(String nome, String email, int nif, String morada, String password)
    {
        // initialise instance variables
        this.setName(nome);
        this.setEmail(email);
        this.setNIF(nif);
        this.setMorada(morada);
        this.setPassword(password);
    }
    
    /**
     * Construtor cópia de objetos.
     */
    public ContribuinteIndividual(ContribuinteIndividual c){
        
        this.setName(c.getName());
        this.setEmail(c.getEmail());
        this.setNIF(c.getNIF());
        this.setMorada(c.getMorada());
       // this.setPassword(c.getPassword());
        this.setNumAgregado(c.getNumAgregado());
        this.nifsAgregado = c.getNifsAgregado();
        this.setCoefFiscal(c.getCoefFiscal());
        this.atividadesEconomicas = c.getAtividadesEconomicas();
    }
    
    /**
     * Clone de um objeto.
     */
    public ContribuinteIndividual clone( ContribuinteIndividual c){
        return new ContribuinteIndividual(this); 
    }
    
    
    /**
     * Verifica se dois objetos são iguais.
     */
    public boolean equals (Object o){
        if(o==this) return true;
        if(o==null || (o.getClass() != this.getClass())) return false;
       
        ContribuinteIndividual c = (ContribuinteIndividual) o;
        return this.getNIF() == c.getNIF();
    }
    
    /**
     * Converte as variáveis em String.
     */
    public String toString(){
    StringBuilder sb = new StringBuilder("Individual...Nif: ");
        sb.append(this.getNIF());
        sb.append(", Nome: ");
        sb.append(this.getName());
        sb.append(", Email: ");
        sb.append(this.getEmail());
        sb.append(", Morada: ");
        sb.append(this.getMorada());
        //sb.append(" ,Password: ");
        sb.append(" ,NúmerAgregado: ");
        sb.append(this.numAgregado);
        sb.append(" ,NifsAgregado: ");
        sb.append(Arrays.toString(this.nifsAgregado));
        sb.append(" ,CoeFiscal: ");
        sb.append(this.coefFiscal);
        sb.append(" ,Ativ.Economicas: ");
        sb.append(Arrays.toString(this.atividadesEconomicas));
       
        return sb.toString();
    }
    
    /**
     * Retorna uma cópia do array de atividades económicas
     * @return atividadesEconomicas.
     */
    public int[] getAtividadesEconomicas(){
        return Arrays.copyOf(this.atividadesEconomicas, this.atividadesEconomicas.length);
    }
    
    /**
     *Retorna o coeficiente fiscal.
     *@return coefFiscal.
     */
    public float getCoefFiscal(){
        return this.coefFiscal;
    }
    
    /**
     * Retorna uma cópia array de nif's do agregado
     * @return nifAgregado. 
     */
    public int[] getNifsAgregado(){
        return  Arrays.copyOf(this.nifsAgregado, this.nifsAgregado.length);
    }
    
    /**
     * Retorna o número do agregado.
     * @return numAgregado.
     */
    public int getNumAgregado(){
        return this.numAgregado;
    }
    
    /**
     * Altera o numero do agregado.
     * @param a
     */
    public void setNumAgregado(int a){
        this.numAgregado=a;
    }
    
    /**
     * Altera o coeficiente fiscal
     * @param c
     */
    public void setCoefFiscal(float c){
        this.coefFiscal = c;
    }
}
