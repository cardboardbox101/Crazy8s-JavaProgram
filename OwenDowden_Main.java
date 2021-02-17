import java.util.*;
public class OwenDowden_Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        // Pre: Takes in Nothing.
        // Post: Initializes a new table and sets it up. Then runs the menu option, until the user quits.
        boolean quit;
        TableTop table= new TableTop();
        setup(table);
        do {
            quit = menu(table);
        }while(!quit);
    }
    public static boolean menu(TableTop table){
        // Pre: Takes in the current table.
        // Post: Prints out the menu and calls the methods based on the option the user selects.
        // Returns: True if the user wants to quit, false if the user wants to keep going.
        boolean quit = false;
        System.out.println("MAIN MENU \n"
                            +"P)lay game\n"
                            +"C)hange number of points to end the game\n"
                            +"R)ename the computers\n"
                            +"T) Change Computer Intelligence\n"
                            +"N) Change the Number of Players\n"
                            +"S)imulate Games\n"
                            +"I)nstructions\n"
                            +"Q)uit\n");
        String input = sc.next().toLowerCase();
        switch(input){
            case "p":
                playGame(table);
                break;
            case "c":
                table.setPointsToWin();
                break;
            case "r":
                table.renameComputers();
                break;
            case "t":
                table.setIntels();
                break;
            case "n":
                table.changePlayers();
                break;
            case "s":
                Simulator s;
                s = new Simulator();
                s.simulate();
                break;
            case "i":
                instructions();
                break;
            case "q":
                quit = true;
                break;
            default:
                System.out.println(input + ": command not found");
                break;
        }
        return quit;
    }
    public static void setup(TableTop table){
        // Pre: Takes in the currently used table.
        // Post: Asks the user for their name and creates the base human player from that.
        // Asks the user if they would like to change the name of the default computers.
        // Returns: Nothing.
        System.out.println("Welcome to Owen's Crazy 8's Program!! Lets get Started.");
        System.out.println("Hello, what is your name?");
        String name = sc.nextLine();
        HumanPlayer rootPlayer = new HumanPlayer(name);
        table.getHumanPlayers().add(rootPlayer);
        table.setComputers();
        System.out.println("Default Computer Names: PC, MAC and AI\n"+
                "Would you like to rename the computers? [Y/N]");
        String response = sc.next().toLowerCase();
        if (response.equals("y") || response.equals("yes")){
            table.renameComputers();
        }
    }
    public static void instructions(){
        System.out.println("INSTRUCTIONS: \n" +
                "Here's How you Play the Game:\n" +
                "Look at the top of the deck and then play any card matching in suit or in denomination.\n" +
                "If you don't have a card to play draw another card. If there are no cards left, skip.\n" +
                "Playing an 8 will allow you to switch the suit that is currently on top of the deck\n" +
                "\n" +
                "At the end of each round, when someone has no cards in their hand, points will be added based on the cards you have left in your hand.\n" +
                "An 8 gets 50 points, face cards get 10 and the rest get the same as their denomination.\n" +
                "\n" +
                "Once someone has gotten 100 points, the person with the least amount of points wins and the game is over.\n" +
                "\n" +
                "Controls:\n" +
                "In general just type the name of the card you would like to play.\n" +
                "If you need to draw another card, just type 'Draw'.\n" +
                "If you need to skip your turn, just type 'Skip'.\n" +
                "" +
                "Have Fun Playing Owen's Crazy 8's! \n" +
                ""  );
    }
    public static void playGame(TableTop table){
        // Pre: Takes in the currently used table.
        // Post: Plays a round until there is a winner. Prints out the winner's information and clears everyone's points from the finished game.
        // Returns: Nothing.
        System.out.println("Lets start playing a game!");
        Winner victor;
        do {
            victor = null;
            if(!playRound(table)) {
                victor = table.checkForWinner();
            }
        }while (victor == null);
        System.out.println("GAME WINNER:" + victor);
        for (ComputerPlayer hp : table.getAiPlayers()){
            hp.clearPoints();
        }
        for (HumanPlayer hp : table.getHumanPlayers()){
            hp.clearPoints();
        }
    }
    public static boolean playRound(TableTop table){
        // Pre: Takes in the currently used table.
        // Post: Prints new round. Resets the deck. While no one has an empty hand it goes through every player and has them play a turn.
        // If the number of skips equals the number of players then it then it returns true.
        // If the round plays out normally, it counts the points of everyone, passing through the person with an empty hand it returns false.
        System.out.println("NEW ROUND:");
        table.setupDeck();
        while (table.emptyHand() == null) {
            for (HumanPlayer c : table.getHumanPlayers()) {
                c.playTurn(table);
            }
            for (ComputerPlayer c : table.getAiPlayers()) {
                c.playTurn(table, true);
            }
            if (table.getSkips()>= table.getHumanPlayers().size()+table.getAiPlayers().size()){
                table.clearSkips();
                return true;
            }else{
                table.clearSkips();
            }
        }
        table.countPoints(table.emptyHand(), true);
        /*Calculation for the lowest amount of points to show winner*/
        table.clearDecks();
        return false;
    }
}
