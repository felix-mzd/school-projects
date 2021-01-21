
import java.lang.Math;
import java.util.Scanner;

public class GroceryPriceCalculator {
    public static void main(String[] args) {
        //Making a scanner to read user input
        Scanner in = new Scanner(System.in);

        //Greeting the customer
        System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
        System.out.println("Organic Groceries in Bulk");
        System.out.println("\n\nAvailable in store today:");
        System.out.println("- Fruit, $26.99;");
        System.out.println("- Cheese, $22.99;");
        System.out.println("- Dairy, $13.99;");
        System.out.println("- Meat, $56.99;");
        System.out.println("- Seafood, $38.99");
        System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");

        //defining the prices for each item available in store
        double fruitPrice = 26.99;
        double cheesePrice = 22.99;
        double dairyPrice = 13.99;
        double meatPrice = 56.99;
        double seafoodPrice = 38.99;
        
        //prompting the user to indicate which items they are purchasing
        System.out.println("In the order in which they appear on the list, and separated by spaces,");
        System.out.println("please enter the quantities purchased of each item on the list:");
        String quantities = in.nextLine();

        //splitting the user's input into five strings, one for item available
        //transforming each string into an integer
        String[] qty = quantities.split(" ");
        int qtyFruit = Integer.parseInt(qty[0]);
        int qtyCheese = Integer.parseInt(qty[1]);
        int qtyDairy = Integer.parseInt(qty[2]);
        int qtyMeat = Integer.parseInt(qty[3]);
        int qtySeafood = Integer.parseInt(qty[4]);

        //tallying the prices of each item, according to the quantities the user entered
        double fruitSubtotal = qtyFruit * fruitPrice;
        double cheeseSubtotal = qtyCheese * cheesePrice;
        double dairySubtotal = qtyDairy * dairyPrice;
        double meatSubtotal = qtyMeat * meatPrice;
        double seafoodTotal = qtySeafood * seafoodPrice;

        double totalSansDiscount = fruitSubtotal + cheeseSubtotal + dairySubtotal + meatSubtotal + seafoodTotal;

        //if else blocks to determine the applicable discount and point promotion
        //the discount variable is the amount customer will pay, not the amount deducted
        int pointPromo;
        double discount;
        if (totalSansDiscount < 250) {
            discount = 0.9;
            pointPromo = 2;
        } 
        else if (totalSansDiscount <= 500) {
            discount = 0.85;
            pointPromo = 2;
        }
        else {
            discount = 0.8;
            pointPromo = 3;
        }

        
        //determining the price with discount
        double totalPrice = discount * (totalSansDiscount - seafoodTotal) + seafoodTotal;

        //prompting the user for their membership status
        System.out.println("\n\nDo you have a membership? (Y/N)");
        String memberStatus = in.next().toUpperCase();

        System.out.print("\n\nThe total price is " + totalPrice + ". ");


        //Calculating the number of points accumulated for this bill
        if (memberStatus.equals("Y")) {
            double points = pointPromo * totalPrice;
            int actualPoints = (int) Math.ceil(points);
            System.out.println("You will receive " + actualPoints + " points.");
        }

        System.out.println("\n\nThanks for shopping! \nSee you next time!\n\n");

    }
}
