package sliding_puzzle.puzzle;

import sliding_puzzle.SharedConfig;
import sliding_puzzle.data.Consts;

import java.util.*;

public class SlidingTileProblemSolver {

    public Node solutionTree, goalNode;
    public Queue<Node> priorityQueue;
    public HashMap<String,Integer> solutionStatesHashed, expandedStatesHashed;
    private int nodesExpanded = 0;
    private int poppedCount = 0;


    public SlidingTileProblemSolver(String[] puzzledListOfValues, String[] goalListOfValues) {
        solutionStatesHashed = new HashMap<>();
        expandedStatesHashed = new HashMap<>();
        priorityQueue = new PriorityQueue<>();
        solutionTree = new Node(puzzledListOfValues, goalListOfValues, true);
        goalNode = new Node(goalListOfValues, goalListOfValues, false);
    }


    public void solve(boolean shouldFindAll) {
        var initTime = System.currentTimeMillis();

        priorityQueue.add(solutionTree);
        solutionStatesHashed.put(Utils.toStringBoardPositions(solutionTree),solutionTree.height);

        while (!priorityQueue.isEmpty()) {
            var popped = priorityQueue.remove();
            recordMove(popped);

            if (Utils.isBoardPositionsClone(popped, goalNode)) {
                System.out.println(SharedConfig.SELECTED_HEURISTICS+" Solution Reached !!\nNodes Expanded:" + nodesExpanded + " Popped Nodes:"+ poppedCount);
                System.out.println("Time(millis):-"+ (System.currentTimeMillis() - initTime));
                simulate(popped);
                if(!shouldFindAll)return;
            }

            Node l = Utils.clone(popped);
            Node r = Utils.clone(popped);
            Node u = Utils.clone(popped);
            Node d = Utils.clone(popped);


            if (l.applyMove(Consts.Moves.LEFT)) {

                var keyL = Utils.toStringBoardPositions(l);

                if(solutionStatesHashed.get(keyL) == null) {
                    l.setParent(popped, Consts.Moves.LEFT);

                    var searchResult = expandedStatesHashed.get(Utils.toStringBoardPositions(l));
                    if (searchResult == null || searchResult > popped.height) {
                        expand(l);
                    }
                }
            }

            if (r.applyMove(Consts.Moves.RIGHT)) {

                var keyR = Utils.toStringBoardPositions(r);

                if(solutionStatesHashed.get(keyR) == null){
                    r.setParent(popped, Consts.Moves.RIGHT);

                    var searchResult = expandedStatesHashed.get(Utils.toStringBoardPositions(r));
                    if (searchResult == null || searchResult > popped.height) {
                        expand(r);
                    }
                }
            }

            if (u.applyMove(Consts.Moves.UP)) {

                var keyU = Utils.toStringBoardPositions(u);

                if(solutionStatesHashed.get(keyU) == null) {
                    u.setParent(popped, Consts.Moves.UP);

                    var searchResult = expandedStatesHashed.get(Utils.toStringBoardPositions(u));
                    if (searchResult == null || searchResult > popped.height) {
                        expand(u);
                    }
                }
            }
            if (d.applyMove(Consts.Moves.DOWN)) {

                var keyD = Utils.toStringBoardPositions(d);
                if(solutionStatesHashed.get(keyD) == null) {
                    d.setParent(popped, Consts.Moves.DOWN);

                    var searchResult = expandedStatesHashed.get(Utils.toStringBoardPositions(d));
                    if (searchResult == null || searchResult > popped.height) {
                        expand(d);
                    }
                }
            }
        }

        System.out.println("PQ Empty!\n");

    }

    private void expand(Node n) {
        priorityQueue.add(n);
        expandedStatesHashed.put(Utils.toStringBoardPositions(n),n.height);
        nodesExpanded++;
    }

    private void recordMove(Node popped) {
        var x = Utils.toStringBoardPositions(popped);

        solutionStatesHashed.put(x,popped.height);
        poppedCount++;


    }

    public void simulate(Node s) {
        List<Node> solutionPath = new ArrayList<>();
        solutionPath.add(s);
        while (s.getParent() != null) {
            solutionPath.add(s.getParent());
            if (s.latestMove != null)
                s = s.getParent();
        }


      /*  for (int i = solutionPath.size() - 1; i >= 0; i--) {
            System.out.println("--->"+solutionPath.get(i).latestMove + "\n" + solutionPath.get(i));
        }
      */
        for (int i = solutionPath.size() - 2; i >= 0; i--) {
            System.out.print(solutionPath.get(i).latestMove+"->");
        }
        System.out.println();
        System.out.println("Path Length:"+(solutionPath.size()-1));

    }

    @Override
    public String toString() {
        return solutionTree.toString();
    }

}
