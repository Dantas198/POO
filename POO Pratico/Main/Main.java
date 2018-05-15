package Main;

 


import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;

//import Contribuintes.Contribuintes;
//import Contribuintes.Contribuinte;
//import Fatura.Faturas;

public class Main implements Serializable {
    private static Faturas f;
    private static Contribuintes c;
    
    private static Contribuintes initContribuintes(String filepath) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(filepath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Contribuintes cs = (Contribuintes) ois.readObject();
        ois.close();
        return cs;
    }
    
    private static int login() {
        return -1;
    }
    
    private static int registerMenuContrInd(){
        return -1;
    }
    
    private static int registerMenuContrEmpr(){
        return -1;
    }
    
    private static int registerMenu(){
        int op;
        System.out.println("Registrando um novo Contribuinte");
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Runnable> toRun = new ArrayList<>();
        
        menuString.add("Registrar contribuinte Individual");
        menuString.add("Registrar contribuinte Empresarial");
        menuString.add("Retroceder");
        toRun.add(Main::registerMenuContrInd);
        toRun.add(Main::registerMenuContrEmpr);
        toRun.add(Main::welcomeMenu);
        
        op = genericMenu(menuString, toRun);
        return op;
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
            
        if(contr == null)
            return -1;
        
        // TO DO: caso em que o login tenha sido feito
        
        return -1;
    }
    
    
    public static void welcomeMenu(){
        int op;
        System.out.println("Welcome User");
        ArrayList<String> menuString = new ArrayList<>();
        ArrayList<Runnable> toRun = new ArrayList<>();
        
        menuString.add("Registrar novo Contribuinte");
        menuString.add("Log In");
        toRun.add(Main::registerMenu);
        toRun.add(Main::loginMenu);
        
        op = genericMenu(menuString, toRun);
    }
        
    
    public static int genericMenu(ArrayList<String> menuString, ArrayList<Runnable> toRun){
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
                System.out.println("Input Errado"+ e.getMessage());
                op = -1;
            }
            if(op > 0 && op <= toRun.size())
                toRun.get(op-1).run();
        }while(op != 0);
        scan.close();
        return op;
    }
    
    public void run() {
        int x = login();
        try{
        Contribuintes cs = initContribuintes(filepath); //tu sabes o que meter.....;)
    }
        catch (FileNotFoundException e){
            System.out.println("Could not find a file with that name");}
        catch (IOException e){
            System.out.println("There was an unexpected error when accessing to that file");}
        catch (ClassNotFoundException e){
            System.out.println("Error! The file does not contain the class specified");}
        welcomeMenu();
    }
    
    
    
    public static void main(String[] args) {
        Main m = new Main();
        m.run();
    }
}
