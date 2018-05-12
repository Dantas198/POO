package Main;

 


import java.util.InputMismatchException;
import java.util.Scanner;

import Contribuintes.Contribuintes;
import Fatura.Faturas;

public class Main {
	private static Faturas f;
	
	private static int login() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private static Contribuintes c;
	
	public static void userInterface() {
		System.out.println("Welcome User xxx");
		int input;
		do {
			Scanner s = new Scanner(System.in);
			try {				
				input = s.nextInt();
			} catch (Exception e) {
				System.out.println("Input Errado"+ e.getMessage());
				input = -1;
			}	
			s.close();
		} while (input != 0);
	}
		
	public void run() {
		int x = login();
		userInterface();
		
	}
	
	public static void main(String[] args) {
		Main m = new Main();
		m.run();
	}
}
