
import java.util.Scanner;

public class NumberASCII {
    public static void main(String[] args) {
        
        Scanner in = new Scanner(System.in);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
        System.out.println("Let's find that ASCII number!\n\n");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
        
        //prompting the user to input a letter
        System.out.print("Please enter a letter from a to z: ");
        char letter = in.next().charAt(0);
        int asciiNumber = (int) letter;

        //evaluating whether the ascii number of that letter is even or odd
        String parity;
        if (asciiNumber % 2 == 0) {
            parity = "even";
        }
        else {
             parity = "odd";
        }
        
        System.out.println("The ASCII number of " + letter + " is " + parity +
                " number " + asciiNumber + ".");

        //determining whether the number is a multiple of 2, 3, 5, and 7
        int[] multipleOf = {2, 3, 5, 7};
        int notMultiple = 0;

        for (int i = 0; i < multipleOf.length; i++){
            int remainder = asciiNumber % multipleOf[i];
            switch (remainder) {
                case 0: 
                    System.out.println("It is a multiple of " + multipleOf[i] + ".");
                    break;
                default: notMultiple++; 
            }
        }

        if (notMultiple == multipleOf.length) {
            System.out.println("It is none of 2, 3, 5, or 7.");
        }

        System.out.println("\n\nHave a good day!!\n");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n\n");

    }
}
