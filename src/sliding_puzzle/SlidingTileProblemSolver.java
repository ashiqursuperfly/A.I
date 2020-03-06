package sliding_puzzle;

import sliding_puzzle.data.Consts;
import sliding_puzzle.puzzle.Node;
import sliding_puzzle.puzzle.Utils;

import java.util.*;

public class SlidingTileProblemSolver {

    public Node solutionTree, goalNode;
    public List<Consts.Moves> movesMadeSoFar; //TODO: test if movesMade Sofar Can be recoverable using node.latestMove
    public Queue<Node> priorityQueue;
    public HashSet<String> solutionStatesHashed,expandedStatesHashed;
    private int nodesExpanded = 0;


    public SlidingTileProblemSolver(String[] puzzledListOfValues, String[] goalListOfValues) {
        solutionStatesHashed = new HashSet<>();
        expandedStatesHashed = new HashSet<>();
        priorityQueue = new PriorityQueue<>();
        solutionTree = new Node(puzzledListOfValues, goalListOfValues, true);
        goalNode = new Node(goalListOfValues, goalListOfValues, false);
        movesMadeSoFar = new ArrayList<>();
    }


    public void solve() {
        priorityQueue.add(solutionTree);

        while (!priorityQueue.isEmpty()) {
            var popped = priorityQueue.poll();
            recordMove(popped);

            if (Utils.isBoardPositionsClone(popped, goalNode)) {
                System.out.println("!! Solution Reached !!\n"
                        + '\n'
                        +"No Of Steps:"+ movesMadeSoFar.size()
                        +" Nodes Expanded:"+ nodesExpanded + "\n" + movesMadeSoFar);
                return;
            }

            Node l = Utils.clone(popped);
            Node r = Utils.clone(popped);
            Node u = Utils.clone(popped);
            Node d = Utils.clone(popped);

            if (l.applyMove(Consts.Moves.LEFT) && !solutionStatesHashed.contains(Utils.toStringBoardPositions(l))) {
                l.setParent(popped, Consts.Moves.LEFT);
                if(!expandedStatesHashed.contains(Utils.toStringBoardPositions(l))){
                    expand(l);
                }
            }

            if (r.applyMove(Consts.Moves.RIGHT) && !solutionStatesHashed.contains(Utils.toStringBoardPositions(r))) {
                r.setParent(popped, Consts.Moves.RIGHT);
                if(!expandedStatesHashed.contains(Utils.toStringBoardPositions(r))){
                    expand(r);
                }
            }

            if (u.applyMove(Consts.Moves.UP) && !solutionStatesHashed.contains(Utils.toStringBoardPositions(u))) {
                u.setParent(popped, Consts.Moves.UP);
                if(!expandedStatesHashed.contains(Utils.toStringBoardPositions(u))){
                    expand(u);
                }
            }

            if (d.applyMove(Consts.Moves.DOWN) && !solutionStatesHashed.contains(Utils.toStringBoardPositions(d))) {
                d.setParent(popped, Consts.Moves.DOWN);
                if(!expandedStatesHashed.contains(Utils.toStringBoardPositions(d))){
                    expand(d);
                }
            }
        }

        System.out.println("PQ Empty!\n" + movesMadeSoFar);


    }

    private void expand(Node n) {
        priorityQueue.add(n);
        expandedStatesHashed.add(Utils.toStringBoardPositions(n));
        nodesExpanded++;
    }

    private void recordMove(Node popped) {
        if(popped.latestMove != null){ // otherwise initial node
            movesMadeSoFar.add(popped.latestMove);
        }
        solutionStatesHashed.add(Utils.toStringBoardPositions(popped));

//
//        try {
//            System.out.println(popped.latestMove+":");
//            System.out.println(popped);
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public String toString() {
        return solutionTree.toString();
    }

}
