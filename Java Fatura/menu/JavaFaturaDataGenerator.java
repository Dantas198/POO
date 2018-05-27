package menu;

import atividadesEconomicas.*;
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
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import comparators.CompareFaturasByDate;

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
    
        public LocalDateTime randomData() {
                Random num = new Random();
		LocalDateTime now = LocalDateTime.now();
		int year = 60 * 60 * 24 * 365;
		long randomNum =  (num.nextInt() %(2 * year)) - 2*year;
		return now.plusSeconds(randomNum);
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
        c.addContribuinte( new ContribuinteIndividual("Antonio", 280446365, "antoniogomes@sapo.pt",
                new Morada(25, new Pair<Integer,Integer>(8543, 144), l.getLocalidade("Porto")), "antonio", 0.2f));;
        c.addContribuinte( new ContribuinteIndividual("Manuel", 281738459, "manuelrobalo@hotmail.com",
                new Morada(35, new Pair<Integer,Integer>(8543, 144), l.getLocalidade("Ofir")), "manuel", 0.2f));
        c.addContribuinte( new ContribuinteIndividual("Maria", 207543992, "mariantonia@hotmail.com",
                new Morada(4, new Pair<Integer,Integer>(8543, 144), l.getLocalidade("Porto")), "maria", 0.2f));
        c.addContribuinte( new ContribuinteIndividual("Roberto", 261827529, "robertodias76@gmail.com",
                new Morada(28, new Pair<Integer,Integer>(8543, 144), l.getLocalidade("Braganca")), "roberto", 0.2f));
        c.addContribuinte( new ContribuinteIndividual("Antonia", 211893838, "antoniachaves@hotmail.com",
                new Morada(4, new Pair<Integer,Integer>(8543, 144), l.getLocalidade("Guimaraes")), "antonia", 0.2f));
        c.addContribuinte( new ContribuinteIndividual("Mariana", 269638814, "marianamacedo@gmail.com",
                new Morada(32, new Pair<Integer,Integer>(8543, 144), l.getLocalidade("Porto")), "mariana", 0.2f));
        c.addContribuinte( new ContribuinteIndividual("Joana", 244697272, "joanamaceeira@gmail.com",
                new Morada(5, new Pair<Integer,Integer>(8543, 144), l.getLocalidade("Ofir")), "joana", 0.2f));
        c.addContribuinte( new ContribuinteIndividual("Bernardete", 256640190, "bernardetesousa@hotmail.com",
                new Morada(7, new Pair<Integer,Integer>(8543, 144), l.getLocalidade("Braga")), "bernardete", 0.2f));
        c.addContribuinte( new ContribuinteIndividual("Marco", 272137456, "marcodantas@gmail.com",
                new Morada(21, new Pair<Integer,Integer>(8543, 144), l.getLocalidade("Porto")), "marco", 0.2f));
        c.addContribuinte( new ContribuinteIndividual("Jose", 225288958, "josefernandes@gmail.com",
                new Morada(10, new Pair<Integer,Integer>(8543, 144), l.getLocalidade("Barcelos")), "jose", 0.2f));
        c.addContribuinte( new ContribuinteIndividual("Cesar", 276689402, "cesar.a.borges98@gmail.com",
                new Morada(15, new Pair<Integer,Integer>(9760, 117), l.getLocalidade("Braga")), "cesar", 0.2f));
                
    }
    
    private void addEmpresaInterior() {
        Localidade ls = this.l.getLocalidade("Braganca");
        if (ls == null)
            return;
        Morada m = new Morada(1, new Pair<Integer,Integer>(4212, 123),ls);
        ContribuinteEmpresarial e = new ContribuinteEmpresarial("Hospital",112,"hostital@saude.pt",m,"admin");
        e.addAtividadeEmpresa(new Saude());
        this.c.addContribuinte(e);
        
        e = new ContribuinteEmpresarial("Restaurante Trincas",52,"trincas@restaurante.pt", 
                new Morada(15, new Pair<Integer,Integer>(5790, 101), l.getLocalidade("Braga")),"trincas");
        e.addAtividadeEmpresa(new Restauracao());
        this.c.addContribuinte(e);
        
        ContribuinteEmpresarial papelaria = new ContribuinteEmpresarial("papelaria",65,"papelaria@gmail.pt", 
            new Morada(31, new Pair<Integer, Integer>(7593, 241), this.l.getLocalidade("Braga")),"papelaria");
        papelaria.addAtividadeEmpresa(new Educacao());
        this.c.addContribuinte(papelaria);
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
        
        ContribuinteEmpresarial Opel = new ContribuinteEmpresarial("Open",89,"opel@opel.pt", 
            new Morada(56, new Pair<Integer, Integer>(2300, 253), this.l.getLocalidade("Porto")),"opel");
        Opel.addAtividadeEmpresa(new ReparacaoManutencaoVeiculos());
        Opel.addAtividadeEmpresa(new ReparacaoManutencaoMotociclos());
        this.c.addContribuinte(Opel);
    }
    
    private void generateFaturas() throws ContribuinteDoesntExistException {
        Fatura fat;
        ContribuinteEmpresarial cat = (ContribuinteEmpresarial) this.c.getContribuinte(33);
        ContribuinteEmpresarial hospital = (ContribuinteEmpresarial) this.c.getContribuinte(112);
        ContribuinteEmpresarial restaurante = (ContribuinteEmpresarial) this.c.getContribuinte(52);
        ContribuinteEmpresarial papelaria = (ContribuinteEmpresarial) this.c.getContribuinte(65);
        ContribuinteEmpresarial opel = (ContribuinteEmpresarial) this.c.getContribuinte(89);
        
        Contribuinte Kratos = this.c.getContribuinte(272390208); 
        Contribuinte Manuel = this.c.getContribuinte(281738459); 
        Contribuinte Antonio = this.c.getContribuinte(280446365);
        Contribuinte Maria = this.c.getContribuinte(207543992);
        Contribuinte Roberto = this.c.getContribuinte(261827529);
        Contribuinte Joana = this.c.getContribuinte(244697272);
        Contribuinte Jose = this.c.getContribuinte(225288958);
        Contribuinte Marco = this.c.getContribuinte(272137456);
        Contribuinte Cesar = this.c.getContribuinte(276689402);
        Contribuinte Mariana = this.c.getContribuinte(269638814);
        
        List<Fatura> opd = new ArrayList<>();
        opd.add(restaurante.emiteFatura(Antonio, "sopa" , 3.99f));
        opd.add(restaurante.emiteFatura(Jose, "lagostas" , 73.99f));
        opd.add(restaurante.emiteFatura(Jose, "lagostas" , 73.99f));
        opd.add(restaurante.emiteFatura(Cesar, "camaroes", 55.99f));
        opd.add(restaurante.emiteFatura(Maria, "prato do dia" , 5.99f));
        opd.add(restaurante.emiteFatura(Cesar, "camaroes", 55.99f));
        opd.add(restaurante.emiteFatura(Mariana, "arroz de pato" , 4.99f));
        opd.add(restaurante.emiteFatura(Maria, "prato do dia" , 5.99f));
        opd.add(hospital.emiteFatura(Kratos, "perna partida", 2.0f));
        opd.add(hospital.emiteFatura(Manuel, "gripe", 8.0f));
        opd.add(hospital.emiteFatura(Manuel, "inflamacao", 5.0f));
        opd.add(hospital.emiteFatura(Antonio, "cuidados ligeiros", 3.0f));
        opd.add(hospital.emiteFatura(Kratos, "constipacao", 5.0f));
        opd.add(hospital.emiteFatura(Marco, "operacao", 200.0f));
        opd.add(papelaria.emiteFatura(Maria, "fotocopias", 1.08f));
        opd.add(papelaria.emiteFatura(Marco, "colas", 3.0f));
        opd.add(papelaria.emiteFatura(Jose, "calculadora", 10.0f));
        opd.add(papelaria.emiteFatura(Cesar, "cartolinas", 5.0f));
        opd.add(papelaria.emiteFatura(Mariana, "calculadora cientifica", 199.0f));
        opd.add(opel.emiteFatura(Roberto, "Manutencao carro", 90.0f));
        opd.add(opel.emiteFatura(Maria, "Manutencao mota", 152.0f));
        opd.add(opel.emiteFatura(Cesar, "Manutencao carro", 155.0f));
        opd.add(opel.emiteFatura(Cesar, "Manutencao mota", 160.0f));
        opd.add(opel.emiteFatura(Marco, "Manutencao carro", 200.0f));
        opd.add(opel.emiteFatura(Mariana, "Manutencao carro", 110.0f));
        opd.add(opel.emiteFatura(Mariana, "Manutencao carro", 160.0f));
        opd.add(opel.emiteFatura(Jose, "Manutencao carro", 150.0f));
        opd.add(papelaria.emiteFatura(Manuel, "fotocopias", 0.76f));
        opd.add(cat.emiteFatura(Kratos, "Hates Dogs", 1000000.99f));
        opd.add(cat.emiteFatura(Kratos, "Hates cats", 9999.98f));
        
        for(Fatura fatura : opd)
            fatura.setDataDespesa(randomData());

        Collections.sort(opd, new CompareFaturasByDate());
        
        for(Fatura fatura : opd)
            this.f.addFatura(fatura);
        
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
