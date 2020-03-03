package sliding_puzzle;

public class Position {
    public int row, col;

    public Position() {
        row =0;
        col =0;
    }

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "("+row+","+col+")";
    }
}
