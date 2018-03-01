
/**
 * Write a description of class ContribuinteIndividual here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ContribuinteIndividual extends Contribuinte
{
    /**
     * Constructor for objects of class ContribuinteIndividual
     */
    public ContribuinteIndividual()
    {
        // initialise instance variables        
    }
    
    public ContribuinteIndividual(String nome, String email, int nif, String morada, String password)
    {
        // initialise instance variables
        this.setName(nome);
        this.setEmail(email);
        this.setNIF(nif);
        this.setMorada(morada);
        this.setPassword(password);
    }

}
