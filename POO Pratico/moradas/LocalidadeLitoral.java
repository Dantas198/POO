package moradas;

public class LocalidadeLitoral extends Localidade {

	public LocalidadeLitoral(String localidade) {
		super(localidade);
	}

	@Override
	public LocalidadeLitoral clone() {
		return new LocalidadeLitoral(this.getLocalidade());
	}
}