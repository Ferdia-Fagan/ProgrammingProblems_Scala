import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// "Missing number" problem
class Solution {

    // O(n^{2}) solution
//    public static int solution(int[] A) {
//        int currentSmallestInteger = 1;
//
//        boolean smallestMissingIntegerNotFoundYet = false;
//
//        while (!smallestMissingIntegerNotFoundYet){
//            int i = 0;
//            while(i < A.length && A[i] != currentSmallestInteger){
//                i++;
//            }
//
//            if (i == A.length){
//                smallestMissingIntegerNotFoundYet = true;
//            }else{
//                currentSmallestInteger++;
//            }
//        }
//
//        return currentSmallestInteger;
//    }

    // Better O(N) solution
    public static int solution(int[] A) {
        int N = A.length;

        boolean[] lowestNumbersPossibleThatHaveBeenSeenInA = new boolean[N];
        Arrays.fill(lowestNumbersPossibleThatHaveBeenSeenInA, false);

        for(int i = 0; i < N;i++){
            int n = A[i] - 1;
            if(n >= 0 && n < N){
                lowestNumbersPossibleThatHaveBeenSeenInA[n] = true;
            }
        }

        for(int i = 0; i < N;i++){
            if(!lowestNumbersPossibleThatHaveBeenSeenInA[i]){
                return i+1;
            }
        }

        return N + 1;
    }

//    static void swap(int[] arr, int i, int j) {
//        int temp = arr[i];
//        arr[i] = arr[j];
//        arr[j] = temp;
//    }
//
//    static int partition(int[] arr, int low, int high)
//    {
//        int pivot = arr[high];
//
//        int i = (low - 1);
//
//        for(int j = low; j <= high - 1; j++) {
//            if (arr[j] < pivot) {
//                i++;
//                swap(arr, i, j);
//            }
//        }
//        swap(arr, i + 1, high);
//        return (i + 1);
//    }
//
//    public static void quickSort(int[] A, int low, int high){
//        if(low < high){
//            int partioningIndex = partition(A, low, high);
//
//            quickSort(A, low, partioningIndex - 1);
//            quickSort(A, partioningIndex + 1, high);
//        }
//    }
//
//    public static int solution(int[] A) {
//        // write your code in Java SE 8
//        quickSort(A, 0, A.length - 1);
//        int currentSmallestInteger = 1;
//        int i = 0;
//
//        while (i < A.length && currentSmallestInteger == A[i]) {
//            i++;
//            while (i < A.length && currentSmallestInteger == A[i]) {
//                i++;
//            }
//            currentSmallestInteger++;
//        }
//        return currentSmallestInteger;
//    }
////        for (int el: A) {
////            if(el == currentSmallestInteger){
////                currentSmallestInteger++;
////            }else{
////                return currentSmallestInteger;
////            }
////        }
////        return currentSmallestInteger;
//    }

//    public static int solution(int[] A) {
//
//    }

    public static void main(String[] args) {
//        System.out.println(solution(new int[]{1, 3, 6, 4, 1, 2}));
        int[] A = new int[]{1, 3, 6, 4, 1, 2};
        System.out.println(solution(A));
    }
}
