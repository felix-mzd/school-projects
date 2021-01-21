/* the following program removes duplicate values 
 * from data input by a user */

import java.util.Scanner;

public class DuplicateElimination {
    public static void main(String[] args) {

        //greeting the user
        System.out.println("\nGreetings!");
        System.out.println("Today I shall remove duplicate data from a set of 10 integers of your choosing...\n");

        Scanner in = new Scanner(System.in);

        //preparing an array to hold values input by user
        int[] values = new int[10];

        //setting a value to indicate an empty spot in the array
        int empty = -1;
        for (int i = 0; i < values.length; i++){
            values[i] = empty;
        }
        //prompting the user for input
        System.out.println("Please input a value in [10,100]");

        //filling in the array with the user's input
        for (int i = 0; i < values.length; i++) {
            
            //letting the user know which value they're inputting
            System.out.print("value " + (i+1) + ": ");
            values[i] = in.nextInt();

            //if the user inputs a value outside of the desired range
            //reminding the user that the input must be within given range
            while (values[i] < 10 || values[i] > 100) {
                System.out.print("That value won't do, please input a number in [10, 100]: ");
                values[i] = in.nextInt();
            }

            //eliminating duplicate values as they fill the array
            if (i > 0) {
                for (int j = 0; j < i; j++) {
                    if (values[i] == values[j]) {
                        values[i] = empty;
                    }
                }
            }
        }

        //Letting the user know which, of the values they provided, are unique
        System.out.println("\nThe unique values are:");
        for (int i = 0; i < values.length; i++) {
            if (values[i] != empty) {
                System.out.print(values[i] + " ");
            }
        }
        
        //Letting the user know the program is done running
        System.out.println("\nI hope this helps!\nGoodbye now.");
        System.out.println();
    }
}
