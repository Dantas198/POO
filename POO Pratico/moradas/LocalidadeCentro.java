package moradas;

public class LocalidadeCentro extends Localidade {

	public LocalidadeCentro(String localidade) {
		super(localidade);
	}

	@Override
	public LocalidadeCentro clone() {
		return new LocalidadeCentro(this.getLocalidade());
	}
}
