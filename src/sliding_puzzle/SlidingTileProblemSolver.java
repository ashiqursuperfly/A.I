package sliding_puzzle;

import sliding_puzzle.data.Consts;
import sliding_puzzle.puzzle.Node;

import java.util.PriorityQueue;

//TODO: check if problem is solvable
public class SlidingTileProblemSolver {

    public Node solutionNode;

    public SlidingTileProblemSolver(String[] puzzledListOfValues, String[] goalListOfValues) {
        solutionNode = new Node(puzzledListOfValues, goalListOfValues);
    }

    public void solve() {
        var pq = new PriorityQueue<Node>();
        pq.add(solutionNode);



    }

    public boolean applyMove(Consts.Moves move){
        return solutionNode.applyMove(move);
    }

    private String[] generateGoalStateList(int n){

        var arr = new String[n];
        for (int i = 0; i < n-1; i++) {
            arr[i] = i+1+"";
        }
        arr[n-1] = -1+"";

        return arr;
    }

    @Override
    public String toString() {
        return solutionNode.toString();
    }
}
