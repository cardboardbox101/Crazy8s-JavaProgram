import java.util.ArrayList;
import java.util.Scanner;

public class Simulator {
    static Scanner sc = new Scanner(System.in);
    private int numOfGames;
    private ArrayList<Integer> scores = new ArrayList<>(4);
    private TableTop table = new TableTop();

    public Simulator() {
        // Pre: Takes in Nothing.
        // Post: Sets up the table for the simulator. Asks how many games the user would like to simulate and how each of the 4 computers should play.
        setup(table);
        System.out.println("How Many Games would you like to simulate?");
        this.numOfGames = sc.nextInt();
        for(ComputerPlayer cp: table.getAiPlayers()){
            System.out.println("How should " + cp.getName() + " play? (0 - Dumb, 1 - Smart)");
            cp.setIntelLevel(sc.nextInt());
        }
    }

    public void simulate() {
        // Pre: Takes in Nothing.
        // Post: Sets the base scores, Plays the given amount of games. Then prints out the number of games won by each and the percentage of the overall games.
        // Returns: Nothing.
        for(int i=0;i<4;i++) {
            scores.add(0);
        }
        System.out.print("Progress [");
        int lastDot =0;
        for(int i=0; i<numOfGames; i++){
            if (i>= (numOfGames/10)+lastDot){ // This makes a progress bar where every 10%, it adds a star.
                System.out.print("*");
                lastDot+=numOfGames/10;
            }
            playGame();
        }
        System.out.println("]");
        System.out.println("IN TOTAL:");
        for (int i=0; i< scores.size();i++){
            String finalscore = String.format("%.5g", ((double)scores.get(i)/numOfGames)*100);
            System.out.println(i+1 + " won: " + scores.get(i) + " - " + finalscore + "% of the time.");
        }
    }
    public static void setup(TableTop table){
        // Pre: Takes in Nothing.
        // Post: Creates 4 computers for the game.
        // Returns: Nothing
        table.createComputer("1");
        table.createComputer("2");
        table.createComputer("3");
        table.createComputer("4");
    }
    public void playGame(){
        // Pre: Takes in Nothing.
        // Post: While there is not a winner: plays a round and cchecks for a winner.
        //       Once a winner is found, it adds a game to their place on the win score sheet and clears everyones points.
        // Returns: Nothing.
        Winner victor = null;
        do {
            if(!playRound()) {
                victor = table.checkForWinner();
            }
        }while (victor == null);
        int inputD = Integer.parseInt(victor.getName())-1;
        scores.set(inputD,scores.get(inputD)+1);
        for (ComputerPlayer hp : table.getAiPlayers()){
            hp.clearPoints();
        }
    }
    public boolean playRound(){
        // Pre: Takes in Nothing.
        // Post: Sets up the deck, While no one has an empty hand: Has every computer play. If the skips are greater than the number of people, it exits out.
                 // After someone has an empty hand, it counts the points and clears the deck, then since it was a normal finish: returns false.
        // Returns: False if it ended normally, True if it was aborted since everyone skipped.
        table.setupDeck();
        while (table.emptyHand() == null) {
            for (ComputerPlayer c : table.getAiPlayers()) {
                c.playTurn(table, false);
            }
            if (table.getSkips()>= table.getHumanPlayers().size()+table.getAiPlayers().size()){
                table.clearSkips();
                return true;
            }else{
                table.clearSkips();
            }
        }
        table.countPoints(table.emptyHand(), false);
        /*Calculation for the lowest amount of points to show winner*/
        table.clearDecks();
        return false;
    }
}
