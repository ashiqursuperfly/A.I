package sliding_puzzle.data;

public class Position {
    public int row, col;

    public Position() {
        row = 0;
        col = 0;
    }

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "("+row+","+col+")";
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        else if(o instanceof Position){
            var rhs = (Position)o;
            return rhs.row == row && rhs.col == col;
        }
        else return false;
    }

}
