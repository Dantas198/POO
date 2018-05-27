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
import java.time.LocalDate;
import java.util.function.Function;
import java.util.List;
import javafx.util.Pair;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import java.time.format.DateTimeParseException;
import java.time.LocalTime;

import atividadesEconomicas.*;
import fatura.Fatura;
import fatura.Faturas;
import contribuintes.Contribuinte;
import contribuintes.ContribuinteEmpresarial;
import contribuintes.ContribuinteIndividual;
import contribuintes.Contribuintes;
import moradas.Morada;
import moradas.Localidade;
import moradas.LocalidadeLitoral;
import moradas.Localidades;
import moradas.LocalidadeCentro;
import exceptions.*;
import comparators.CompareFaturasByDate;
import comparators.CompareFaturasByValor;

/**
 * Write a description of class Menu here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Menu implements Serializable
{   
    private static List<AtividadeEconomica> atividadesEconomicasPossiveis;
    
    private Faturas f;
    private Contribuintes c;
    private Contribuinte loggedIn;
    private Localidades locs;
    
    /**
     * Permite salvar todos os dados num ficheiro
     */
    public void saveMenu(String filepath) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileOutputStream fos = new FileOutputStream(new File(filepath));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
    }
    
    /**
     * Permite ver os 10 contribuintes mais dispendiosos do sistema
     */
    private int ver10ContribuintesMaisDispendiosos(){
        System.out.println("Os contribuintes com mais depesas:");
        List<Pair<Integer, Float>> osDez = f.getMostSpenders(10);

        for(Pair<Integer, Float> despesa : osDez){
            List<Fatura> lFaturas = f.getFaturasFromContribuinte(despesa.getKey());
            float deducao = f.getDeducao(lFaturas);
            System.out.println("Nif: "+ despesa.getKey() + " -> Despesa:" + despesa.getValue() + " | Deduçao Fiscal:" + deducao);
        }  
        return menuAdmin();
    }
   
    /**
     * Permite ver as empresas que mais faturam no sistema
     */
    private int verEmpresasMaisFaturadas(){
        System.out.println("As empresas que mais faturam:");
        int x = (int) getInfo("Introduza o numero de empresas que quer ver", Integer.class);
        List<ContribuinteEmpresarial> empresas = c.getXMostFaturado(x);
       
        for(ContribuinteEmpresarial p : empresas){
            List<Fatura> faturas = f.getFaturasFromEmitente(p.getNif());
            float deducao = f.getDeducao(faturas);
            System.out.println("Nif: " + p.getNif() + " - " + p.getLucro() + " faturado | Deducao fiscal - " + deducao);
        }
        return menuAdmin();
    }
    
    
    /**
     * Permite ver as correçoes que foram feitas em faturas por contribuintes individuais
     */
    private int verCorrecoesDeFaturas(){
        ArrayList<Pair<Integer, Pair<AtividadeEconomica, AtividadeEconomica>>> fixes = f.getCorrecoes();
        for(Pair<Integer, Pair<AtividadeEconomica, AtividadeEconomica>> fix : fixes){
            System.out.println("/////////////////////////////////////////////////////////");
            System.out.println("Numero de fatura: " + fix.getKey());
            System.out.println("Atividade Economica alterada: " + fix.getValue().getKey());
            System.out.println("Atividade Economica colocada: " +  fix.getValue().getValue());
            System.out.println("/////////////////////////////////////////////////////////");
        }
        return menuAdmin();
    }
    
    /**
     * Adiciona uma localidade a locs
     * */
    public int addLocalidadeToSistema() {
        String l = (String) getInfo("Intruduza o nome da localidade", String.class);
        if (locs.isLocalidade(l))
            System.out.println("Localidade ja existe");
        else {
            Localidade lo = null;
            String o;
            do {
                o = (String) getInfo("Esta localidade e do (centro ou litoral)?", String.class); 
            } while (!(o.equals("centro") || o.equals("litoral")));
            if (o.equals("centro")) {
                double desc = (Double) getInfo("Insira benificio para empresas do centro (Insira 0 se nao existir)", Double.class);
                lo = new LocalidadeCentro(l, desc);
            }
            else if (o.equals("litoral")) {
                lo = new LocalidadeLitoral(l);
            }
            this.locs.addLocalidade(lo);
        }
        return menuAdmin();
    }
    
    /**
     * Metodo que constroi o menu do administrador
     */
    private int menuAdmin(){
        System.out.println("Welcome Administrator");
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        
        menuString.add("Ver os 10 contribuintes que gastam mais no sistema");
        menuString.add("Ver as empresas que faturam mais e as sua deduçao fiscal");
        menuString.add("Ver correcoes de faturas");
        menuString.add("Adicionar localidades");
        menuString.add("Log out");
        
        toRun.add(this::ver10ContribuintesMaisDispendiosos);
        toRun.add(this::verEmpresasMaisFaturadas);
        toRun.add(this::verCorrecoesDeFaturas);
        toRun.add(this::addLocalidadeToSistema);
        toRun.add(this::logOut);
        
        return genericMenu(menuString, toRun);
    }
    
    /**
     * Permite ver o montante de deducao fiscal do agregado familiar
     */
    private int verMonstanteDeDeducaoFiscal(){
        float deducaoFiscalDoAgregado = f.getNFAcumuladoAgregado((ContribuinteIndividual) this.loggedIn);
        System.out.println("Deducao fiscal do agregado familiar - " + deducaoFiscalDoAgregado + "\n"); 
        return menuContrIndiv();
    }
    
    /**
     * Oermite associar uma atividade economica a uma despesa
     */
    private int associaAtividadeADespesa(){
        int nFat = (int) getInfo("Intruduza o numero da fatura que deseja associar", Integer.class);
        Fatura fatura;
        try{
            fatura = f.getFaturaPendente(nFat);
        } catch(FaturaNaoExisteException e){
            System.out.println("A fatura nao existe");
            return menuContrIndiv();
        }
        List<AtividadeEconomica> lae = fatura.getNaturezasPossiveis();
        AtividadeEconomica ae = menuAtividadesEconomicas(lae);
        
        try{
            f.associaAtividadeEconcomica(this.loggedIn, nFat, ae);
        } catch (FaturaNaoExisteException e){
            System.out.println("A fatura nao existe");
        } catch (FaturaNaoPendenteException e){
            System.out.println("Esta fatura nao se encontra pendente");
        }
        return menuContrIndiv();
    }
    
    /**
     * Parte do menu que permite ao Contribuinte Individual corrigir a atividadeEconomica de uma despesa/fatura
     */
    
    private int corrigirClassificacaoDeAtividade(){
         Fatura fatura;
         int nFat = (int) getInfo("Intruduza o numero da fatura que deseja corrigir", Integer.class);
         try{
             fatura = f.getFatura(nFat);
            } catch(FaturaNaoExisteException e){
                System.out.println("Fatura nao existe - " + nFat);
                return menuContrIndiv();
            }
         List<AtividadeEconomica> lae = fatura.getNaturezasPossiveis();
         AtividadeEconomica ae = menuAtividadesEconomicas(lae);
         try{
            f.corrigeAtividadeFatura(this.loggedIn, nFat, ae);
        } catch (FaturaNaoExisteException e){
            System.out.println("A Fatura nao existe - " + nFat);
        } catch (FaturaPendenteException e){
            System.out.println("Esta fatura encontra-se pendente - " + nFat);
        }
         return menuContrIndiv();
    }
    
    /**
     * Permite ver as Fturas de um contribuinte empresarial ordenadas por contribuinte e de seguida por Valor
     */
    private int verFaturasDeUmaEmpresaPorValor(){
        int nifEmpresa = (int) getInfo("Introduza o Nif da empresa da qual quer ver as faturas", Integer.class);
        List<Fatura> fatDeEmpresa = f.getFaturasOfClienteFromEmitente(this.loggedIn.getNif(), nifEmpresa, new CompareFaturasByValor());
        if(fatDeEmpresa.size() == 0) System.out.println("Nao possui faturas com esta empresa");
        System.out.println(f.listToString(fatDeEmpresa));
        
        return menuContrIndiv();
    }
    
    /**
     *  Permite ver as Fturas de um contribuinte empresarial ordenadas por contribuinte e de seguida por Data
     */
    private int verFaturasDeUmaEmpresaPorData(){
        int nifEmpresa = (int) getInfo("Introduza o Nif da empresa da qual quer ver as faturas", Integer.class);
        List<Fatura> fatDeEmpresa = f.getFaturasOfClienteFromEmitente(this.loggedIn.getNif(), nifEmpresa, new CompareFaturasByDate());
        if(fatDeEmpresa.size() == 0) System.out.println("Nao possui faturas com esta empresa");
        System.out.println(f.listToString(fatDeEmpresa));
        
        return menuContrIndiv();
    }
    
    /**
     * Menu com as opçoes para ver as faturas de um contribuinte empresarial
     */
    private int verFaturasDeUmaEmpresa(){
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        
        menuString.add("Ver faturas de uma empresa ordenada por data");
        menuString.add("Ver faturas de uma empresa ordenada por valor");
        menuString.add("Retroceder");
        
        toRun.add(this::verFaturasDeUmaEmpresaPorData);
        toRun.add(this::verFaturasDeUmaEmpresaPorData);
        toRun.add(this::menuContrIndiv);
        
        return genericMenu(menuString, toRun);
    }
    
    /**
     * Permite ver as despesas pendentes de um contribuinte individual
     */
    private int verDespesasPendentes(){
        List<Fatura> faturas = f.getFaturasPendentesFromContribuinte(this.loggedIn.getNif());
        System.out.println(Faturas.listToString(faturas));
        return menuContrIndiv();
    }
    
     /**
     * Permite ver todas as despesas de um contribuinte individual
     */
    private int verDespesas(){   
        List<Fatura> faturas = f.getFaturasFromContribuinte(this.loggedIn.getNif());
        System.out.println(Faturas.listToString(faturas));
            
        return menuContrIndiv();
    }
    
    private int alterarNumDependentesNoAgregado(){
        int nDependentes = (int) getInfo("Introduza o novo numero de dependentes no agregado", Integer.class);
        try{
            c.addDependenteToAgregado(this.loggedIn.getNif(), nDependentes);
        } catch(ContribuinteDoesntExistException e){
              System.out.println("Contribuinte nao existe - " + this.loggedIn.getNif());
        } catch(ContribuinteNaoIndividualException e){
                System.out.println("Nao se pode adicionar uma empresa a um agregado familiar - " + this.loggedIn.getNif());
        }
        return menuContrIndiv();
    }
    
    /**
     * Constroi o menu do contribuinte individual
     */
    private int menuContrIndiv(){
        System.out.println("Em que lhe posso ajudar "+ this.loggedIn.getNome());
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        
        menuString.add("Ver despesas");
        menuString.add("Ver despesas pendentes");
        menuString.add("Ver montante de deduçao fiscal acumulado do agregado familiar");
        menuString.add("Associar uma atividade economica a uma despesa");
        menuString.add("Corrigir classificaçao de uma atividade economica");
        menuString.add("Ver lista de faturas de uma empresa");
        menuString.add("Alterar numero de dependentes no agregado");
        menuString.add("Log out");
        
        toRun.add(this::verDespesas);
        toRun.add(this::verDespesasPendentes);
        toRun.add(this::verMonstanteDeDeducaoFiscal);
        toRun.add(this::associaAtividadeADespesa);
        toRun.add(this::corrigirClassificacaoDeAtividade);
        toRun.add(this::verFaturasDeUmaEmpresa);
        toRun.add(this::alterarNumDependentesNoAgregado);
        toRun.add(this::logOut);
        
        return genericMenu(menuString, toRun);
    }
    

    /**
     * Permite a uma empresa, atraves do menu, emitir uma fatura por parte de uma empresa(menu c. empresarial)
     */
    private int criarFatura(){
        Fatura fat;
        Contribuinte cliente;
        AtividadeEconomica ae;
        
        System.out.println("Criando uma nova fatura");
        try{
            cliente = c.getContribuinte((int) getInfo("Introduza o Nif do cliente", Integer.class));
        } catch (ContribuinteDoesntExistException e){
            System.out.println("Contribuinte doesn't exist" + e.getMessage());
            return welcomeMenu();
        }
        String descricao = (String) getInfo("Introduza a descricao da fatura", String.class);
        float despesa = (int) getInfo("Introduza a despesa", Integer.class);
  
        if(this.loggedIn instanceof ContribuinteEmpresarial){
            fat = ((ContribuinteEmpresarial) this.loggedIn).emiteFatura(cliente, descricao, despesa);
        }
        else{
            System.out.println("You don't have permission to create a new Fatura");
            return welcomeMenu();
        }
        
        try{
            f.addFatura(fat);
            System.out.println("Fatura inserida - " + (f.getNumFaturas() - 1));
        } catch (Exception e){
            System.out.println("Couldn't insert Fatura" + e.getMessage());
            e.printStackTrace(System.out);
        }
        
        return menuContrEmpr();
    }    
    
    /**
     * Permite ao contribuinte empresarial ver todas as faturas
     */
    private int verTodasFaturas(){
        List<Fatura> faturas = f.getFaturasFromEmitente(this.loggedIn.getNif());
        System.out.println(f.listToString(faturas));
        return menuVerFaturas();
    }
    
    /**
     * Adiciona ao menu a possibilidade de ver as faturas de um contribuinte
     */
    private int menuVerFaturas(){
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        
        menuString.add("Ver todas as faturas");
        menuString.add("Ver faturas por contribuinte num intervalo de tempo");
        menuString.add("Ver faturas por contribuinte por ordem decrescente");
        menuString.add("Retroceder");
       
        toRun.add(this::verTodasFaturas);
        toRun.add(this::verFaturasPeloTempo);
        toRun.add(this::VerFaturasPorContribuinte);
        toRun.add(this::menuContrEmpr);
        
        return genericMenu(menuString, toRun);
    }
    
    /**
     * Permite ao contribuinte empresarial ver as faturas ordenadas pelo tempo
     */
    private int verFaturasPeloTempo(){
        LocalDateTime start = (LocalDateTime) getInfo("Introduza a data inicial \"YYYY-MM-dd\"", LocalDateTime.class);
        LocalDateTime end = (LocalDateTime) getInfo("Introduza a data final \"YYYY-MM-dd\"", LocalDateTime.class);
        
        List<Fatura> faturasPeloTempo = f.getFaturasFromEmitenteBetweenDate(this.loggedIn.getNif(), start, end);
        System.out.println(Faturas.listToString(faturasPeloTempo));

        return menuVerFaturas();
    }
    
    /**
     * Permite ao contribuinte empresarial ver as faturas ordenadas por contribuinte
     */
    private int VerFaturasPorContribuinte(){
        List<Fatura> faturas = f.getFaturasByValorDecrescente((ContribuinteEmpresarial) this.loggedIn);
        System.out.println(Faturas.listToString(faturas));
        
        return menuVerFaturas();
    }
    
    /**
     * Permite ao contribuinte empresarial ver o total Fatura num intervalo de tempo
     */
    private int verTotalFaturadoPeloTempo(){
        LocalDateTime start = (LocalDateTime) getInfo("Introduza a data inicial \"YYYY-MM-dd\"", LocalDateTime.class);
        LocalDateTime end = (LocalDateTime) getInfo("Introduza a data final \"YYYY-MM-dd\"", LocalDateTime.class);
        
        float totalFaturado = f.totalFaturado((ContribuinteEmpresarial) this.loggedIn, start, end);
        System.out.println("Total faturado de " + start.toString() + " a " + end.toString() + " - " + totalFaturado + "\n");
        
        return menuVerFaturas();
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
        menuString.add("Ver total faturado num intervalo de tempo");
        menuString.add("Log out");
        
        toRun.add(this::criarFatura);
        toRun.add(this::menuVerFaturas);
        toRun.add(this::verTotalFaturadoPeloTempo);
        toRun.add(this::logOut);
        
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
        contr.setNome((String) getInfo("Introduza o Nome", String.class));
        contr.setEmail((String) getInfo("Introduza o Email", String.class));
        
        String password = (String) getInfo("Introduza a sua palavra-passe", String.class);
        
        boolean temAgregado = (boolean) getInfo("Ja faz parte de algum agregado familiar", Boolean.class);
        while(temAgregado){
            int nifDeAgregado = (int) getInfo("Introduza um nif que esta incluido no seu agregado familiar", Integer.class);
            try{
                c.addNewContribuinteToAgregado(nifDeAgregado, contr.getNome(), contr.getNif(), contr.getEmail(), password);
                return welcomeMenu();
            } catch(ContribuinteDoesntExistException e){
                System.out.println("Contribuinte nao existe - " + nifDeAgregado);
            } catch(ContribuinteNaoIndividualException e){
                System.out.println("Nao se pode adicionar uma empresa a um agregado familiar - " + nifDeAgregado);
            }
            temAgregado = !((boolean) getInfo("Quer continuar sem introduzir o agregado?", Boolean.class));
        }
        contr.setNumDependentesAgregado(0); 
        contr.addAgregado(contr.getNif());    
        contr.setPassword(password);
        
        try {
            contr.setMorada(moradaMenu());
        } catch (UserdidntSendInput e1) {
            return welcomeMenu();
        }
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
     * Constroi o menu de registo de um contribuinte empresarial
     */
    private int registerMenuContrEmpr(){
        AtividadeEconomica ae;
        ContribuinteEmpresarial contr = new ContribuinteEmpresarial();
        contr.setNif((int) getInfo("Introduza o Nif", Integer.class));
        contr.setNome((String) getInfo("Introduza o Nome", String.class));
        contr.setEmail((String) getInfo("Introduza o Email", String.class));
        try {
            contr.setMorada(moradaMenu());
        } catch (UserdidntSendInput e1) {
            return welcomeMenu();
        }
       
        ae = menuAtividadesEconomicas(this.atividadesEconomicasPossiveis);
        contr.addAtividadeEmpresa(ae);
        while((boolean) getInfo("Quer introduzir mais atividades economicas?", Boolean.class)){
            ae = menuAtividadesEconomicas(this.atividadesEconomicasPossiveis);
            contr.addAtividadeEmpresa(ae);
        }
        
        contr.setPassword((String) getInfo("Introduza a sua palavra-passe", String.class));
        try{
            if(c.existeContribuinte(contr))
                System.out.println("User already exists");
            else c.addContribuinte(contr);
        } catch (NullPointerException e){
            System.out.println("Couldn't register Contribuinte");
            e.printStackTrace(System.out);
        }
        return welcomeMenu();
    }
    
    /**
     * Constroi o menu de registo que permite escolher o tipo de contribuinte que está em causa
     */
    private int registerMenu(){
        System.out.println("Registando um novo Contribuinte");
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        
        menuString.add("Registar contribuinte Individual");
        menuString.add("Registar contribuinte Empresarial");
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
            if(nif == -777 && pass.equals("Administrador"))
                return menuAdmin();
            
                System.out.println("Utilizador nao encontrado/password errada");
            return welcomeMenu();
        }             
            System.out.println("Authentication sucessful");
            return menuContr();
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
        
        menuString.add("Registar novo Contribuinte");
        menuString.add("Log In");
        toRun.add(this::registerMenu);
        toRun.add(this::loginMenu);
        
        return genericMenu(menuString, toRun);
    }
    
    /**
     * Obtem uma localidade do utilizador
     * @throws UserdidntSendInput 
     * */
    private Localidade getLocalidadeMenu() throws UserdidntSendInput{
        Localidade l;
        boolean cycle = true;
        do {
            String localidade = (String) getInfo("Introduza a localidade", String.class);
            l = this.locs.getLocalidade(localidade);
            if(l!=null)
                cycle = false;
            else {
                System.out.println("Localidade nao existe - Contacte administrador");
                System.out.println("Deseja inserir outra localidade");
                String o;
                do {
                    o = (String) getInfo("Responda com yes ou no", String.class);   
                } while (!(o.equals("yes") || o.equals("no")));
                if(o.equals("no"))
                    throw new UserdidntSendInput("Localidade");
            }
        } while (cycle);
        return l;
    }
    
    /**
     * Constroi o menu de morada
     * @throws UserdidntSendInput 
     */
    private Morada moradaMenu() throws UserdidntSendInput{
        int nPorta = ((int) getInfo("Introduza o numero de porta", Integer.class));
        Localidade l = getLocalidadeMenu();
        Pair<Integer, Integer> codPostal = ((Pair<Integer,Integer>) getInfo("Introduza o seu codigo postal  \"? - ?\"", Pair.class));
        return new Morada(nPorta, codPostal, l);
    }
    
    /**
     * Menu generico usado regularmente (feito para simplificar codigo)
     */
    private int genericMenu(ArrayList<String> menuString, ArrayList<Callable<Integer>> toRun){
        int op;
        int i = 1;
        for(String str: menuString){
            System.out.println( i + "-" + str);
            i++;
        }
        System.out.println("0-Sair");
        
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
        this.loggedIn = null;
        scan.close();
        return op;
    }
    
    /**
     * Pede ao utilizador certo dado e converte-o adequadamente
     */
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
                    str = s.next();
                    cod2 = s.nextInt();
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
            if(cl == Boolean.class){
                try{
                    char c = s.next().charAt(0);
                    if(Character.toLowerCase(c) == 'y')
                        return true;
                    if(Character.toLowerCase(c) == 'n')
                        return false;
                    else System.out.println("Write yes or no");
                } catch(InputMismatchException e){
                    System.out.println("This is a yes or no question \"y/n\"");
                }
            }
            if(cl == LocalDateTime.class){
                try{
                    String dataString = s.next();
                    LocalDate data = LocalDate.parse(dataString);
                    LocalTime time = LocalTime.now();
                    time.parse("00:00");
                    LocalDateTime resData = LocalDateTime.of(data, time);
                    return resData;
                } catch (DateTimeParseException e){
                    System.out.println("Introduza a data no formato \"YYYY-MM-dd\"");
                }
            }
           s.close();
        } while(true);
    }
    
    /**
     * Mini-Menu de escolha para as atividades economicas
     */
    private AtividadeEconomica menuAtividadesEconomicas(List<AtividadeEconomica> aes){
        int i = 1, op = aes.size() + 1;
        System.out.println("Escolha a atividade economica desejada: ");
        for(AtividadeEconomica ae : aes){
            System.out.println( i + "-" + ae.getNomeAtividade());
            i++;
        }
        Scanner s = new Scanner(System.in);
        while(op <= 0 || op > aes.size()){
            try{
                op = s.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Escoolha um numero entre 1 e " + aes.size());
            }
        }
        s.close();
        return aes.get(op-1);
    }
    
    /**
     * Faz log out do contribuinte
     */
    private int logOut(){
        this.loggedIn = null;
        return welcomeMenu();
    }
    
    /**
     * Inicializa as atividades economicas possiveis
     */
    private void initAtividadesEconomicasPossiveis(){
        Menu.atividadesEconomicasPossiveis = new ArrayList<>();
        Menu.atividadesEconomicasPossiveis.add(new Saude());
        Menu.atividadesEconomicasPossiveis.add(new Educacao());
        Menu.atividadesEconomicasPossiveis.add(new DespesasGeraisFamiliares());
        Menu.atividadesEconomicasPossiveis.add(new Restauracao());
        Menu.atividadesEconomicasPossiveis.add(new PassesTransportes());
        Menu.atividadesEconomicasPossiveis.add(new Veterinario());
        Menu.atividadesEconomicasPossiveis.add(new Imoveis());
        Menu.atividadesEconomicasPossiveis.add(new Lares());
        Menu.atividadesEconomicasPossiveis.add(new CabeleiroBeleza());
        Menu.atividadesEconomicasPossiveis.add(new ReparacaoManutencaoMotociclos());
        Menu.atividadesEconomicasPossiveis.add(new ReparacaoManutencaoVeiculos());
    }
    
    /**
     * Começa o menu com os dados de um ficheiro
     */
    public void run(){
        initAtividadesEconomicasPossiveis();
        welcomeMenu();
    }
    
    /**
     * Começa o menu com dados pr feitos
     */
    public void initRun(){
        this.c = new Contribuintes();
        this.f = new Faturas();
        this.locs = new Localidades();
        JavaFaturaDataGenerator j = new JavaFaturaDataGenerator();
        this.c = j.getC();
        this.f = j.getF();
        this.locs = j.getL();
        this.loggedIn = null;
        this.run();
    }
}
