package sliding_puzzle.puzzle;

import java.util.Objects;

public class Tile { // parameter T might mean anything starting from Strings to Links/paths to images of actual puzzles
    String value;
    int goalPosition;

    public Tile(int goalPosition, String value) {
        this.value = value;
        this.goalPosition = goalPosition;
    }

    @Override
    public String toString() {
        return value; // + "(" + goalPosition + ")" ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;
        Tile tile = (Tile) o;
        return goalPosition == tile.goalPosition &&
                value.equals(tile.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, goalPosition);
    }
}
