package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sliding_puzzle.puzzle.Node;
import sliding_puzzle.data.Consts;
import sliding_puzzle.SlidingTileProblemSolver;
import sliding_puzzle.puzzle.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    private static final String FILE_NAME = "in.txt";
    private static SlidingTileProblemSolver[] problems;

    @BeforeAll
    static void init() {
        File fullPath = new File(new File("").getAbsolutePath(), FILE_NAME);
        try(BufferedReader br = new BufferedReader(new FileReader(fullPath))){

            String line,givenStates,goalStates;
            int nOfTestCases = Integer.parseInt(br.readLine());
            problems = new SlidingTileProblemSolver[nOfTestCases];

            for (int i = 0; i < nOfTestCases; i++) {
                line = br.readLine();
                var t = line.split(Consts.GivenStateAndGoalStateInputSeparator.value);
                givenStates = t[0];
                goalStates = t[1];

                problems[i] = new SlidingTileProblemSolver(
                        givenStates.split(Consts.InputSeparator.value),
                        goalStates.split(Consts.InputSeparator.value)
                );
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void findBasicHeuristics() {
        int[] expected = new int[]{2, 4, 15, 3};
        for (int i = 0; i < problems.length; i++) {
            SlidingTileProblemSolver s = problems[i];
            var v = Utils.findBasicHeuristicsValue(s.solutionNode);
            assertEquals(expected[i], v);
        }
    }

    @Test
    void findManhattanHeuristics() {
        int[] expected = new int[]{2, 4, 30, 8};

        for (int i = 0; i < problems.length; i++) {
            SlidingTileProblemSolver s = problems[i];
            var v = Utils.findManhattanHeuristicsValue(s.solutionNode);
            assertEquals(expected[i], v);
        }
    }

    @Test
    void boardComparatorTest() {
        var pq = new PriorityQueue<Node>();
        var s = new String[]{"A", "D", "C", "B"};
        var goal = new String[]{"A","B","C","D"};
        for (int i = 0; i < 10; i++) {
            var temp = new Node(s,goal);
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
            switch ((int) (Math.random()*Integer.MAX_VALUE % 4)) {
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
                System.out.println();
            }
            System.out.println();
        }

    }

    @Test
    void testCloneBoard(){
        for (SlidingTileProblemSolver problem : problems) {
            var board = Utils.clone(problem.solutionNode);
            assertTrue(Utils.isClone(board, problem.solutionNode));
        }
    }

}