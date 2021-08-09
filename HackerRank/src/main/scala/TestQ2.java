import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class TestQ2 {

    public static int solution(int N) {
        // write your code in Java SE 8
        int numberInserting = 5;

        boolean isNegative = false;

        int[] digits = Integer.toString(N).chars()
                .map(c -> Character.getNumericValue(c)).toArray();
        if(N < 0){
            // is negative
            isNegative = true;
            digits = Arrays.copyOfRange(digits, 1, digits.length);
        }

        int[] newDigits = new int[digits.length+1];

        int digits_i = 0;
        int newDigits_i = 0;

        boolean hasFoundPlaceForDigit = false;

        while(digits_i < digits.length && !hasFoundPlaceForDigit){
            if((!isNegative && digits[digits_i] < numberInserting) ||
                    (isNegative && digits[digits_i] > numberInserting)){
                newDigits[newDigits_i] = numberInserting;
                hasFoundPlaceForDigit = true;
            }else {
                newDigits[newDigits_i] = digits[digits_i];
                digits_i++;
            }
            newDigits_i++;
        }

        for(; newDigits_i < newDigits.length; newDigits_i++){
            newDigits[newDigits_i] = digits[digits_i++];
        }

        StringBuilder s = new StringBuilder();

        if(isNegative){
            s.append("-");
        }

        for (int i : newDigits)
        {
            s.append(i);
        }

        return Integer.parseInt(String.valueOf(s));
    }

    public static void main(String[] args) {
        int[] digits = Integer.toString(5658).chars()
                .map(c -> Character.getNumericValue(c)).toArray();
//        StringBuilder s = new StringBuilder();
//
//        for (int i : digits)
//        {
//            s.append(i); //add all the ints to a string
//        }

        System.out.println(solution(-999));
    }

}


