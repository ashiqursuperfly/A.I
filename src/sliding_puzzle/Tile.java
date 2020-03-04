package sliding_puzzle;

public class Tile {
    int goalPosition;

    //TODO: must have a 'value' field as abstraction for handling different goal states
    // Need to parse input state and goal state together
    public Tile(int goalPosition) {
        this.goalPosition = goalPosition;
    }

    @Override
    public String toString() {
        return goalPosition +"";
    }


}
