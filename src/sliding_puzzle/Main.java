package sliding_puzzle;

import sliding_puzzle.data.Consts;
import sliding_puzzle.data.SharedConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
	    File fullPath = new File(new File("").getAbsolutePath(), SharedConfig.FILE_NAME);
        try(BufferedReader br = new BufferedReader(new FileReader(fullPath))){
            String line,givenStates,goalStates;
            int nOfTestCases = Integer.parseInt(br.readLine());

            for (int i = 0; i < nOfTestCases; i++) {
                line = br.readLine();
                var t = line.split(Consts.GivenStateAndGoalStateInputSeparator.value);
                givenStates = t[0];
                System.out.println(givenStates);
                goalStates = t[1];
                System.out.println(goalStates);

                new SlidingTileProblemSolver(givenStates.split(Consts.InputSeparator.value), goalStates.split(Consts.InputSeparator.value));
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }



}
