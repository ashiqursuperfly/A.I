package sliding_puzzle;


import java.util.PriorityQueue;

//TODO: check if problem is solvable
public class SlidingTileProblemSolver {

    public Node solutionNode;

    public SlidingTileProblemSolver(String[] puzzledListOfValues) {

        solutionNode = new Node(puzzledListOfValues);
    }

    public void solve() {
        var pq = new PriorityQueue<Node>();
        pq.add(solutionNode);

//        while (!pq.isEmpty()){
//            var popped = pq.poll();
//            var left = new Node(popped);
//
//
//
//            for(var c : popped.children){
//
//            }
//
//
//        }

    }

    public void applyMove(Consts.Moves move){
        solutionNode.applyMove(move);
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
