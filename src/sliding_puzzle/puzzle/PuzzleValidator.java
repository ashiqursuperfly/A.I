package sliding_puzzle.puzzle;

import sliding_puzzle.data.Consts;

import java.util.ArrayList;
import java.util.Arrays;

public class PuzzleValidator {

    public static boolean isSolvable(String[] puzzled, String[] goal, int blankRow) {


        puzzled = removeBlank(puzzled);
        goal = removeBlank(goal);

        var goalPositions = Arrays.asList(goal);
        var arrForInversionCount = new ArrayList<Integer>();

        for (String value : puzzled) {
            var goalPosition = goalPositions.indexOf(value);

            if (goalPosition == -1) {
                System.out.println("ERROR: Counting inversions. Couldnt find item in goal list");
                return false;
            }
            arrForInversionCount.add(goalPosition);
        }

        Integer[] x = new Integer[puzzled.length];
        int[] arr = new int[x.length];


        arrForInversionCount.toArray(x);


        for (int i = 0; i < x.length; i++) {
            arr[i] = x[i];
        }

        var size = (int) Math.sqrt(puzzled.length + 1);

        if(size == 3) return isSolvable8(arr);
        else if(size == 4) return isSolvable15(arr,size, blankRow);
        else return false;
    }

    private static boolean isSolvable15(int[] x, int N, int blankRow) {

//        System.out.println(Arrays.toString(x));

        int invCount = CountInversions.count(x);

//      System.out.println("" + N + ":"+blankRow +" " +invCount);

        if(N % 2 != 0){
            return invCount % 2 == 0 ? true : false;
        }

        // if size is even
        if(invCount % 2 != 0 && blankRow % 2 ==0) return true;
        else if(invCount %2 ==0 && blankRow%2 != 0) return true;
        else return false;

    }

    private static boolean isSolvable8(int[] puzzle) {
        int invCount = CountInversions.count(puzzle);

        return (invCount % 2 == 0);
    }

    private static String[] removeBlank(String [] arr){

        String [] removed = new String[arr.length-1];

        for (int i = 0; i < arr.length; i++) {

            if(arr[i].equals(Consts.BLANK.value)) {
                if (arr.length - 1 - i >= 0) System.arraycopy(arr, i + 1, removed, i, arr.length - 1 - i);
                break;
            }
            removed[i] = arr[i];
        }

        return removed;

    }



}


