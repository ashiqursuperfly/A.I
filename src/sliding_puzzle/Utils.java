package sliding_puzzle;

import kotlin.Pair;

import java.util.Arrays;

public class Utils {

    public static Node clone(Node copyFrom) {

        var copied = new Node();
        copied.currentState = new Tile[copyFrom.size][copyFrom.size];
        copied.currentState = Arrays.stream(copyFrom.currentState).map(Tile[]::clone).toArray(Tile[][]::new);

        copied.size = copyFrom.size;
        copied.blankPos = copyFrom.blankPos;
        copied.heuristicVal = copyFrom.heuristicVal;
        copied.height = copyFrom.height;

        return copied;
    }

    public static boolean isClone(Node lhs, Node rhs) {
        if (lhs == rhs) return true;
        if (rhs != null && lhs != null) {

            if (lhs.heuristicVal != rhs.heuristicVal || lhs.height != rhs.height || lhs.size != rhs.size || !lhs.blankPos.equals(rhs.blankPos))
                return false;

            for (int i = 0; i < lhs.size; i++) {
                for (int j = 0; j < lhs.size; j++) {
                    if (!rhs.currentState[i][j].equals(lhs.currentState[i][j])) return false;
                }
            }
            return true;
        } else return false;


    }

    public static boolean isBoardDimensionsValid(int n, Tile[][] board) {
        var size = Math.sqrt(n + 1);
        int x = board[0].length;

        if (!("" + size).split("\\.")[1].equals("0") || x != (int) size) {
            try {
                throw new Exception("Invalid Value Provided for n " + n);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    public static Pair<Tile[][], Position> constructBoardFromSequence(String[] puzzledListOfValues) {
        var size = (int) Math.sqrt(puzzledListOfValues.length + 1);

        var initialState = new Tile[size][size];
        var blankPos = new Position();

        if (!isBoardDimensionsValid(puzzledListOfValues.length - 1, initialState)) return null;

        int row = 0;
        for (int k = 0; k < puzzledListOfValues.length; k++) {
            var goal = 0;
            if (puzzledListOfValues[k].equals(Consts.BLANK.value)) {
                goal = puzzledListOfValues.length;
                blankPos.row = row;
                blankPos.col = k - row * size;

            } else goal = Integer.parseInt(puzzledListOfValues[k]);

            initialState[row][k - row * size] = new Tile(goal);

            if ((k + 1) % size == 0) row++;
        }

        return new Pair<>(initialState, blankPos);
    }

    public static int findBasicHeuristicsValue(Node currentNode) {

        int size = currentNode.currentState[0].length, heuristicsVal = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                var valueCurrent = i * size + j + 1;
                if (valueCurrent != currentNode.currentState[i][j].goalPosition) heuristicsVal++;
            }
        }
        return heuristicsVal;
    }

    public static int findManhattanHeuristicsValue(Node currentNode) {
        int size = currentNode.currentState[0].length, heuristicsVal = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                var goalI = (currentNode.currentState[i][j].goalPosition / size);
                if (currentNode.currentState[i][j].goalPosition % size == 0) goalI--;
                var goalJ = (currentNode.currentState[i][j].goalPosition - 1) % size;

                // System.out.println(currentNode.currentState[i][j].goalPosition + " " + (goalI) + " " + goalJ);
                heuristicsVal += (Math.abs(goalI - i) + Math.abs(goalJ - j));
            }
        }
        return heuristicsVal;
    }

}

