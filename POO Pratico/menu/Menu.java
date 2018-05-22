package menu;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.List;
import javafx.util.Pair;
import java.io.Serializable;

import atividadesEconomicas.AtividadeEconomica;
import fatura.Fatura;
import fatura.Faturas;
import contribuintes.Contribuinte;
import contribuintes.ContribuinteEmpresarial;
import contribuintes.ContribuinteIndividual;
import contribuintes.Contribuintes;
import moradas.Morada;
import moradas.Localidade;
import moradas.LocalidadeLitoral;
import moradas.LocalidadeCentro;
import exceptions.FaturaPendenteException;
import exceptions.FaturaNaoExisteException;
import exceptions.ContribuinteDoesntExistException;

/**
 * Write a description of class Menu here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Menu implements Serializable
{   
    private Faturas f;
    private Contribuintes c;
    
    private Contribuinte loggedIn;
    private LocalDateTime start;
    private LocalDateTime end;
    
<<<<<<< HEAD
    
=======
    /**
     * Atualiza as faturas
     */
    private void setFaturas(Faturas f){
        this.f = f;
    }
    
    /**
     * Atualiza os contribuintes
     */
    private void setContribuintes(Contribuintes c){
        this.c = c;
    }
    
    /**
     * Metodo que guarda o estado do menu
     */
>>>>>>> b58349cc0243a450c6cd1907ae4a27ff6266e218
    public void saveMenu(String filepath) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileOutputStream fos = new FileOutputStream(new File(filepath));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
    }
    
<<<<<<< HEAD
    private int ver10ContribuintesMaisDispendiosos(){
        System.out.println("Os contribuintes com mais depesas:");
        List<Pair<Integer, Float>> osDez = f.getTenContribuintesMostDespesa();
        for(Pair<Integer, Float> despesa : osDez){
            System.out.println("Nif: "+ despesa.getKey() + " -> Despesa:" + despesa.getValue());
        }  
        return menuAdmin();
    }
    
    //Ainda por acabar
    private int verEmpresasMaisFaturadas(){
        System.out.println("As empresas que mais faturam:");
        
        
        return menuAdmin();
    }
    
=======
    /**
     * Metodo que constroi o menu do administrador
     */
>>>>>>> b58349cc0243a450c6cd1907ae4a27ff6266e218
    private int menuAdmin(){
        System.out.println("Welcome Administrator");
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        
        menuString.add("Ver os 10 contribuintes que gastam mais no sistema");
        menuString.add("Ver as empresas que faturam mais e as suas deduçoes fiscais");
        menuString.add("Log out");
        
        toRun.add(this::ver10ContribuintesMaisDispendiosos);
        //toRun.add(this::verEmpresasMaisFaturadas);
        toRun.add(this::menuAdmin);
        
        return genericMenu(menuString, toRun);
    }
    
    /**
     * Adiciona opcoes de ver despesas ao menu
     */
    private int verDespesas(){
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        List<Fatura> faturas = f.getFaturasFromContribuinte(this.loggedIn.getNif());
        
        System.out.println(faturas.toString());
        menuString.add("Retroceder");
        toRun.add(this::menuContrIndiv);   
        return genericMenu(menuString, toRun);
    }
    
    /**
     * Constroi o menu do contribuinte individual
     */
    private int menuContrIndiv(){
        System.out.println("Em que lhe posso ajudar "+ this.loggedIn.getNome());
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        
        menuString.add("Ver despesas");
        menuString.add("Ver montante de deduçao fiscal acumulado");
        menuString.add("Associar uma atividade economica a uma despesa");
        menuString.add("Corrigir classificaçao de uma atividade economica");
        menuString.add("Ver lista de facturas de uma empresa");
        menuString.add("Log out");
        
        toRun.add(this::verDespesas);
        //toRun.add(this::verMonstanteDeDeducaoFiscal);
        //toRun.add(this::associaAtividadeADespesa);
        //toRun.add(this::corrigitClassificacaoDeAtividade);
        //toRun.add(this::verFaturasDeUmaEmpresa);
        toRun.add(this::welcomeMenu);
        
        return genericMenu(menuString, toRun);
    }
    
<<<<<<< HEAD
    // Nao funciona ate se definir a estrutura da fatura
=======
    /**
     * Permite a uma empresa, atraves do menu, emitir uma fatura por parte de uma empresa(menu c. empresarial)
     */
