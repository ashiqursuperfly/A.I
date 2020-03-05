package sliding_puzzle.puzzle;

import kotlin.Pair;
import sliding_puzzle.data.Consts;
import sliding_puzzle.data.Position;

import java.util.ArrayList;
import java.util.Arrays;

public class Utils {

    public static Node clone(Node copyFrom) {

        var copied = new Node();
        copied.currentState = new Tile[copyFrom.size][copyFrom.size];

        int length = copyFrom.currentState.length;
        Tile[][] target = new Tile[length][copyFrom.currentState[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(copyFrom.currentState[i], 0, target[i], 0, copyFrom.currentState[i].length);
        }
        copied.currentState = target;

        //copied.currentState = Arrays.stream(copyFrom.currentState).map(Tile[]::clone).toArray(Tile[][]::new);

        copied.size = copyFrom.size;
        copied.blankPos = new Position(copyFrom.blankPos.row,copyFrom.blankPos.col);
        copied.heuristicVal = copyFrom.heuristicVal;
        copied.height = copyFrom.height;

        return copied;
    }

    public static boolean isClone(Node lhs, Node rhs) {
        if (lhs == rhs) return true;
        if (rhs != null && lhs != null) {

            if (lhs.heuristicVal != rhs.heuristicVal || lhs.height != rhs.height || lhs.size != rhs.size || !lhs.blankPos.equals(rhs.blankPos))
                return false;

            return isBoardPositionsClone(lhs, rhs);
        } else return false;
    }

    public static boolean isBoardPositionsClone(Node lhs, Node rhs) {

        for (int i = 0; i < lhs.size; i++) {
            for (int j = 0; j < lhs.size; j++) {
                if (!rhs.currentState[i][j].equals(lhs.currentState[i][j])) return false;
            }
        }
        return true;
    }

    public static boolean isBoardDimensionsValid(int n, Tile[][] board) {
        var size = Math.sqrt(n + 1);
        int x = board[0].length;

        if (!("" + size).split("\\.")[1].equals("0") || x != (int) size) {
            try {
                throw new Exception("Invalid Value Provided for n " + n);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static Pair<Tile[][], Position> constructBoardFromSequence(String[] puzzled, String[] goal) {
        var size = (int) Math.sqrt(puzzled.length + 1);
        var initialState = new Tile[size][size];
        var blankPos = new Position();

        if (!isBoardDimensionsValid(puzzled.length - 1, initialState)) return null;


        var goalPositions = Arrays.asList(goal);

        int row = 0;
        for (int k = 0; k < puzzled.length; k++) {
            var value = puzzled[k];
            var goalPosition = goalPositions.indexOf(puzzled[k]) + 1; // since, we are using 1 indexed positions here

            if(goalPosition == -1)return null;

            if (puzzled[k].equals(Consts.BLANK.value)) {
                blankPos.row = row;
                blankPos.col = k - row * size;
            }

            initialState[row][k - row * size] = new Tile(goalPosition,value);

            if ((k + 1) % size == 0) row++;
        }

        // TODO: check if problem solvable
        return new Pair<>(initialState, blankPos);
    }

    public static int findBasicHeuristicsValue(Node currentNode) {

        int size = currentNode.currentState[0].length, heuristicsVal = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(currentNode.currentState[i][j].value.equals(Consts.BLANK.value))continue;
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

                if(currentNode.currentState[i][j].value.equals(Consts.BLANK.value))continue;

                var goalI = (currentNode.currentState[i][j].goalPosition / size);
                if (currentNode.currentState[i][j].goalPosition % size == 0) goalI--;
                var goalJ = (currentNode.currentState[i][j].goalPosition - 1) % size;

                //System.out.println(currentNode.currentState[i][j].value+ "-" +currentNode.currentState[i][j].goalPosition + " " + (goalI) + " " + goalJ);

                var h = (Math.abs(goalI - i) + Math.abs(goalJ - j));
                heuristicsVal += h;
                //System.out.println(currentNode.currentState[i][j].value+ "-" +currentNode.currentState[i][j].goalPosition + " " + h + " ");
            }
        }
        return heuristicsVal;
    }

    public static int countInversions(String [] puzzled, String[] goal) {

        var goalPositions = Arrays.asList(goal);

        var arrForInversionCount = new ArrayList<Integer>();

        for (String value : puzzled) {
            var goalPosition = goalPositions.indexOf(value);

            if (goalPosition == -1) {
                System.out.println("ERROR: Counting inversions. Couldnt find item in goal list");
                return -1;
            }
            arrForInversionCount.add(goalPosition);
        }

        Integer [] x = new Integer[puzzled.length];
        arrForInversionCount.toArray(x);

        System.out.println(Arrays.toString(x));
        return 0;
    }

    public static String hashBoardPositions(Node popped) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < popped.size; i++) {
            for (int j = 0; j < popped.size; j++) {
                sb.append(popped.currentState[i][j]).append("-");
            }
        }
        return sb.toString();
    }
}

