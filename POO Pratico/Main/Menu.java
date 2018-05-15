package Main;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import Contribuintes.Contribuintes;
import Contribuintes.Contribuinte;
import Contribuintes.ContribuinteIndividual;
import Contribuintes.ContribuinteEmpresarial;
import Fatura.Faturas;
/**
 * Write a description of class Menu here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Menu
{   
    private static Faturas f;
    private static Contribuintes c;
    
    
    private static Object getInfo(String Message, Class<?> cls){
        do{
            System.out.println(Message);
            Scanner s = new Scanner(System.in);
            if(cls == String.class){
                try{
                    String res = s.nextLine();
                    return res;
                } catch (InputMismatchException e){
                    System.out.println("Insert a text please");
                }
            }
            if(cls == Integer.class){
                try{
                    int res = s.nextInt();
                    return res;
                } catch (InputMismatchException e){
                    System.out.println("Insert a number only please");
                }
            }
        }while(true);
    }
    
    

    
    private static int registerMenuContrInd(){
        ContribuinteIndividual c = new ContribuinteIndividual();
        c.setNif((int) getInfo("Introduza o Nif", Integer.class));
        c.setNome((String) getInfo("Introduza o Nome", String.class));
        c.setEmail((String) getInfo("Introduza o Email", String.class));
        c.setMorada((String) getInfo("Introduza a Morada", String.class));
        c.setNumDependentesAgregado((int) getInfo("Introduza o numero do agregado familiar", Integer.class)); 
        c.setCoefFiscal((int) getInfo("Introduza o coeficiente fiscal", Integer.class));
        c.setPassword((String) getInfo("Introduza a sua palavra-passe", String.class));
        // metodo que coloca o contribuinte no ficheiro
        
        return welcomeMenu();
    }
    
    private static int registerMenuContrEmpr(){
        ContribuinteEmpresarial c = new ContribuinteEmpresarial();
        c.setNif((int) getInfo("Introduza o Nif", Integer.class));
        c.setNome((String) getInfo("Introduza o Nome", String.class));
        c.setEmail((String) getInfo("Introduza o Email", String.class));
        c.setMorada((String) getInfo("Introduza a Morada", String.class));
        c.setFatorDeducao((int) getInfo("Introduza o fator de deduçao", Integer.class));
        c.setPassword((String) getInfo("Introduza a sua palavra-passe", String.class));
        // metodo que coloca o contribuinte no ficheiro
        
        return welcomeMenu();
    }
    
    private static int registerMenu(){
        System.out.println("Registrando um novo Contribuinte");
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        
        menuString.add("Registrar contribuinte Individual");
        menuString.add("Registrar contribuinte Empresarial");
        menuString.add("Retroceder");
        toRun.add(Menu::registerMenuContrInd);
        toRun.add(Menu::registerMenuContrEmpr);
        toRun.add(Menu::welcomeMenu);
        
        return genericMenu(menuString, toRun);
    }
    
    private static int loginMenu(){
        int nif;
        String pass;
        Contribuinte contr = null;
        
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
            contr = c.login(nif, pass);
            
        if(contr == null){
            System.out.println("User not found/Wrong password");
            return welcomeMenu();
        }
        
        
        return -1;
    }
    
    
    public static int welcomeMenu(){
        System.out.println("Welcome User");
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Callable<Integer>> toRun = new ArrayList<>();
        
        menuString.add("Registrar novo Contribuinte");
        menuString.add("Log In");
        toRun.add(Menu::registerMenu);
        toRun.add(Menu::loginMenu);
        
        return genericMenu(menuString, toRun);
    }
        
    
    public static int genericMenu(ArrayList<String> menuString, ArrayList<Callable<Integer>> toRun){
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
                op = -1;
            }
            if(op > 0 && op <= toRun.size()){
                try{
                    op = toRun.get(op-1).call();
                } catch (Exception e){
                    System.out.println("Please try again");
                    op = -1;
                }
            }
        }while(op != 0);
        scan.close();
        return op;
    }
    
    public void run(){
        welcomeMenu();
    }
}
