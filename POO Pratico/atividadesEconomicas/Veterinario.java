package atividadesEconomicas;

public class Veterinario extends AtividadeEconomica {

	public Veterinario() {
		this.setNomeAtividade("Veterinario");
		this.setCoef((float) 0.1);
	}

	public Veterinario(Veterinario a) {
		super(a);
	}
	
	@Override
	public Veterinario clone() {
		return new Veterinario(this);
	}
}
