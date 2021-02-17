import java.util.ArrayList;

public class ComputerPlayer extends Player{
    private int intelLevel = 0;
    public ComputerPlayer(String name) {
        super(name);
    }
    public void playTurn(TableTop table){

    }
    public void playTurn(TableTop table, boolean b) {
        // Pre: Takes in the current table being used and a boolean value.
        // Post: If the computer is set to be dumb, it plays a random move, skips or draws another card, until its played.
        //       If the computer is set to play smart, it calls the intelligent move method.
        // When B is set to false, the method prints nothing.
        // Returns: Nothing.
        if (intelLevel == 0) {
            boolean playedTurn = false;
            do{
                if (!randomMove(table, b)){ // If there arent any possible cards to play
                    if(!table.draw(this)){ // if there are no cards that can be drawn
                        playedTurn = true;
                        if (b) System.out.println(getName() + " skipped their turn.");
                        table.addSkip();
                    }else {
                        if (b) System.out.println(getName() + " drew another card.");
                    }
                }else{
                    playedTurn = true;
                }
            }while(!playedTurn);

        }else {
            intelligentMove(table, b);
        }
    }
    public ArrayList<Card> possibleCards(TableTop table){
        // Pre: Takes in the current table being used.
        // Post: Creates a new ArrayList of all the possible cards which the computer could play. Searches the hand for cards that are possible.
        // Returns: The arraylist which has all the playable cards.
        ArrayList<Card >possible = new ArrayList<>();
        for(Card c : hand){
            if(c.matches(table.getTopOfPile())){
                possible.add(c);
            }
        }
        return possible;
    }
    public boolean randomMove(TableTop table, boolean b){
        // Pre: Takes in the current table being used and a boolean value to tell if it should print anything or not.
        // Post: If there are cards that it can play, it chooses the first one in the list, removes it from its hand and draws another card. Once all that is completed it returns true.
        //       If there are no cards that it can play it returns false.
        ArrayList<Card> pos = possibleCards(table);
        if(pos.size()>0){
            Card toPlay = pos.get(0);
            table.setTopOfPile(toPlay, this);
            if (b) System.out.println(getName()+" Played a " + toPlay.toString());
            hand.remove(toPlay);
            table.draw(this);
            return true;
        }else{
            return false;
        }
    }
    public boolean intelligentMove(TableTop table, boolean b){
        // Pre: Takes in the current table being used and a boolean value to tell if it should print anything or not.
        // Post: If there are no cards that can be played, it either draws another card and tried again or skips based on if there are no cards left in the draw pile.
        //       If there are cards that can be played, it takes the possible cards and finds the highest denomination card. Then sets that to the top of the pile, removes it from the hand and draws another card.
        // Returns: True if everything goes to plan
        boolean playedTurn = false;
        do {
            ArrayList<Card> possible = possibleCards(table);
            if (possible.size() > 0) { // If there are cards which are able to play.
                Card highest = possible.get(0);
                for (Card c : possible) {
                    if (c.getDenom() > highest.getDenom()) {
                        highest = c;
                    }
                }
                table.setTopOfPile(highest, this);
                if (b) System.out.println(getName() + " Played a " + highest.toString());
                hand.remove(highest);
                table.draw(this);
                return true;
            } else {
                if (!table.draw(this)){ // If there are no cards that can be drawn.
                    playedTurn = true;
                    if (b) System.out.println(getName()+" skipped their turn.");
                    table.addSkip();
                } else{
                    if (b) System.out.println(getName()+" drew another card.");
                }
            }
        }while(!playedTurn);
        return false;
    }
    public void setIntelLevel(int intelLevel) {
        // Pre: Takes an int.
        // Post: Sets the computer's intelligence level to the int given.
        this.intelLevel = intelLevel;
    }
}
