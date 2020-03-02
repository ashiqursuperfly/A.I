package sliding_puzzle;

public class Utils {

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

    public static Tile[][] constructBoardFromSequence(String[] puzzledListOfValues) {
        var size = (int) Math.sqrt(puzzledListOfValues.length + 1);

        var board = new Tile[size][size];

        if (!isBoardDimensionsValid(puzzledListOfValues.length - 1, board)) return null;

        int row = 0;
        for (int k = 0; k < puzzledListOfValues.length; k++) {

            var goal = 0;
            if (puzzledListOfValues[k].equals(Consts.BoardConsts.BLANK.value)) {
                goal = puzzledListOfValues.length;
            } else goal = Integer.parseInt(puzzledListOfValues[k]);

            board[row][k - row * size] = new Tile(goal);


            if ((k + 1) % size == 0) row++;
        }

        return board;
    }

    public static int findBasicHeuristicsValue(Board currentNode) {

        int size = currentNode.currentState[0].length, heuristicsVal = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                var valueCurrent = i * size + j + 1;
                if (valueCurrent != currentNode.currentState[i][j].goalPosition) heuristicsVal++;
            }
        }
        return heuristicsVal;
    }

    public static int findManhattanHeuristicsValue(Board currentNode) {
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
