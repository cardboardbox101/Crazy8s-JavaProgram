import java.util.*;

public abstract class Player {
    protected ArrayList<Card> hand = new ArrayList<>();
    private String name;
    private int points;

    public Player(String name) {
        // Pre: Takes in a string.
        // Post: Sets the Player name to the given string.
        this.name = name;
    }
    public Player() {
        // Pre: Takes in Nothing
        // Post: Sets the player name to a default John/Jane Doe.
        this.name = "John/Jane Doe";
    }
    public String handToString(){
        // Pre: Takes in nothing.
        // Post: Turns the players hand into a string form.
        String stringHand = "";
        for (Card c: this.hand) {
            stringHand += c.toString()+" ";
        }
        return stringHand;
    }

    @Override
    public String toString() {
        return "Player{" + "hand=" + hand + ", name='" + name + ", points=" + points + '}';
    }

    public void purgeHand() {
        // Pre: Takes in nothing.
        // Post: Clears the Players hand.
        this.hand.clear();
    }
    public void setName(String name) {
        // Pre: Takes in a string.
        // Post: Sets the players name to the string.
        this.name = name;
    }
    public void addPoints(int points) {
        // Pre: Takes in an int of points.
        // Post: Adds the given amount of points to the player's points.
        this.points += points;
    }
    public String getName(){
        // Pre: Takes in nothing.
        // Post: Returns the player's name.
        return this.name;
    }
    public int getPoints() {
        // Pre: Takes in nothing
        // Post: Returns the player's points.
        return points;
    }
    public List<Card> getHand() {
        // Pre: Takes in nothing.
        // Post: Returns the player's hand.
        return hand;
    }
    public abstract void playTurn(TableTop table);
    public void calcPoints(){
        // Pre: Takes in nothing.
        // Post: Adds the together the denominations or points for the left over cards. Puts them into the points.
        if (hand.size()>0) {
            for (Card c : hand) {
                if(c.getDenom() == 8) {
                    points+=50;
                } else if (c.getDenom() >10) {
                    points+=10;
                }else{
                    points+=c.getDenom();
                }
            }
        }
    }
    public void clearPoints(){
        // Pre: Takes in nothing.
        // Post: Sets the Players points to 0.
        points = 0;
    }
}
