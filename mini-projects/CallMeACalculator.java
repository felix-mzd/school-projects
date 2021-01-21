
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.*;

public class CallMeACalculator {
	
	//Method that adds a delay between actions
	//For delays equal to or greater than one second
	public static void wait(int sec) {
		try {
			TimeUnit.SECONDS.sleep(sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	} 
	
	static char[] title = {'T', 'I', 'M', 'E', '_','T', 'O', '_', 'C', 'A', 'L', 'C', 'U', 'L', 'A', 'T', 'E', '!', '!', '!', '!'};
	
	
	public static void main(String[] args) throws InterruptedException {
		
		Scanner in = new Scanner(System.in);
		
		//A loop that prints each letter one at a time, to produce the effect of the letters being typed in real time
		//Didn't use the wait method I defined above because I wanted the pauses here to be less than a second
		int j = title.length; 
		for (int i = 0; i < j; i++) {
			char x = title[i];
			System.out.print(x);
			Thread.sleep(100);
		}
		
		//Throughout the rest of the program, I included some pauses of 1-2 seconds so that the user doesn't feel rushed
		
		//Here the program greats the user and prompts them to input two integers
		wait(2);
		System.out.println("\n\nHello!\n"); wait(1);
		System.out.println("Please type the integer of your choice:");
		int firstInt = in.nextInt(); wait(1);
		System.out.println("\n\nPlease type a second (non-zero) integer:");
		int secondInt = in.nextInt(); wait(2);
		
		//Using some simple arithmetic to manipulate the user's inputs
		int plus = firstInt + secondInt; 
		int minus = firstInt - secondInt;
		int times = firstInt * secondInt;
		double divided = (double)firstInt / secondInt;
		
		//Printing the results of the calculations above
		System.out.println("\n\n" +firstInt + " + " + secondInt + " = " + plus); wait(1);
		System.out.println(firstInt + " - " + secondInt + " = " + minus); wait (1);
		System.out.println(firstInt + " * " + secondInt + " = " + times); wait(1);
		System.out.println(firstInt + " / " + secondInt + " = " + divided); wait(2);
		
		//Politely informing the user that the program has finished running
		System.out.println("\n\nThanks for calculating!"); wait(1);
		System.out.println("Bye now.");
		
		
	}
	
	
	
	
}
