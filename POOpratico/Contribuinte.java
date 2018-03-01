
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
    //Email
    private String email;
    //Alterar morada para struct later maybe
    private String morada;
    private String password;
    
/**
 * Altera o NIF
 * @param n NIF
 */
    public void setNIF(int n){
        this.nif= n;
    }
/**
 * Retorna o NIF
 * @return NIF
 */
    public int getNIF(){
        return this.nif;
    }
/**
 * Altera o nome
 * @param n Nome
 */
    public void setName(String n){
        this.nome= n;
    }
/**
 * Retorna o nome
 * @return nome
 */    
    public String getName(){
        return this.nome;
    }
/**
 * Altera o email
 * @param e Email
 */
    public void setEmail(String e){
        this.email= e;
    }
/**
 * Retorna o email
 * @return Email
 */
    public String getEmail(){
        return this.email;
    }
/**
 * Altera a Morada
 * @param m Morada
 */
    public void setMorada(String m){
        this.morada= m;
    }
/**
 * Retorna a Morada
 * @return Morada
 */    
    public String getMorada(){
        return this.morada;
    }
/**
 * Altera a password
 */
    public void setPassword(String pass){
        this.password = pass;
    }
/**
 * Verifica se pass é a password
 * @param pass Password a ser testada
 * @return True se a password for igual, false se for diferente
 */
    public boolean isPassword(String pass){
        return this.password.equals(pass);
    }
    
/**
 * Constructor for objects of class Contribuinte
 */      
    public Contribuinte()
    {
    }
}
