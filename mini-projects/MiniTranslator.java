
//importing the scanner package
import java.util.Scanner;

public class MiniTranslator{
    public static void main(String[] args) {

        //greeting the user
        System.out.println("\n\n• • • • • • • • • • • • • • • • • • • • • • • • • • \n");
        System.out.println("Let me show you that I understand English!\n");
        System.out.println("\n• • • • • • • • • • • • • • • • • • • • • • • • • • \n\n");

        Scanner in = new Scanner(System.in);

        //starting a loop so that the user may use the program until they press q to quit
        do {
            //prompting the user for a sentence to analyze
            System.out.println("Please enter the input sentence: \n");

            //splitting up the user's input at the spaces
            String input = in.nextLine();
            String[] words = input.split(" ");

            //based on the template, the year is the last word
            //since the index of the array starts at zero, the index for the year is one less 
            //than the number of elements in the array
            final int CURRENT_YEAR = 2020;
            String yearWord = words[words.length - 1];

            //checking whether the last character is a period before changing the string to an int
            char last = input.charAt(input.length() - 1);
            if (last == '.') {
                yearWord = yearWord.substring(0, yearWord.length() - 1);
            }
            int yearOfArrival = Integer.parseInt(yearWord); 
            int lengthOfStay = CURRENT_YEAR - yearOfArrival;

            //initializing the indexes for the words of interest before evaluating them
            int afterName = 0;
            int cityBeginning = 0; 
            int cityEnd = 0;
            int countryBeginning = 0; 
            int countryEnd = 0; 


            //for the NAME, CITY, and COUNTRY, I didn't want to assume the user
            //would input names without spaces (e.g. New York), so I made some 'if' and 'for' 
            //statements to determine each word based on their positions in relation to known variables 
            //before each of these loops I indentify the variables I use to situate the given word


            //determining the positions of the remaining 3 strings of interest within the array
            for (int i = 0; i < words.length; i ++) {
                switch (words[i]) {
                    case ("came"):
                        afterName = i; break;
                    case ("to"):
                        cityBeginning = i + 1; break;
                    case ("in"):
                        countryEnd = i - 1; break;
                }
                char lastChar = words[i].charAt(words[i].length() - 1);
                if (lastChar == ',') {
                    cityEnd = i;
                    countryBeginning = i + 1;

                }
            }

    
            //the name ends before the word 'came'
            String name = "";
            for (int j = 0; j < afterName; j++) {
                name += words[j] + " ";
            }

            //the city starts after 'to' and ends with a comma
            String city = "";
            if (cityBeginning == cityEnd) {
                city = words[cityEnd];
                city = city.substring(0, (city.length() - 1)) + " ";
            } else {
                for (int k = cityBeginning; k <= cityEnd; k++) {
                    city += words[k] + " "; 
                }
                city = city.substring(0, (city.length() - 2)) + " ";
            }

            //the country starts after the comma and finishes before 'in'
            String country = "";
            if (countryBeginning == countryEnd) {
                country = words[countryEnd];
            } else {
                for (int l = countryBeginning; l <= countryEnd; l++){
                    country += words[l] + " ";
                }
                country = country.substring(0, (country.length() - 1));
            }

            //now that all words of interest have been found, it's time to print the desired output
            System.out.print("\n" + name + "stays in " + city + "for " + lengthOfStay + " years. ");
            System.out.println(city + "is in " + country + ".");

            //letting the user know that the program is ready to start over or quit
            System.out.println("\n\n• • • • • • • • • • • • • • • • • • • • • • • • • • \n");
            System.out.println("Thanks for letting me show off :~)");
            System.out.println("Press enter to restart or q to quit");
            System.out.println("\n• • • • • • • • • • • • • • • • • • • • • • • • • • \n\n");

        }
        //below is the condition that allows the user to quit the program
        while (!in.nextLine().equals("q"));
    }
}
