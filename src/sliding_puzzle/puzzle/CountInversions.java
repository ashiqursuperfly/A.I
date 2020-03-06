package sliding_puzzle.puzzle;

import java.util.Arrays;

public class CountInversions {

    private static int countInternal(int[] arr, int l, int m, int r) {

        int[] leftSubArray = Arrays.copyOfRange(arr, l, m + 1);

        int[] rightSubArray = Arrays.copyOfRange(arr, m + 1, r + 1);

        int i = 0, j = 0, k = l, swaps = 0;

        while (i < leftSubArray.length && j < rightSubArray.length) {
            if (leftSubArray[i] <= rightSubArray[j])
                arr[k++] = leftSubArray[i++];
            else {
                arr[k++] = rightSubArray[j++];
                swaps += (m + 1) - (l + i);
            }
        }

        while (i < leftSubArray.length)
            arr[k++] = leftSubArray[i++];

        while (j < rightSubArray.length)
            arr[k++] = rightSubArray[j++];

        return swaps;
    }

    private static int countRecursive(int[] arr, int left, int right) {

        int count = 0;

        if (left < right) {
            int m = (left + right) / 2;

            count += countRecursive(arr, left, m);

            count += countRecursive(arr, m + 1, right);

            count += countInternal(arr, left, m, right);
        }

        return count;
    }


    public static int count(int[] arr) {
        return countRecursive(arr, 0, arr.length - 1);
    }

    public static void main(String[] arg)
    {
        int arr[] = {1,2,3,0,4,6,7,9,5,10,12,8,11,14,13};
        System.out.println(countRecursive(arr, 0, arr.length - 1));
    }

}