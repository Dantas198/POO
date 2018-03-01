
/**
 * Write a description of class Contribuinte here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Contribuinte
{
    //Número Fiscal(NIF)
    private int nif;
    
    //Nome do Contribuinte 
    private String nome;
    
    //Alterar morada para struct later maybe
    private String morada;
    
    private String password;
    
    
    public void setNIF(int n){
        this.nif= n;
    }
    
    public int getNIF(){
        return(this.nif);
    }
    
    public void setName(String n){
        this.nome= n;
    }
    
    public String getName(){
        return(this.nome);
    }
    /**
     * Constructor for objects of class Contribuinte
     */      
    public Contribuinte()
    {
    }
}
