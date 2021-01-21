/** The BattleshipGrid class contains the building blocks 
 * for the game. It is in this file that the grid is defined, 
 * as well as all the methods that provide the functionality of 
 * the game. */

import java.util.Random;
import java.lang.*;


public class BattleshipGrid {



    // the grid is made up of a three-dimensional array:
    // 8 rows, 8 columns, and a set of properties for each 
    // square of the grid: whether the square has been hit, which object, 
    // if any, occupies the square, and which player placed an object
    // if there is one
    private int[][][] grid = new int[8][8][3];


    // the integers have no significance on their own, they 
    // are simply used to indicate who placed an item there
    // integers are used instead of booleans because there are three options:
    // human, computer, and empty
    private final int HUMAN = 11;
    private final int COMP = 22;
    // there are certain methods (like objectSet and fireRocket) where booleans are of
    // more use than integers
    private final boolean H = true;
    private final boolean C = false;

    // once again, these values are chosen arbitrarily
    // it wasn't necessary to use integers as identifiers, but it 
    // seemed the most convenient
    private final int HIT = 8;
    private final int MISS = 9;
    
    private final int EMPTY = 0;
    private final int GRENADE = 1;
    private final int SHIP = 2;

    // the following arrays are to keep track of the development
    // of the game: how many ships and grenades need to be set, whose turn it is,
    // how many ships each player has 
    private int[] unusedShips = new int[2];
    private int[] unusedGrenades = new int[2];
    private int[] sunkenShips = new int[2];
    private int[] turnTracker = new int[3];

    // initializing the values that are to be subbed into each position 
    // during game play
    private int objectSlot = EMPTY;
    private int playerSlot = EMPTY;

    // these chars are used in the process of interpreting user input 
    // for coordinates
    private char x;
    private char y;

    // this will be used to generate the coordinate choices of the computer
    private Random botPick = new Random();

    // to print the text to the console one letter at a time, so that the user can
    // have an easier time understanding the development of events (without it 
    // it can be difficult to know that the computer has already played) 
    // it's also just a stylistic choice though
    public void typeString(String dialogue) throws InterruptedException {
        char[] splitDialogue = dialogue.toCharArray();
        for (int i = 0; i < splitDialogue.length; i ++) {
            char x = splitDialogue[i];
            Thread.sleep(100);
            System.out.print(x);
        }
    }

    // method used to initialize the game
    public void newGame() {
        // initializing each square on the grid
        for (int i=0; i < 8; i++) {
            for (int j=0; j < 8; j++) {
                grid[i][j][0] = MISS;
                grid[i][j][1] = EMPTY;
                grid[i][j][2] = EMPTY;
            }
        }
        
        // setting up the number of objects each player can place, and 
        // how many ships are still afloat
        // index 0 is for the human player (the logic is that the human
        // plays first), index 1 is for the computer
        unusedGrenades[0] = 4;
        unusedGrenades[1] = 4;
        unusedShips[0] = 6;
        unusedShips[1] = 6;

        sunkenShips[0] = 0;
        sunkenShips[1] = 0;

        // the way the turnTracker is used will be explained below
        turnTracker[0] = 1;
        turnTracker[1] = 0;
    } 



    // method used to print the grid to the console
    // what is printed depends on the status of each square 
    // (i.e. whether the square has been hit or not, which object 
    // was there, who placed the object)
    public void printGrid() {
        for (int i =0; i < 8; i++) {
            for (int j=0; j < 8; j++) {
                String displayPos = " _ ";
                if (grid[i][j][0] == HIT) {
                    if (grid[i][j][1] == EMPTY) {
                        displayPos = " * ";
                    } else if (grid[i][j][1] == GRENADE) {
                        displayPos = " g ";
                    } else {
                        displayPos = " s ";
                    }
                    // the characters that represent the computer's objects are 
                    // displayed in upper case 
                    if (grid[i][j][2] == COMP) {
                        displayPos = displayPos.toUpperCase();
                    }
                }

                if (j==7) {
                    System.out.print(displayPos + "\n");
                } else if (j==0) {
                    System.out.print("\t\t" + displayPos);
                } else {
                    System.out.print(displayPos);
                }
            }
        }
    }
    


    // used to determine whether the loop that allows each player to set objects 
    // can go on (as the title suggests, it does so by determining whether the player 
    // has any objects left to set)
    public boolean hasUnusedObjects(boolean isShip, boolean isHuman) {
        boolean status = false;
        if (isHuman  == true) {
            if (isShip == true) {
                status = unusedShips[0] > 0;
            } else {
                status = unusedGrenades[0] > 0;
            }
        } else {
            if (isShip == true) {
                status = unusedShips[1] > 0;
            } else {
                status = unusedGrenades[1] > 0;
            }
        }
        return status;
    } 

