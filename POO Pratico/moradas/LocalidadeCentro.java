package moradas;

public class LocalidadeCentro extends Localidade {
    private double beneficioPercentagem;
    
    /**
     * Calcula o valor do beneficio da empresa por estar numa regiao centro
     */
    
    public double getBeneficioPercentagem() {
        return beneficioPercentagem;
    }
    
    /**
     * Construtor parametrizado de LocalidadeCentro
     */
    public LocalidadeCentro(String localidade, double beneficio) {
        super(localidade);
        this.beneficioPercentagem = beneficio;
    }
    
    /**
     * Clone de LocalidadeCentro
     */
    public LocalidadeCentro clone() {
        return new LocalidadeCentro(this.getLocalidade(),this.beneficioPercentagem);
    }
}
