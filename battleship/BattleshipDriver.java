
/** This program is a simplified version of the game Battleship. 
 * The BattleshipDriver takes the building blocks provided by BattleshipGrid and
 * organizes them into a playable algorithm. Most of the comments here 
 * are just to explain the actual structure of the game/gameplay, to understand 
 * how the methods work, see the BattleshipGrid.java file */


import java.util.Scanner;



public class BattleshipDriver {
    //need to include the throws InterruptedException here because the typeString
    // method that prints the dialogue makes use of delays
    public static void main(String[] args) throws InterruptedException {

        // these booleans are used as parameters in the methods 
        // that determine turns, placement of objects, and the winner
        // H is for human, C is for computer, S is for ship, G is for grenade
        final boolean H = true;
        final boolean C = false;
        final boolean S = true;
        final boolean G = false;

        Scanner in = new Scanner(System.in);

        //initializing the game 
        BattleshipGrid play = new BattleshipGrid();
        play.newGame();


        // intro greeting and instructions for entering valid coordinates
        System.out.println("BATTLESHIP");
        play.typeString("Let's play!!");

        String coordinates = "Coordinates are of the form XY, \nwhere X is a letter from A to H,\nand Y is a number from 1 to 8.\ne.g. A4 or D6\n\n";

        System.out.println("\nYou must place 6 ships and 4 grenades on the grid.\n" + coordinates);


        // prompting the user to place their ships and grenades
        while (play.hasUnusedObjects(S, H)) {
            System.out.print("\nEnter the coordinates for a ship: ");
            char[] shipPos = in.next().toCharArray();
            play.objectSet(S, H, shipPos);
        }

        while (play.hasUnusedObjects(G, H)) {
            System.out.print("\nEnter the coordinates for a grenade: ");
            char[] grenadePos = in.next().toCharArray();
            play.objectSet(G, H, grenadePos);
        }

        // setting the computer's ships and grenades at random
        while (play.hasUnusedObjects(S, C)) {
            play.objectSet(S);
        }
        while (play.hasUnusedObjects(G, C)) {
            play.objectSet(G);
        }

        // letting the player know that the game is all set to be played
        play.typeString("\n\nYour opponent has set their ships and grenades.");
        play.typeString("\n\n\nPrepare to battle!");
        System.out.println("\nFirst player to lose all ships loses.");



        // first turn goes to the human player
        boolean player = H;
        // an empty array is used as dummy input by the computer on its turn
        char[] botRocket = new char[2];

        // this loop is where the gameplay actually happens
        // the loop ends once one of the players has lost all of their ships
        while (play.noWinner() == true) {
            System.out.println();
            if (player == H) {
                play.typeString("\n\n\nWhat are the coordinates for yer next target? ");
                char[] rocketPos = in.next().toCharArray();
                play.fireRocket(player, rocketPos);
            } else {
                play.fireRocket(C, botRocket);
            } 
            player = play.nextTurn(player);
            play.printGrid();
            System.out.println();
        } 

        // once the gameplay has ended, the winner is declared
        if (play.luckyDuck() == H) {
            play.typeString("\n\n\nOY! Looks like ye've won! Congrats, Cap'n!");
        } else {
            play.typeString("\n\n\nCrew overboard! Looks like ye've lost.\n\n");
        }

        play.printGrid();

        // closing message so that the user knows the game is quitting
        play.typeString("\n\n\n\nShiver me timbers! That was a battle fer the ages...");
        play.typeString("I bid ye safe travels and farewell.\n\n");
        }


    }
