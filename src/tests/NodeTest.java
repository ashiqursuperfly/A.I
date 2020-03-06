package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sliding_puzzle.data.Position;
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

    private static final String FILE_NAME = "test_input";
    private static SlidingTileProblemSolver[] problems;
    private static boolean isDebug = false;

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

                System.out.println(problems[i]);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void findBasicHeuristics() {
        int[] expected = new int[]{3, 14, 4, 14, 3};
        for (int i = 0; i < problems.length; i++) {
            SlidingTileProblemSolver s = problems[i];
            var v = Utils.findBasicHeuristicsValue(s.solutionTree);
            //System.out.println(v+"-->");

            assertEquals(expected[i], v);
        }
    }

    @Test
    void findManhattanHeuristics() {
        int[] expected = new int[]{8, 26, 4, 25, 8};

        for (int i = 0; i < problems.length; i++) {
            SlidingTileProblemSolver s = problems[i];
            var v = Utils.findManhattanHeuristicsValue(s.solutionTree);
            assertEquals(expected[i], v);
        }
    }

    @Test
    void boardComparatorTest() {
        var pq = new PriorityQueue<Node>();
        var s = new String[]{"A", "D", "C", "B","X","Z","Y","N","K"};
        var goal = new String[]{"A","B","C","D","X","Y","Z","K","N"};
        for (int i = 0; i < 10; i++) {
            var temp = new Node(s,goal, false);
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
        for (int i = 0; i < 10000000; i++) {
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
            if(isDebug)System.out.println("Initial Board\n" + problem);
            for (var move : moves) {
                if(isDebug)System.out.println("Applying move:" + move.value);

                var prevBlank = new Position(problem.solutionTree.blankPos.row,problem.solutionTree.blankPos.col);
                problem.solutionTree.applyMove(move);
                var newBlank = new Position(problem.solutionTree.blankPos.row,problem.solutionTree.blankPos.col);

                var diff = Math.abs(prevBlank.row-newBlank.row) + Math.abs(prevBlank.col-newBlank.col);
                if(diff > 1)fail("Prev:"+prevBlank+" New:"+newBlank);


                if(isDebug)System.out.println(problem);
                if(isDebug)System.out.println();
            }
            if(isDebug)System.out.println();
        }

    }

    @Test
    void testCloneBoard(){
        for (SlidingTileProblemSolver problem : problems) {
            var board = Utils.clone(problem.solutionTree);

            assertTrue(Utils.isBoardPositionsClone(board, problem.solutionTree));
        }
    }

    @Test
    void countInversions(){
        var s = new String[]{"2","*","3","4","1","6","7","8","5","9","10","11","13","14","15","12"};
        var g = new String[]{"1","2","3","4","5","6","*","7","8","9","10","11","12","13","14","15"};

        //Utils.countInversions(s,g);
    }

}