package menu;

import contribuintes.Contribuinte;
import contribuintes.ContribuinteIndividual;
import contribuintes.Contribuintes;
import exceptions.ContribuinteDoesntExistException;
import exceptions.ContribuinteNaoIndividualException;
import fatura.Faturas;
import javafx.util.Pair;
import moradas.Localidade;
import moradas.LocalidadeCentro;
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
	
	private LocalidadeCentro newLocCentro(String nome,double d) {
		LocalidadeCentro ls = new LocalidadeCentro(nome,  d);
		this.l.addLocalidade(ls);
		return ls;
	}
	
	public void addFamilia() {
		Morada m = new Morada(4, new Pair<Integer,Integer>(4239, 423), newLocCentro("WildWoods", 0.1));
		ContribuinteIndividual pai = new ContribuinteIndividual("Kratos",272390208,"godofboy@greekGod.com",m,"boy",0.1f);
		c.addContribuinte(pai);
		try {
			c.addNewContribuinteToAgregado(272390208, "Faye", 262268256, "Faye@gmail.com", "artreos");
		} catch (ContribuinteDoesntExistException | ContribuinteNaoIndividualException e) {
			e.printStackTrace();
		}
		try {
			c.addDependenteToAgregado(272390208,1);
		} catch (ContribuinteNaoIndividualException | ContribuinteDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printFamily() {
		try {
			System.out.println(this.c.getContribuinte(272390208).toString());
		} catch (ContribuinteDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(this.c.getContribuinte(262268256).toString());
		} catch (ContribuinteDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addWitcher() {
		Morada m = new Morada(1, new Pair<Integer,Integer>(4222, 123), newLocCentro("Kaer Morhen", 0.25));
		ContribuinteIndividual witcher = new ContribuinteIndividual("Geralt",237313731,"whiteWolf@wolfSchool.com",m,"cirilla",0.2f);
		c.addContribuinte(witcher);
	}
	
	public JavaFaturaDataGenerator() {
		c = new Contribuintes();
		l = new Localidades();
		f = new Faturas();
	}
}
