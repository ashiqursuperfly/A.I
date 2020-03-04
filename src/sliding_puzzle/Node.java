package sliding_puzzle;


import java.util.ArrayList;
import java.util.List;

import static sliding_puzzle.Utils.*;

/**
 * Our Node class for the internal graph
 * n-puzzle problem means, total n+1 positions:
 * root(n+1) x root(n+1) board
 **/

public class Node implements Comparable<Node> {

    public Position blankPos;
    public Tile[][] currentState;
    public int heuristicVal, height;
    public int size;

    public Node leftChild,rightChild,upChild,downChild; //explanation of the weird names. downChild-> board after a applyMove(Moves.Down) and so on.
    private Node parent;

    public Node(){ }

    public Node(String[] puzzledListOfValues) {
        size = (int) Math.sqrt(puzzledListOfValues.length + 1);
        var b = constructBoardFromSequence(puzzledListOfValues);
        if (b != null) {
            currentState = b.getFirst();
            blankPos = b.getSecond();
        }

        heuristicVal = (SharedConfig.SELECTED_HEURISTICS == Consts.Heuristics.MANHATTAN) ?
                findManhattanHeuristicsValue(this) : findBasicHeuristicsValue(this);

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

        var blank = currentState[row][col];
        var tile = currentState[swapRow][swapCol];

        currentState[row][col] = tile;
        currentState[swapRow][swapCol] = blank;

        blankPos.row = swapRow;
        blankPos.col = swapCol;

        heuristicVal = (SharedConfig.SELECTED_HEURISTICS == Consts.Heuristics.MANHATTAN) ?
                findManhattanHeuristicsValue(this) : findBasicHeuristicsValue(this);

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Tile[] tiles : currentState) {
            for (int j = 0; j < currentState.length; j++) {

                for (int i = 0; i < (size - tiles[j].toString().length()); i++) {
                    sb.append(".");
                }

                //TODO: find better way to check for `blank` once goal state is being used
                if (tiles[j].goalPosition == size * size & size > 3) sb.append(".");

                if (tiles[j].goalPosition != size * size) sb.append(tiles[j]);
                else sb.append(Consts.BLANK.value);
                //if (j + 1 != currentState.length) sb.append(".");

                //sb.append(".");
            }
            sb.append('\n');
        }

        sb.append("BlankPosition:").append(blankPos.toString());
        return sb.toString();
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(heuristicVal + height, o.heuristicVal + o.height);
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
        this.height = parent.height + 1;
    }

}
