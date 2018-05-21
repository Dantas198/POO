package menu;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import contribuintes.Contribuinte;
import contribuintes.Contribuintes;

import java.lang.ClassNotFoundException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.io.File;

import fatura.Faturas;


public class Main implements Serializable{
    private static Menu menu;

    
    private static void initMenu(String filepath) throws FileNotFoundException, IOException{
        FileInputStream fis = new FileInputStream(new File(filepath));
        ObjectInputStream ois = new ObjectInputStream(fis);
        try{
            menu = (Menu) ois.readObject();
        } catch (ClassNotFoundException e){
            menu = new Menu();
        }
        ois.close();
    }
    
    
    private static void run(){
        menu = new Menu();
        try{
          initMenu("Menu.txt");
          menu.run();
        }
        catch (FileNotFoundException e){
            System.out.println("Could not find a file with that name"); menu.initRun();}
        catch (IOException e){
            System.out.println("There was an unexpected error when accessing to that file"); menu.initRun(); e.printStackTrace(System.out);}
        catch (NullPointerException e){
            System.out.println("Menu doesn't exist"); menu.initRun();
        }
         try{
             menu.saveMenu("Menu.txt");
         } catch (FileNotFoundException e){
             System.out.println("Could not find a file with that name");
         } catch (IOException e){
            System.out.println("There was an unexpected error when accessing to that file");e.printStackTrace(System.out);
         }
           catch (ClassNotFoundException e){
            System.out.println("Error! The file does not contain the class specified");
         }
      }
      
    
    
    public static void main(String[] args){
        run();
        return;
    }
}
