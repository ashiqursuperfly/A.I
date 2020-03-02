package sliding_puzzle;


import static sliding_puzzle.Utils.constructBoardFromSequence;

/**
 * Our Node class for the internal graph
 * n-puzzle problem means, total n+1 positions:
 * root(n+1) x root(n+1) board
 **/

public class Board {

    public Tile[][] currentState;
    private int size;
    public int heuristicVal;

    public Board(String[] puzzledListOfValues) {
        size = (int) Math.sqrt(puzzledListOfValues.length + 1);
        this.currentState = constructBoardFromSequence(puzzledListOfValues);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Tile[] tiles : currentState) {
            for (int j = 0; j < currentState.length; j++) {
                if(tiles[j].goalPosition != size*size)sb.append(tiles[j]);
                else sb.append(Consts.BoardConsts.BLANK.value);
                if (j + 1 != currentState.length) sb.append(".....");
            }
            sb.append('\n');
        }

        return sb.toString();
    }


}