    // used to determine whether the gameplay can continue
    // the method does so by checking if one of the player has
    // lost all of their ships
    public boolean noWinner() {
        boolean stillAfloat = true;
        if (sunkenShips[0] == 6 || sunkenShips[1] == 6) {
            stillAfloat = false;
        }
        return stillAfloat;
    }
    

    // used to translate the user's input (for the coordinates) 
    // from characters to integers (that can be used as index values)
    private int[] coordinatesTranslate(char[] position) {

       x = Character.toUpperCase(position[0]); 
       y = position[1]; 
       
       int j = Character.getNumericValue(y) - 1;
       int i = 9;
        switch (x) {
            case 'A': i = 0; break;
            case 'B': i = 1; break;
            case 'C': i = 2; break;
            case 'D': i = 3; break;
            case 'E': i = 4; break;
            case 'F': i = 5; break;
            case 'G': i = 6; break;
            case 'H': i = 7; break;
            default: i = 9; break;
        }
        int[] coordinates = {i, j};
        return coordinates;
    }

    // used to verify whether a position may be used (for setting objects and
    // targeting with rockets)
    // only allows for squares within the bounds of the grid to be chosen
    // when there are objects to be placed, it only allows for the selection of empty 
    // squares. when rockets are being fired, the only restriction is that the value
    // is within the bounds
    public boolean posVerify(int i, int j, boolean player) throws InterruptedException {
        boolean validI = i >= 0 && i < 8;
        boolean validJ = j >= 0 && j < 8;
        boolean validPos = true;
        boolean allSet = unusedShips[0] == 0 && unusedShips[1] == 0 && unusedGrenades[0] == 0 && unusedGrenades[1] == 0;
       
        if (!validI || !validJ) {
           validPos = false;
           if (player == H){
                typeString("Coordinates out of bounds.");
           }
       } else if (grid[i][j][1] != EMPTY) {
           if (allSet == true) {
                validPos= true;
           } else {
                validPos = false;
                if (player == H) {
                    typeString("Coordinates are already in use.");
                }
           }
       }
       return validPos;
    }

    // used to set the computer's objects 
    public void objectSet(boolean object) throws InterruptedException {
        playerSlot = COMP;
        if (object == true) {
            objectSlot = SHIP;
        } else {
            objectSlot = GRENADE;
        }
        
        // selecting the coordinates for the computer's selection at random
        int i = botPick.nextInt(8);
        int j = botPick.nextInt(8);

        // quitting the method if the random selection is not valid
        if (posVerify(i, j, C) == false) {
            return;
        }

        // modifying the grid according to the object and object owner
        // also keeping track of remaining objects to be set
        if (objectSlot == SHIP) {
            unusedShips[1]--;
            grid[i][j][1] = SHIP;
            grid[i][j][2] = COMP;
        } else {
            unusedGrenades[1]--;
            grid[i][j][1] = GRENADE;
            grid[i][j][2] = COMP;
        }
    }

    // another method used to set objects (grenades and ships) on the grid
    // could be easily adapted for a game that has two human players
    public void objectSet(boolean object, boolean player, char[] position) throws InterruptedException {
       
       String objectName = "";
       if (object == false) {
           objectSlot = GRENADE;
           objectName = "Grenade";
       } else {
           objectSlot = SHIP;
           objectName = "Ship";
       }

       if (player == true) {
           playerSlot = HUMAN;
       } else {
           playerSlot = COMP;
       }

       int[] pos = coordinatesTranslate(position);
       int i = pos[0]; int j = pos[1];

       boolean positionValidity = posVerify(i, j, player);
       // quitting the method if the selection is not valid
       if (positionValidity == false) {
           return;
       }

       if (positionValidity == true) {
           grid[i][j][1] = objectSlot;
           grid[i][j][2] = playerSlot;
           typeString(objectName + " set!");

           if (objectSlot == GRENADE && playerSlot == HUMAN) {
               unusedGrenades[0] --;
           } else if (objectSlot == GRENADE && playerSlot == COMP) {
               unusedGrenades[1] --;
           } else if (objectSlot == SHIP && playerSlot == HUMAN) {
               unusedShips[0]--;
           } else if (objectSlot == SHIP && playerSlot == COMP) {
               unusedShips[1]--;
           }
       }
    }
    
