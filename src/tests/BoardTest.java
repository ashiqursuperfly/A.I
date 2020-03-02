package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sliding_puzzle.Consts;
import sliding_puzzle.SlidingTileProblemSolver;
import sliding_puzzle.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class BoardTest {

    private static final String FILE_NAME = "test_input";
    private static SlidingTileProblemSolver[] problems;


    @BeforeAll
    static void init() {
        File fullPath = new File(new File("").getAbsolutePath(), FILE_NAME);

        try (BufferedReader br = new BufferedReader(new FileReader(fullPath))) {
            String line;
            int nOfTestCases = Integer.parseInt(br.readLine());
            problems = new SlidingTileProblemSolver[nOfTestCases];

            for (int i = 0; i < nOfTestCases; i++) {
                line = br.readLine();
                problems[i] = new SlidingTileProblemSolver(line.split(","), Consts.Heuristics.MANHATTAN);
                System.out.println(problems[i]);
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    @Test
    void findBasicHeuristics(){

        for (SlidingTileProblemSolver s : problems) {
            var v = Utils.findBasicHeuristicsValue(s.solutionSteps.get(0));
            System.out.println(v);
        }
    }


}