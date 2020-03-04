package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sliding_puzzle.Node;
import sliding_puzzle.Consts;
import sliding_puzzle.SlidingTileProblemSolver;
import sliding_puzzle.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class NodeTest {

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
                problems[i] = new SlidingTileProblemSolver(line.split(","));
                System.out.println(problems[i]);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            fail("Error: Reading test input file:" + FILE_NAME);
        }


    }

    @Test
    void findBasicHeuristics() {
        int[] expected = new int[]{2, 4, 15};

        for (int i = 0; i < problems.length; i++) {
            SlidingTileProblemSolver s = problems[i];
            var v = Utils.findBasicHeuristicsValue(s.solution);
            assertEquals(expected[i], v);
        }
    }

    @Test
    void findManhattanHeuristics() {
        int[] expected = new int[]{2, 4, 30};

        for (int i = 0; i < problems.length; i++) {
            SlidingTileProblemSolver s = problems[i];
            var v = Utils.findManhattanHeuristicsValue(s.solution);
            assertEquals(expected[i], v);
        }
    }

    @Test
    void boardComparatorTest() {
        var pq = new PriorityQueue<Node>();
        var s = new String[]{"2", "1", "3", "0"};
        for (int i = 0; i < 10; i++) {
            var temp = new Node(s);
            temp.heuristicVal = temp.height = i;
            pq.add(temp);
        }

        var i = 0;
        while (!pq.isEmpty()) {
            assertEquals(pq.poll().height, i++);
        }
    }

    @Test
    void boardApplyMovesTest() {
        List<Consts.Moves> moves = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            switch ((int) (Math.random() % 4)) {
                case 0:
                    moves.add(Consts.Moves.UP);
                    break;
                case 1:
                    moves.add(Consts.Moves.DOWN);
                    break;
                case 2:
                    moves.add(Consts.Moves.LEFT);
                    break;
                case 3:
                    moves.add(Consts.Moves.RIGHT);
                    break;
            }
        }


        for (var problem : problems) {
            System.out.println("Initial Board\n" + problem);
            for (var move : moves) {
                System.out.println("Applying move:" + move.value);
                problem.applyMove(move);
                System.out.println(problem);
            }
            System.out.println();
        }

    }

    @Test
    void testCopyConstructorBoard(){
        for (int i = 0; i < problems.length; i++) {
            var board = new Node(problems[i].solution);

            for (int j = 0; j < problems[i].solution.; j++) {

            }

            assertEquals( 0,Arrays.compare(board.currentState,problems[i].solution.currentState));
        }
    }

}