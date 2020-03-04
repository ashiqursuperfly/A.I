package sliding_puzzle.puzzle;

public class Tile { // parameter T might mean anything starting from Strings to Links/paths to images of actual puzzles
    String value;
    int goalPosition;

    public Tile(int goalPosition, String value) {
        this.value = value;
        this.goalPosition = goalPosition;
    }

    @Override
    public String toString() {
        return value + "(" + goalPosition + ")" ;
    }
}
