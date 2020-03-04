package sliding_puzzle;


import java.util.PriorityQueue;

//TODO: check if problem is solvable
public class SlidingTileProblemSolver {

    public Node solution;

    public SlidingTileProblemSolver(String[] puzzledListOfValues) {

        solution = new Node(puzzledListOfValues);
    }

    public void solve() {
        var pq = new PriorityQueue<Node>();
        pq.add(solution);

        while (!pq.isEmpty()){
            var popped = pq.poll();
            var left = new Node(popped);



            for(var c : popped.children){

            }


        }

    }

    public void applyMove(Consts.Moves move){
        solution.applyMove(move);
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
        return solution.toString();
    }
}
