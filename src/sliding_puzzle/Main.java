package sliding_puzzle;

import sliding_puzzle.data.Consts;
import sliding_puzzle.puzzle.SlidingTileProblemSolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        File fullPath = new File(new File("").getAbsolutePath(), SharedConfig.FILE_NAME);
        try (BufferedReader br = new BufferedReader(new FileReader(fullPath))) {
            String givenStates, goalStates;
            int nOfTestCases = Integer.parseInt(br.readLine()) - 1;
            goalStates = br.readLine();

            for (int i = 0; i < nOfTestCases; i++) {
                givenStates = br.readLine();
                try {
                    System.out.println("CASE:" + i);
                    SharedConfig.SELECTED_HEURISTICS = Consts.Heuristics.MANHATTAN_HEURISTIC;
                    new SlidingTileProblemSolver(givenStates.split(Consts.InputSeparator.value), goalStates.split(Consts.InputSeparator.value)).solve(true);
                    System.out.println();

                    SharedConfig.SELECTED_HEURISTICS = Consts.Heuristics.MISPLACEMENT_HEURISTIC;
                    new SlidingTileProblemSolver(givenStates.split(Consts.InputSeparator.value), goalStates.split(Consts.InputSeparator.value)).solve(true);
                    System.out.println();

                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }


}
