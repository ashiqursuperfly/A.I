package sliding_puzzle;

public class Tile {
    int goalPosition;

    public Tile(int goalPosition) {
        this.goalPosition = goalPosition;
    }

    @Override
    public String toString() {
        return goalPosition +"";
    }
}
