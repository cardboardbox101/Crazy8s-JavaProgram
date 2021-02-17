public class Card {
    private int denom;
    private String suit;
    

    public Card(int denom, String suit) {
        // Pre: Takes in a denomination and suiit
        // Post: Sets the denomination of this card to the one given, sets the suit of this card to the one given.
        this.denom = denom;
        this.suit = suit;
    }
    public boolean matches(Card c){
        // Pre: Takes in a card.
        // Post: Compares to see if the given card matches the denomination or suit of this card.
        // Returns: true if it matches, false if it doesn't.
        if (c.getDenom() == this.denom || c.getSuit().equals(this.suit)){
            return true;
        }else return false;
    }
    public int getDenom() {
        // Pre: Takes in nothing.
        // Post: Returns the Denomination of the card.
        return denom;
    }
    public String getSuit() {
        // Pre: Takes in nothing.
        // Post: Returns the suit of this card in string form.
        return suit;
    }
    public String toString(){
        // Pre: Takes in nothing
        // Post: Returns a string form of the card.
//      THIS WAS AN ATTEMPT AT SYMBOLS , (IT DIDN'T WORK)
//        switch(suit) {
//            case "c":
//                returned += '\u2663';
//                break;
//            case "d":
//                returned += '\u2666';
//                break;
//            case "h":
//                returned += '\u2764';
//                break;
//            case "s":
//                returned += '\u2660';
//                break;
//        }

        return "" + this.denom+this.suit;
    }
    public void setSuit(String suit) {
        // Pre: Takes in a string.
        // Post: Sets the suit equal to that string.
        this.suit = suit;
    }
}
