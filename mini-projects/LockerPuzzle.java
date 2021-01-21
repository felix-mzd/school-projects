
/* here is a program that solves the locker puzzle, where each student 
 * passing through the hall opens or closes lockers that are multiples of 
 * their student number */

public class LockerPuzzle {
    public static void main(String[] args) {
        //greeting statement
        System.out.println("After students have been opening and closing lockers all day long...");
        System.out.println("Here are the results:\n");

        //closing all the lockers before the students pass through
        boolean isClosed = true;
        boolean[] lockers = new boolean[100];
        for (int i=0; i < lockers.length; i++) {
            lockers[i] = isClosed;
        }

        //numbering all 100 students
        int[] students = new int[100];
        for (int i = 0; i < students.length; i++) {
            students[i] = i + 1;
        }

        //each student walks through the hall
        for (int i = 0; i < students.length; i++) {
            //int studentNum = i + 1;

            for (int j = 0; j < lockers.length; j++) {
                //assigning a locker number variable because 
                //the indexing starts at zero
                int lockerNum = j + 1;
                
                //as the student passes by each locker, 
                //the student changes the locker's closed/open status
                //if the locker number is a multiple of the student number
                if (lockerNum % students[i] == 0) {
                    lockers[j] = !lockers[j];
                }
            }
            

        }
        
        //sharing with the user which lockers are open and which are closed
        for (int k = 0; k < lockers.length; k++) {
            if (lockers[k] != isClosed) {
                System.out.println("Locker " + (k+1) + " is open");
            }
        }

        //closing message
        System.out.println("\n...What a hectic day!");
        System.out.println("Goodbye now.");
    }
}
