package sliding_puzzle;

import java.io.*;
import java.util.Arrays;

/**
 * n-puzzle problem means, total n+1 positions:
 * root(n+1) x root(n+1) board
 */

public class Board {

    enum BoardConsts {
        BLANK("B"), UP("U"), DOWN("D"), L("L"), R("R");

        public String value;

        BoardConsts(String value) {
            this.value = value;
        }
        }

    private BoardItem[][] board;
    private int blankPositionRow, blankPositionCol;

    private boolean validateInput(int n, BoardItem[][] board) {
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

    public Board(int n, BoardItem[][] board) {
        if (validateInput(n, board)) this.board = board;
    }

    public Board(String[] puzzledListOfValues) {
        int len = puzzledListOfValues.length;
        int size = (int) Math.sqrt(len + 1);
        this.board = new BoardItem[size][size];

        if (!validateInput(len - 1, board)) return;

        int row = 0;
        for (int k = 0; k < len; k++) {
            board[row][k - row * size] = new BoardItem(puzzledListOfValues[k]);
            if (puzzledListOfValues[k].equals(BoardConsts.BLANK.value)) {
                blankPositionRow = row;
                blankPositionCol = k - row * size;
            }

            if ((k + 1) % size == 0) row++;


        }

        System.out.println("Problem:\n" + this + "\n" + "Blank("+blankPositionRow+","+blankPositionCol+")\n");
    }

    public void solve() {
        //TODO:
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                sb.append(board[i][j]);
                if (j + 1 != board.length) sb.append(".....");
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