    // basically, this method is what one turn does
    public void fireRocket(boolean player, char[] position) throws InterruptedException {
        int i; int j;
        
        // spacing out the info displayed for easier readability
        System.out.println("\n\n\n");

        // determining the coordinates of the player's target
        if (player == H) {
            int[] pos = coordinatesTranslate(position);
            i = pos[0]; j = pos[1];
        } else {
            i = botPick.nextInt(8); j = botPick.nextInt(8);
        }
        // verifying that the coordinates are valid
        boolean positionValidity = posVerify(i, j, player);

       // quitting the method if the selection is not valid
       if (positionValidity == false) {
           typeString("\nYer ambition's got ta be admired, but that target is out of range...\n\n");
           return;
       }
        
       
        // some booleans used to summarize possible events in a turn
        // making it easier to follow the decision tree that follows
        boolean hHitOpp = player == H && grid[i][j][2] == COMP;
        boolean cHitOpp = player == C && grid[i][j][2] == HUMAN;
        boolean hitOpp = hHitOpp || cHitOpp;
        boolean shipDown = grid[i][j][1] == SHIP;
        boolean success = hitOpp && shipDown;
        boolean miss = grid[i][j][0] == HIT || grid[i][j][1] == EMPTY;
        boolean grenadeHit = !miss && !shipDown;
  
        // decision tree that determines how to modify the grid
        // and the sunken ship tally, 
        // as well as what message to display to the user
        if (positionValidity == true) {
            if (miss) {
                if (player == H) {
                    typeString("A missed opportunity...");
                } else {
                    typeString("Yer enemy missed! Ha!");
                }
    
            } else if (success) {
                if (player == H) {
                    typeString("Excellent! You've taken down an enemy ship!");
                    sunkenShips[1]++;
                } else {
                    typeString("Sorry, Bucko... they've taken out one of yer ships!");
                    sunkenShips[0]++;
                }
               
            } else if (hitOpp & !success) {
                if (player == H) {
                    typeString("Blimey! You've fallen onto an enemy's grenade!");
                } else {
                    typeString("Good thinking with that grenade there!\nYou've bought yerself some time.");
                }
            } else if (!hitOpp & shipDown) {
                if (player == H) {
                    typeString("Oh the tragedy! You've taken down one of yer own...");
                    sunkenShips[0]++;
                } else {
                    typeString("Yer enemy seems to have taken down one of their own!");
                    sunkenShips[1]++;
                }
            } else if (!hitOpp) {
                if (player == H) {
                    typeString("Yer a darn scurvy dog!\nHitting one of yer own grenades like that...");
                } else {
                    typeString("Well would you look at that! Yer opponent shot up their own grenade! Ha!");
                }
            }
            grid[i][j][0] = HIT;
        } else {
            typeString("Yer ambition's got ta be admired, but that target is out of range...");
        }

        System.out.println("\n\n");
        updateTurnTracker(player, positionValidity, grenadeHit);
    }

    // this method, called at the end of each fireRocket call, is what defines 
    // which player is up next
    private void updateTurnTracker(boolean currentPlayer, boolean positionValidity, boolean grenadeHit) {
        
        // if the targeted position is valid, the player's turn count
        // goes down by one, and the oponent's turn count increases by one
        // (but the latter only happens if the next player is not the current 
        // player, so as to avoid the issue of a player getting extra turns if a
        // grenade is hit and the opponent plays twice)
        if (positionValidity == true) {
            if (currentPlayer == H) {
                turnTracker[0]--;
                if (nextTurn(currentPlayer) != currentPlayer) {
                    turnTracker[1]++;
                }
            } else {
                turnTracker[1]--;
                if (nextTurn(currentPlayer) != currentPlayer) {
                    turnTracker[0]++;
                }
            }
            // giving the opponent an extra turn if the player hits a grenade
            if (grenadeHit == true) {
                if (currentPlayer == H) {
                    turnTracker[1]++;
                } else {
                    turnTracker[0]++;
                }
            }
        }
    }

    // this method is used to interpret the turnTracker based on who is the
    // current player and how many turns they have left
    public boolean nextTurn(boolean player) {
        boolean nextPlayer = !player;
        int playerIndex = 0;
        if (player == C) {
            playerIndex = 1;
        }
        if (turnTracker[playerIndex] != 0) {
            nextPlayer = player;
        }
        return nextPlayer;
    }

    // method used to determine who is the winner of the game
    public boolean luckyDuck() {
        boolean winner = false;
        if (sunkenShips[1] == 6) {
            winner = true;
        }
        return winner;
    }
}
