package sliding_puzzle.puzzle;

public class Puzzle15Validator {

    static int getInvCount(int[][] arr) {
        int inv_count = 0;
        for (int i = 0; i < 3 - 1; i++)
            for (int j = i + 1; j < 3; j++)

                // Value 0 is used for empty space
                if (arr[j][i] > 0 &&
                        arr[j][i] > arr[i][j])
                    inv_count++;
        return inv_count;
    }

    static boolean isSolvable(int[][] puzzle) {
        int invCount = getInvCount(puzzle);

        return (invCount % 2 == 0);
    }

    public static void main(String[] args) {
        int[][] puzzle = {{1, 8, 2}, {0, 4, 3}, {7, 6, 5}};
        if (isSolvable(puzzle))
            System.out.println("Solvable");
        else
            System.out.println("Not Solvable");
    }

}