>>>>>>> b58349cc0243a450c6cd1907ae4a27ff6266e218
    private int criarFatura(){
        Fatura fat;
        Contribuinte cliente;
        AtividadeEconomica ae = new AtividadeEconomica();
        
        System.out.println("Criando uma nova fatura");
        try{
            cliente = c.getContribuinte((int) getInfo("Introduza o Nif do cliente", Integer.class));
        } catch (ContribuinteDoesntExistException e){
            System.out.println("Contribuinte doesn't exist" + e.getMessage());
            return welcomeMenu();
        }
        String descricao = (String) getInfo("Introduza a descricao da fatura", String.class);
        float despesa = (int) getInfo("Introduza a despesa", Integer.class);
        //fat.setNumFatura(f.getNumFaturas());
        //fat.setDataDespesa(LocalDateTime.now());
        //fat.setDesignacaoEmitente((String) getInfo("Introduza a designacao", String.class));

        ae.setNomeAtividade((String) getInfo("Introduza a atividade", String.class));
        ae.setCoef((float) getInfo("Introduza o coeficiente da atividade", Float.class));
        //fat.setNaturezaDespesa(ae);
        
        if(this.loggedIn instanceof ContribuinteEmpresarial)
            fat = new Fatura((ContribuinteEmpresarial) this.loggedIn, 
                LocalDateTime.now(), cliente, descricao, ae, despesa);
        else{
            System.out.println("You don't have permission to create a new Fatura");
            return welcomeMenu();
        }
        
        try{
            f.addFatura(fat);
            System.out.println("Fatura inserida - " + f.getNumFaturas());
        } catch (Exception e){
            System.out.println("Couldn't insert Fatura" + e.getMessage());
        }
        
        return menuContrEmpr();
    }    
    
    /**
     * Adiciona ao menu a possibilidade de ver as faturas de um contribuinte
     */
    private int verFaturas(){
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        List<Fatura> faturas = f.getFaturasFromEmitente(this.loggedIn.getNif());
        
        System.out.println(faturas.toString());
        menuString.add("Retroceder");
        toRun.add(this::menuContrEmpr);
        
        return genericMenu(menuString, toRun);
    }
    
    /**
     * Constroi o menu do contribuinte empresarial
     */
    private int menuContrEmpr(){
        System.out.println("Em que lhe posso ajudar "+ this.loggedIn.getNome());
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();

        
        menuString.add("Criar fatura");
        menuString.add("Ver faturas");
        menuString.add("Ver faturas por contribuinte num intervalo de tempo");
        menuString.add("Ver faturas por contribuinte");
        menuString.add("Ver total faturado num intervalo de tempo");
        menuString.add("Log out");
        
        toRun.add(this::criarFatura);
        toRun.add(this::verFaturas);
        //toRun.add(Menu::verFaturasPeloTempo);
        //toRun.add(Menu::verFaturasPorContribuinte);
        //toRun.add(Menu::verTotalFaturadoPeloTempo);
        toRun.add(this::welcomeMenu);
        
        return genericMenu(menuString, toRun);
        
    }
    
    
    /**
     * Dependendo do tipo de contribuinte invoca o menu correspondente a esse tipo
     */
    private int menuContr(){
        if(this.loggedIn instanceof ContribuinteIndividual)
            return menuContrIndiv();
        else return menuContrEmpr();
    }
    
    /**
     * Constroi o menu de registo do contribuinte individual
     */
    private int registerMenuContrInd(){
        ContribuinteIndividual contr = new ContribuinteIndividual();
        
        
        contr.setNif((int) getInfo("Introduza o Nif", Integer.class));
        System.out.println(contr.getNif());
        contr.setNome((String) getInfo("Introduza o Nome", String.class));
        System.out.println(contr.getNome());
        contr.setEmail((String) getInfo("Introduza o Email", String.class));
        System.out.println(contr.getEmail());
        contr.setMorada(moradaMenu());
        System.out.println(contr.getMorada());
        contr.setNumDependentesAgregado((int) getInfo("Introduza o numero do agregado familiar", Integer.class)); 
        contr.setCoefFiscal((int) getInfo("Introduza o coeficiente fiscal", Integer.class));
        contr.setPassword((String) getInfo("Introduza a sua palavra-passe", String.class));
        
        try{
            if(c.existeContribuinte(contr))
                System.out.println("User already exists");
            else c.addContribuinte(contr);
        } catch (NullPointerException e){
            System.out.println(contr);
            System.out.println(c);
            System.out.println("Couldn't register Contribuinte");
        }   
        return welcomeMenu();
    }
   
    /**
     * Constroi o menu de registo de um contribuinte empresarial
     */
    private int registerMenuContrEmpr(){
        ContribuinteEmpresarial contr = new ContribuinteEmpresarial();
        contr.setNif((int) getInfo("Introduza o Nif", Integer.class));
        contr.setNome((String) getInfo("Introduza o Nome", String.class));
        contr.setEmail((String) getInfo("Introduza o Email", String.class));
        contr.setPassword((String) getInfo("Introduza a sua palavra-passe", String.class));
        contr.setMorada(moradaMenu());
        try{
            if(c.existeContribuinte(contr))
                System.out.println("User already exists");
            else c.addContribuinte(contr);
        } catch (NullPointerException e){
            System.out.println("Couldn't register Contribuinte");
        }
        return welcomeMenu();
    }
    
    /**
     * Constroi o menu de registo que permite escolher o tipo de contribuinte que está em causa
     */
    private int registerMenu(){
        System.out.println("Registrando um novo Contribuinte");
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        
        menuString.add("Registrar contribuinte Individual");
        menuString.add("Registrar contribuinte Empresarial");
        menuString.add("Retroceder");
        toRun.add(this::registerMenuContrInd);
        toRun.add(this::registerMenuContrEmpr);
        toRun.add(this::welcomeMenu);
        
        return genericMenu(menuString, toRun);
    }
    
    /**
     * Constroi o menu de login dos contribuintes
     */
    private int loginMenu(){
        int nif;
        String pass;
        
        System.out.println("Introduza o seu NIF:");
        Scanner s1 = new Scanner(System.in);
        try{
            nif = s1.nextInt();
        } catch (InputMismatchException e){
            nif = -1;
        }
        s1.close();
        
        System.out.println("Introduza a sua palavra-pass:");
        Scanner s2 = new Scanner(System.in);
        try{
            pass = s2.nextLine();
        } catch (InputMismatchException e){
            pass = "";
        }
        s2.close();
        
        if(c != null)
            this.loggedIn = c.login(nif, pass);
            
        if(this.loggedIn == null){
            System.out.println("Utilizador nao encontrado/password errada");
            return welcomeMenu();
        }
        else{
            if(loggedIn.getNif() == -777 && loggedIn.isPassword(pass))
                return menuAdmin();
           
            
            System.out.println("Authentication sucessful");
            return menuContr();
        }
    }
    
    /**
     * Chama o welcomeMenu() ?
     */
    private int welcomeMenu(Object o){
        return welcomeMenu();
    }
    
    /**
     * Constroi o menu de boas-vindas
     */
    private int welcomeMenu(){
        System.out.println("Welcome User");
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        
        menuString.add("Registrar novo Contribuinte");
        menuString.add("Log In");
        toRun.add(this::registerMenu);
        toRun.add(this::loginMenu);
        
        return genericMenu(menuString, toRun);
    }
    
    /**
     * Constroi o menu de morada
     */
    private Morada moradaMenu(){
        int nPorta = ((int) getInfo("Introduza o numero de porta", Integer.class));
        String localidade = (String) getInfo("Introduza a localidade", String.class);
        LocalidadeCentro l = new LocalidadeCentro (localidade, (double) getInfo("Introduza o desconto fiscal", Double.class));
        Pair<Integer, Integer> codPostal = ((Pair<Integer,Integer>) getInfo("Introduza o seu codigo postal  \"? - ?\"", Pair.class));
        return new Morada(nPorta, codPostal, l);
    }
    
    /**
     * 
     */
    private int genericMenu(ArrayList<String> menuString, ArrayList<Callable<Integer>> toRun){
        int op;
        int i = 1;
        for(String str: menuString){
            System.out.println( i + "-" + str);
            i++;
        }
        System.out.println("0-Sair");
        //System.out.println("c: "+ c);
        
        Scanner scan = new Scanner(System.in);
        do{
            try{
                op = scan.nextInt();
            } catch(InputMismatchException e){
                System.out.println("Input Errado");
                scan.close();
                op = -1;
            }
            if(op > 0 && op <= toRun.size()){
                try{
                    op = toRun.get(op-1).call();
                } catch (Exception e){
                    scan.close();
                    System.out.println("Tente mais tarde: " + e.getMessage());
                    e.printStackTrace(System.out);
                    return welcomeMenu();
                }
            }
            if(op > 0) System.out.println("Funcionalidade nao encontrada");
        }while(op != 0);
        scan.close();
        return op;
    }
    
        
    private Object getInfo(String message, Class<?> cl){
        do{
            Scanner s = new Scanner(System.in);
            System.out.println(message);
            if(cl == String.class){
                try{
                    String res = s.nextLine();
                    s.close();
                    return res;
                } catch (InputMismatchException e){
                    System.out.println("Insert a text please");
                }
            }
            if(cl == Integer.class){
                try{
                    int res = s.nextInt();
                    s.close();
                    return res;
                } catch (InputMismatchException e){
                    System.out.println("Insert a number only please");
                }
            }
            if(cl == Float.class){
                try{
                    float res = s.nextFloat();
                    s.close();
                    return res;
                } catch (InputMismatchException e){
                    System.out.println("Insert a decimal number only please");
                }
            } 
            if(cl == Pair.class){
                try{
                    int cod1, cod2;
                    String str;
                    cod1 = s.nextInt();
                    System.out.println(cod1);
                    str = s.next();
                    System.out.println(str);
                    cod2 = s.nextInt();
                    System.out.println(cod2);
                    return new Pair<Integer,Integer>(cod1, cod2);    
                } catch (InputMismatchException e){
                    System.out.println("Follow the format \"Number - Number\"");
                }
            } 
            if(cl == Double.class){
                try{
                    double res = s.nextDouble();
                    s.close();
                    return res;
                } catch(InputMismatchException e){
                    System.out.println("Insert a decimal number onply please");
                }
            }
           s.close();
        }while(true);
    }
    
    public void run(){
        welcomeMenu();
    }
    
    public void initRun(){
        this.c = new Contribuintes();
        this.f = new Faturas();
        this.loggedIn = null;
        this.start = null;
        this.end = null;
        this.run();
    }
}
