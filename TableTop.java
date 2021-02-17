import java.util.*;

public class TableTop {
    static Scanner sc = new Scanner(System.in);
    private ArrayList<Card> mainDeck= new ArrayList<>();
    private Card topOfPile;
    private int pointsToWin;
    private int computerLevel;
    private ArrayList<ComputerPlayer> aiPlayers = new ArrayList<>();
    private ArrayList<HumanPlayer> humanPlayers = new ArrayList<>();
    private int skips=0;

    public TableTop() {
        // Pre: Takes in Nothing.
        // Post: Constructs a table object with default values.
        this.pointsToWin = 100;
        this.computerLevel = 0;
    }

    public void renameComputers(){
        // Pre: Takes in Nothing.
        // Post: Goes through each computer object and asks if they should rename it/does change it.
        for (ComputerPlayer comp: aiPlayers) {
            System.out.println(comp.getName());
            System.out.println("Would you like to change this computers name? [Enter name or N for no]");
            String input = sc.nextLine();
            if(!input.equalsIgnoreCase("n")){
                comp.setName(input);
            }
        }
    }
    public boolean setTopOfPile(Card set, Player p){
        // Pre: Takes in a card and the player who currently holds the card.
        // Post: 1) Tests if the card could be played with the current pile.
        //       2) Sets the top of pile to the selected card.
        //       3) Removes the card from the players hand.
        //       4) Returns true if all of that was completed, false if it didn't match.
        if (set.matches(topOfPile)){
            this.topOfPile = set;
            p.hand.remove(set);
            return true;
        }
        return false;
    }
    public void setTopOfPileV8(Card set, Player p){
        // Pre: Takes in a card and the player who currently holds it.
        // Post: Skips the checks and puts the card straight on the pile and removes it from the players hand.
        //       (This is used rather than the other because if you try to match an 8, it won't always work)
            this.topOfPile = set;
            p.hand.remove(set);
    }
    public void setupDeck(){
        // Pre: Takes in nothing
        // Post: Creates 4 suits, adds them to the deck, shuffles the cards, distributes them to everyone.
        Random rand = new Random();
        int index = 0;
        createSuit("D");
        createSuit("H");
        createSuit("C");
        createSuit("S");
        for (Card c : mainDeck){
            int nextPos = rand.nextInt(mainDeck.size());
            Card inPos = mainDeck.get(nextPos);
            mainDeck.set(nextPos,c);
            mainDeck.set(index,inPos);
            index++;
        }
        topOfPile = mainDeck.get(0);
        mainDeck.remove(0);
        int cardsForEach = 5;
        for (HumanPlayer p : humanPlayers){
            for (int i =0; i<cardsForEach; i++) {
                draw(p);
            }
        }
        for (ComputerPlayer p : aiPlayers){
            for (int i =0; i<cardsForEach; i++) {
                draw(p);
            }
//            System.out.print(p.getName()+" cards: ");
//            for (Card c : p.getHand()){
//                System.out.print(c + " ");
//
//            }
//            System.out.println();
        }
    }
    public void setComputers(){
        // Pre: Nothing
        // Post: Creates the 3 Default computers.
        createComputer("Mac");
        createComputer("PC");
        createComputer("AI");
    }
    public void setPointsToWin(){
        // Pre: Nothing
        // Post: Asks the user what the new point value should be, sets the points to that value.
        int points = 100;
        boolean finished = false;
        System.out.println("How many points should someone need to end?");
        while (!finished) {
             points = sc.nextInt();
            if (points<=0){
                System.out.println("It cannot be equal to or less than 0 points.");
            }else {
                System.out.println("Points Set To: " + points);
                finished = true;
            }
        }
        this.pointsToWin = points;
    }
    public void setIntels(){
        // Pre: Nothing
        // Post: Goes through each computer and prompts the user if they would like to change a computer's intelligence.
        boolean finished;
        int points = 0;
        for(ComputerPlayer cp: aiPlayers){
            finished = false;
            while (!finished) {
                System.out.println("How should " + cp.getName() + " play? (0 - Dumb, 1 - Smart)");
                points = sc.nextInt();
                if (points == 0 || points ==1){
                    System.out.println(cp.getName() + "'s Intelligence Set To: " + points);
                    finished = true;
                }else {
                    System.out.println("Intelligence level must be 0 or 1.");
                }
            }
            cp.setIntelLevel(points);
        }
    }

    public ArrayList<ComputerPlayer> getAiPlayers() {
        // Pre: Takes in nothing.
        // Post: Returns the ArrayList of computer players.
        return aiPlayers;
    }
    public ArrayList<HumanPlayer> getHumanPlayers() {
        // Pre: Takes in Nothing
        // Post: Returns the ArrayList of Human Players.
        return humanPlayers;
    }
    public Card getTopOfPile() {
        // Pre: Takes in Nothing.
        // Post: Returns the card at the top of the pile.
        return topOfPile;
    }

    public void createSuit(String s){
        // Pre: Takes in a string.
        // Post: Creates a card for each value in a normal suit and adds it to the main deck.
        for(int i=0;i<13;i++){
            Card c = new Card(i+1,s);
            mainDeck.add(c);
        }
    }
    public void createComputer(String name){
        // Pre: Takes in a string for the name of a computer.
        // Post: Initializes the computer using the given name and adds it to the computer list.
        ComputerPlayer c = new ComputerPlayer(name);
        aiPlayers.add(c);
    }

