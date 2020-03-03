package sliding_puzzle;


import static sliding_puzzle.Utils.constructBoardFromSequence;

/**
 * Our Node class for the internal graph
 * n-puzzle problem means, total n+1 positions:
 * root(n+1) x root(n+1) board
 **/

public class Board implements Comparable<Board> {

    public Position blankPos;
    public Tile[][] currentState;
    public int heuristicVal, height;
    private int size;

    public Board(String[] puzzledListOfValues) {
        size = (int) Math.sqrt(puzzledListOfValues.length + 1);
        var b = constructBoardFromSequence(puzzledListOfValues);

        if (b != null) {
            currentState = b.getFirst();
            blankPos = b.getSecond();
        }
    }

    public boolean applyMove(Consts.Moves move) {

        var row = blankPos.row;
        var col = blankPos.col;

        if (row >= size || row < 0) return false;
        else if (col >= size || col < 0) return false;

        int swapRow = row, swapCol = col;

        switch (move) {
            case LEFT:
                if (col == 0) return false;
                swapCol--;
                break;
            case RIGHT:
                if (col == size - 1) return false;
                swapCol++;
                break;
            case UP:
                if (row == 0) return false;
                swapRow--;
                break;
            case DOWN:
                if (row == size - 1) return false;
                swapRow++;
                break;
        }

        //TODO:
        //1. swap
        //2. update blank pointer
        var blank = currentState[row][col];
        var tile = currentState[swapRow][swapCol];

        currentState[row][col] = tile;
        currentState[swapRow][swapCol] = blank;

        blankPos.row = swapRow;
        blankPos.col = swapCol;

        return true;


    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Tile[] tiles : currentState) {
            for (int j = 0; j < currentState.length; j++) {

                for (int i = 0; i < tiles[j].toString().length() ; i++) {
                    sb.append(".");
                }

                if (tiles[j].goalPosition != size * size) sb.append(tiles[j]);
                else sb.append(Consts.BLANK.value);
                if (j + 1 != currentState.length) sb.append(".");
            }
            sb.append('\n');
        }

        sb.append("BlankPosition:").append(blankPos.toString());
        return sb.toString();
    }

    @Override
    public int compareTo(Board o) {
        return Integer.compare(heuristicVal + height, o.heuristicVal + o.height);
    }
}
