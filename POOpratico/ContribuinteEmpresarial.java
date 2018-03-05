import java.util.ArrayList;
/**
 * Write a description of class ContribuinteEmpresarial here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ContribuinteEmpresarial extends Contribuinte
{
    // instance variables - replace the example below with your own
    private int x;
    private ArrayList<String> atividadesEconomicas; //Alterar tipo maybe class
    private int coefFiscal;
    /**
     * Constructor for objects of class ContribuinteEmpresarial
     */
    public ContribuinteEmpresarial()
    {
        // initialise instance variables
        x = 0;
    }
    
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
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
}
