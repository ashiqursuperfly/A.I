package sliding_puzzle.puzzle;


import sliding_puzzle.data.Consts;
import sliding_puzzle.data.Position;
import sliding_puzzle.data.SharedConfig;

import java.util.ArrayList;
import java.util.List;

import static sliding_puzzle.puzzle.Utils.*;

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
    public Consts.Moves latestMove = null;

    public Node(){ }

    public Node(String[] puzzledListOfValues, String[] goalListOfValues, boolean shouldCheckForSolvable) {
        size = (int) Math.sqrt(puzzledListOfValues.length + 1);
        var b = constructBoardFromSequence(puzzledListOfValues, goalListOfValues, shouldCheckForSolvable);



        if (b != null) {
            currentState = b.getFirst();
            blankPos = b.getSecond();
        } else {
            try {
                throw new Exception("Cannot Create board from given invalid input.");
            } catch (Exception e) {
                e.printStackTrace();
            }
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

        latestMove = move;

        heuristicVal = (SharedConfig.SELECTED_HEURISTICS == Consts.Heuristics.MANHATTAN) ?
                findManhattanHeuristicsValue(this) : findBasicHeuristicsValue(this);

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("H:").append(heuristicVal).append("\n").append("G:").append(height).append('\n');
        for (Tile[] tiles : currentState) {
            for (int j = 0; j < currentState.length; j++) {
                sb.append("_".repeat(Math.max(0, (size - tiles[j].toString().length()))));
                sb.append(tiles[j]);
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

    public void setParent(Node parent, Consts.Moves childPos) {
        this.parent = parent;
        this.height = parent.height + 1;

        switch (childPos){
            case UP:
                parent.upChild = this;
                break;
            case DOWN:
                parent.downChild = this;
                break;
            case LEFT:
                parent.leftChild = this;
                break;
            case RIGHT:
                parent.rightChild = this;
                break;
        }
    }



}