    public Winner checkForWinner(){
        // Pre: Takes in nothing.
        // Post: Checks if there is someone with enough points to trigger the end of a game.
        // If there is then it finds the player with the least amount of points. Returns: A winner object that holds all the information.
        // If there isnt, it returns a null value.
        if (oneAbove100()){
            Player lowest = new HumanPlayer();
            lowest.addPoints(2000000000);
            for (ComputerPlayer c: aiPlayers) {
                if(c.getPoints()<lowest.getPoints()){
                    lowest = c;
                }
            }
            for (HumanPlayer c: humanPlayers) {
                if(c.getPoints()<lowest.getPoints()){
                    lowest = c;
                }
            }
            return new Winner(lowest.getPoints(), lowest.getName());
        }else {
            return null;
        }
    }
    public void clearDecks(){
        // Pre: Takes in nothing.
        // Post: Goes through every player's hand and purges it.
        for (ComputerPlayer c: aiPlayers) {
            c.purgeHand();
        }
        for (HumanPlayer c: humanPlayers) {
            c.purgeHand();
        }
    }
    public boolean draw(Player p){
        // Pre: Takes in the player.
        // Post: If there are still cards in the deck, it removes one and puts it in the hand of the given player.
        //       If there are no cards in the deck, it sees if the person drawing is a person, if it is it prints that there aren't anything to draw.
        // Returns: True if it drew a card. False if there are no cards to draw.
        if (mainDeck.size()>0){
            Card toReturn = mainDeck.get(0);
            mainDeck.remove(0);
            p.hand.add(toReturn);
            return true;
        } else{
            if (humanPlayers.size() > 0 && p.getClass() == humanPlayers.get(0).getClass()){
                System.out.println("(No cards in the drawpile left.)");
            }
            return false;
        }

    }
    public Player emptyHand(){
        // Pre: Takes in nothing
        // Post: Checks to see if there is someone with an empty hand. If there is it returns that player.
        for(HumanPlayer p : humanPlayers){
            if(p.getHand().size() ==0){
                return p;
            }
        }
        for(ComputerPlayer p : aiPlayers){
            if(p.getHand().size() ==0){
                return p;
            }
        }
        return null;
    }
    public void countPoints(Player p, boolean b){
        // Pre: Takes in the player who had nothing in their hand and a boolean if the method should print anything out.
        // Post: Calculates the points of every player, (in some cases) prints out everyone's point values.
        if (p != null) {
            for (HumanPlayer hp : humanPlayers) {
                hp.calcPoints();
            }
            for (ComputerPlayer cp : aiPlayers) {
                cp.calcPoints();
            }
            if (b) {
                System.out.print("-");
                for (int i = 0; i < p.getName().length(); i++) {
                    System.out.print("--");
                }
                System.out.println("------------------------------------");
                System.out.println(p.getName() + " had the empty hand!");
                for (HumanPlayer hp : humanPlayers) {
                    System.out.println("| " + hp.getName() + " has " + hp.getPoints() + " |");
                }
                for (ComputerPlayer cp : aiPlayers) {
                    System.out.println("| " + cp.getName() + " has " + cp.getPoints() + " |");
                }
                System.out.print("-");
                for (int i = 0; i < p.getName().length(); i++) {
                    System.out.print("-");
                }
                System.out.println("------------------------------------");
            }
        }else{
            System.out.println("ROUND OVER WITH A TIE");
        }
    }
    public void changePlayers(){
        // Pre: Takes in nothing.
        // Post: Goes to each type of player and creates ones if there should be more it creates them with the inputted name.
        // Returns: Nothing.
        System.out.println("How many Ai Players should be in the game? (Default: 3)");
        int num = sc.nextInt();
        if(num>=aiPlayers.size()) {
        	int startingSize = aiPlayers.size();
            for (int i = 0; i < num - startingSize; i++) { // 6-3 -> 3 -> 0,1,2
                System.out.println("What should this new computer be named? ");
                createComputer(sc.next());
            }
        }else{
            int numOfCurrent = aiPlayers.size();
            for (int i = 0; i < numOfCurrent-num; i++) {
                aiPlayers.remove(aiPlayers.size()-1);
            }
        }
        System.out.println("How many Human Players should be in the game? (Default: 1)");
        num = sc.nextInt();
        if(num>=humanPlayers.size()) {
        	int startingSize = humanPlayers.size();
            for (int i = 0; i < num - startingSize; i++) {
                System.out.println("What should this new player be named?");
                humanPlayers.add(new HumanPlayer(sc.next()));
            }
        }else{
            int numOfCurrent = humanPlayers.size();
            for (int i = 0; i < numOfCurrent-num; i++) {
                humanPlayers.remove(humanPlayers.size()-1);
            }
        }
    }
    private boolean oneAbove100(){
        // Pre: Takes in nothing.
        // Post: Goes through every player and if one of them has over the amount of points needed to end the game, it returns true.
        // It returns false if no one has over or equal to the points needed to win.
        for (ComputerPlayer c: aiPlayers) {
            if (c.getPoints() >= pointsToWin){
                return true;
            }
        }
        for (HumanPlayer c: humanPlayers) {
            if (c.getPoints() >= pointsToWin) {
                return true;
            }
        }
        return false;
    }

    public void addSkip(){
        // Pre: Takes in nothing.
        // Post: Adds one skip to the counter.
        skips++;
    }
    public int getSkips(){
        // Pre: Takes in nothing.
        // Post: Returns the current number of skips.
        return this.skips;
    }
    public void clearSkips(){
        // Pre: Takes in nothing.
        // Post: Sets the skip counter back to 0.
        skips=0;
    }
}
