package moradas;

public class LocalidadeLitoral extends Localidade {
    
    /**
     * Construtor parametrizado de LocalidadeLitoral
     */
    public LocalidadeLitoral(String localidade) {
        super(localidade);
    }
    
    /**
     * Clone de LocalidadeLitoral
     */
    public LocalidadeLitoral clone() {
        return new LocalidadeLitoral(this.getLocalidade());
    }
}