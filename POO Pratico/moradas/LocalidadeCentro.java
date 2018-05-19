package moradas;

public class LocalidadeCentro extends Localidade {
	private double beneficioPercentagem;
	
	public double getBeneficioPercentagem() {
		return beneficioPercentagem;
	}

	public LocalidadeCentro(String localidade, double beneficio) {
		super(localidade);
		this.beneficioPercentagem = beneficio;
	}
	
	@Override
	public LocalidadeCentro clone() {
		return new LocalidadeCentro(this.getLocalidade(),this.beneficioPercentagem);
	}
}
