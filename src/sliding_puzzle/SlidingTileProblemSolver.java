package sliding_puzzle;

import java.util.ArrayList;
import java.util.List;

public class SlidingTileProblemSolver {

    private final Consts.Heuristics heuristic;
    public List<Board> solutionSteps;

    public SlidingTileProblemSolver(String[] puzzledListOfValues, Consts.Heuristics manhattan) {
        this.heuristic = manhattan;

        solutionSteps = new ArrayList<>();
        solutionSteps.add(new Board(puzzledListOfValues));
    }

    public void solve(){}

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
        return solutionSteps.get(solutionSteps.size()-1).toString();
    }
}
