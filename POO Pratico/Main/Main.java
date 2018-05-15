package Main;

 


import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import Contribuintes.Contribuintes;
import Contribuintes.Contribuinte;
import Fatura.Faturas;

public class Main{
    private static Menu menu = new Menu();
    
    private static void initContribuintes(){
        // c = Ler contribuintes de um ficheiro
    }
    
    private static int login() {
        return -1;
    }
    
    public static void run() {
        int x = login();
        initContribuintes();
        menu.run();
    }
    
    
    
    public static void main(String[] args) {
        Main m = new Main();
        m.run();
    }
}
