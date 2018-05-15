package Main;

 


import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;


import Contribuintes.Contribuintes;
import Contribuintes.Contribuinte;
import Fatura.Faturas;


public class Main implements Serializable{
    private static Menu menu = new Menu();

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
    
    public static void run() {
        int x = login();

        try{
        Contribuintes cs = initContribuintes("filepath"); //tu sabes o que meter.....;)
        menu.run(cs);
    }
        catch (FileNotFoundException e){
            System.out.println("Could not find a file with that name"); menu.run();}
        catch (IOException e){
            System.out.println("There was an unexpected error when accessing to that file");}
        catch (ClassNotFoundException e){
            System.out.println("Error! The file does not contain the class specified");}
            
         
    }
    
    
    
    public static void main(String[] args) {
        Main m = new Main();
        m.run();
    }
}
