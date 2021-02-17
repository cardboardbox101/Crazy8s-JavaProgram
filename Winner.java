public class Winner {
    private int endPoints;
    private String name;

    public Winner(int endPoints, String name) {
        // Pre: Takes in the points they had in the end and the name of the person.
        // Post: Sets the points to the points given and name to name given.
        this.endPoints = endPoints;
        this.name = name;
    }

    @Override
    public String toString() {
        // Pre: Takes in nothing
        // Post: Returns a String form of the End points and the name.
        return " End Points: " + endPoints + " | Name: " + name;
    }

    public String getName() {
        // Pre: Takes in nothing.
        // Post: Returns the winner's name.
        return name;
    }

    public int getEndPoints() {
        // Pre: Takes in Nothing.
        // Post: Returns the winner's points.
        return endPoints;
    }
}
