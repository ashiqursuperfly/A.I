package sliding_puzzle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ProblemSolver {

    private static String FILE_NAME = "in.txt";

    public static void main(String[] args) {
	// write your code here
        File fullPath = new File(new File("").getAbsolutePath(),FILE_NAME);
        try(BufferedReader br = new BufferedReader(new FileReader(fullPath))){
            String line;
            int nOfTestCases = Integer.parseInt(br.readLine());

            for (int i = 0; i < nOfTestCases; i++) {
                line = br.readLine();
                new Board(line.split(",")).solve();
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }



}
