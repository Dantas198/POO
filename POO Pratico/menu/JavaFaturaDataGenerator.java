package menu;

import atividadesEconomicas.Saude;
import atividadesEconomicas.Veterinario;
import contribuintes.Contribuinte;
import contribuintes.ContribuinteEmpresarial;
import contribuintes.ContribuinteIndividual;
import contribuintes.Contribuintes;
import exceptions.ContribuinteDoesntExistException;
import exceptions.ContribuinteNaoIndividualException;
import fatura.Fatura;
import fatura.Faturas;
import javafx.util.Pair;
import moradas.Localidade;
import moradas.LocalidadeCentro;
import moradas.LocalidadeLitoral;
import moradas.Localidades;
import moradas.Morada;

public class JavaFaturaDataGenerator {
	private Contribuintes c;
	private Localidades l;
	private Faturas f;
	
	public Contribuintes getC() {
		return c.clone();
	}
	public void setC(Contribuintes c) {
		this.c = c.clone();
	}
	public Localidades getL() {
		return l.clone();
	}
	public void setL(Localidades l) {
		this.l = l.clone();
	}
	public Faturas getF() {
		return f.clone();
	}
	public void setF(Faturas f) {
		this.f = f.clone();
	}
	
	
	private void addFamilia() {
		Localidade ls = this.l.getLocalidade("Lisboa");
		if (ls == null)
			return;
		Morada m = new Morada(4, new Pair<Integer,Integer>(4239, 423), ls);
		ContribuinteIndividual pai = new ContribuinteIndividual("Kratos",272390208,"godofboy@greekGod.com",m,"boy",0.1f);
		c.addContribuinte(pai);
		try {
			c.addNewContribuinteToAgregado(272390208, "Faye", 262268256, "Faye@gmail.com", "artreos");
		} catch (ContribuinteDoesntExistException | ContribuinteNaoIndividualException e) {
			e.printStackTrace();
		}
		try {
			c.addDependenteToAgregado(272390208,2);
		} catch (ContribuinteNaoIndividualException | ContribuinteDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createLocalidades() {
		this.l.addLocalidade(new LocalidadeCentro("Braga", 0.15));
		this.l.addLocalidade(new LocalidadeCentro("Barcelos", 0.2));
		this.l.addLocalidade(new LocalidadeCentro("Braganca", 0.25));
		this.l.addLocalidade(new LocalidadeCentro("Guimaraes", 0.1));
		this.l.addLocalidade(new LocalidadeLitoral("Ofir"));
		this.l.addLocalidade(new LocalidadeLitoral("Porto"));
		this.l.addLocalidade(new LocalidadeLitoral("Lisboa"));		
	}
	
	private void addIndividual() {
		Localidade ls = this.l.getLocalidade("Braga");
		if (ls == null)
			return;
		Morada m = new Morada(1, new Pair<Integer,Integer>(4222, 123),ls);
		ContribuinteIndividual witcher = new ContribuinteIndividual("Geraldo",237313731,"whiteWolf@gmail.com",m,"cirilla",0.2f);
		c.addContribuinte(witcher);
	}
	
	private void addEmpresaInterior() {
		Localidade ls = this.l.getLocalidade("Braganca");
		if (ls == null)
			return;
		Morada m = new Morada(1, new Pair<Integer,Integer>(4212, 123),ls);
		ContribuinteEmpresarial e = new ContribuinteEmpresarial("Hospital",112,"hostital@saude.pt",m,"admin");
		e.addAtividadeEmpresa(new Saude());
		this.c.addContribuinte(e);
	}
	
	private void addEmpresaLitoral() {
		Localidade ls = this.l.getLocalidade("Ofir");
		if (ls == null)
			return;
		Morada m = new Morada(25, new Pair<Integer,Integer>(4222, 150),ls);
		ContribuinteEmpresarial e = new ContribuinteEmpresarial("SAVE the cats",33,"Save the cats@animal.pt",m,"dog");
		e.addAtividadeEmpresa(new Veterinario());
		e.addAtividadeEmpresa(new Saude());
		this.c.addContribuinte(e);
	}
	
	private void generateFaturas() throws ContribuinteDoesntExistException {
		ContribuinteEmpresarial cat = (ContribuinteEmpresarial) this.c.getContribuinte(33);
		Contribuinte cliente = this.c.getContribuinte(272390208);
		Fatura fat = cat.emiteFatura(cliente, "Hates Dogs", 1000000.99f);
		this.f.addFatura(fat);
		ContribuinteEmpresarial hospital = (ContribuinteEmpresarial) this.c.getContribuinte(112);
		Contribuinte client = this.c.getContribuinte(272390208);
		Fatura fa = hospital.emiteFatura(client, "perna partida", 2.0f);
		this.f.addFatura(fa);
	}
	
	public void generateData() {
		this.createLocalidades();
		this.addFamilia();
		this.addIndividual();
		this.addEmpresaInterior();
		this.addEmpresaLitoral();
		try {
			this.generateFaturas();
		} catch (ContribuinteDoesntExistException e) {
			return;
		}
	}
	
	
	public JavaFaturaDataGenerator() {
		c = new Contribuintes();
		l = new Localidades();
		f = new Faturas();
		this.generateData();
	}
}
