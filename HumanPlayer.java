import java.util.Locale;
import java.util.Scanner;

public class HumanPlayer extends Player{
    static Scanner sc = new Scanner(System.in);
    public HumanPlayer() {
        // Pre: Takes in Nothing.
        // Post: Calls the SuperClass constructor.
        super();
    }
    public HumanPlayer(String name) {
        // Pre: Takes in a string.
        // Post: Calls the SuperClass constructor with a name.
        super(name);
    }

    @Override
    public void playTurn(TableTop table) {
        // Pre: Takes in the currently used table.
        // Post: Has the user choose a card, if they said to draw or skip, it will do that, otherwise it will try and set it on top of the pile.
        System.out.println();
        System.out.println("It's "+this.getName() + "'s Turn.");
        boolean finished = false;
        do {
            Card chosen = chooseACard(table);
            if (chosen.getDenom()==-1){ // If the card turned back is designated as a user input
                if (chosen.getSuit().equals("d")){ // if the input was to draw
                    table.draw(this);
                } else{ // if it was a user input but not to draw, its a skip and so the loop can close.
                    finished = true;
                }
            }
           else if(chosen.getDenom() ==8){ // if the card they chose is an 8.
                playAn8(chosen, table);
                finished=true;
           }
            else if (table.setTopOfPile(chosen, this)) finished = true; // If it does match and is correctly set to the top of the pile, then the loop will exit.
            else{ // If nothing applies or can be matched, that means that the card most likely doesn't match and the user must try again.
                System.out.println(chosen +" doesn't match " + table.getTopOfPile());
            }
        }while(!finished);
        table.draw(this); // Draws a card to refill their hand at the end of their turn.
    }
    public Card chooseACard(TableTop table){
        // Pre: Takes in the currently used table.
        // Post:
        Card chosen;
        do {
            System.out.println("Top of the Pile:" + table.getTopOfPile().toString());
            System.out.println("Your current hand:" + handToString());
            String inputS = sc.next();
            if (inputS.equalsIgnoreCase("draw")){
                return new Card(-1,"d");
            }
            else if (inputS.equalsIgnoreCase("skip")){
                table.addSkip();
                return new Card(-1,"s");
            }
            try {
                int inputD = Integer.parseInt(inputS.substring(0, inputS.length() - 1));
                chosen = findAndRemoveCard(inputD, inputS.substring(inputS.length() - 1));
            } catch (Exception NumberFormatException){
                chosen = null;
            }
            if (chosen ==null) System.out.println("ERROR: That card is not in your hand. Try again");
        }while (chosen==null);
        return chosen;
    }
    public Card findAndRemoveCard(int inputD, String inputS){
        // Pre: Takes in an integer of the cards denomination and a string of the cards suit.
        // Post: Goes through the entire players hand and if it finds the card that matches both the suit and denomination it returns it.
        // If there is no card that fully matches, it returns null.
        for (Card c: this.getHand()) {
            if (c.getDenom() == inputD && c.getSuit().equalsIgnoreCase(inputS)){
                return c;
            }
        }
        return null;
    }
    private void playAn8(Card c, TableTop t){
        // Pre: Takes in the selected card and the currently used table.
        // Post: Prompts the user for what suit they would like the 8 to change to.
        // Changes the 8 to that new suit and sets it at the top of the pile.
        // Returns: Nothing.
        boolean finished = false;
        do {
            System.out.println("What suit would you like it to be?");
            String chosenSuit = sc.next().toLowerCase(Locale.ROOT);
            switch(chosenSuit){
                case "c":
                    c.setSuit("C");
                    finished = true;
                    break;
                case "d":
                    c.setSuit("D");
                    finished = true;
                    break;
                case "h":
                    c.setSuit("H");
                    finished = true;
                    break;
                case "s":
                    c.setSuit("S");
                    finished = true;
                    break;
                default:
                    System.out.println(chosenSuit + ": suit not found");
                    break;
            }
        }while (!finished);
        t.setTopOfPileV8(c,this);
    }
}
