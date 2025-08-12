package QuestionNo3;

import java.util.*;

public class MagicalWordPower {

    /*
     * Magical Word Power Calculator
     * 
     * Identifies the most powerful combination of two non-overlapping magical words.
     * A magical word is defined as an odd-length palindromic substring.
     * The power is calculated as the product of the lengths of the two words.
     */

    // Finds all odd-length palindromic substrings using center expansion
    private static List<int[]> findOddPalindromes(String input) {
        List<int[]> palindromeData = new ArrayList<>();

        for (int center = 0; center < input.length(); center++) {
            int left = center, right = center;
            // Expand outward from center while palindrome condition holds
            while (left >= 0 && right < input.length() && 
                   input.charAt(left) == input.charAt(right)) {
                int currentLength = right - left + 1;
                // Only consider odd-length palindromes
                if (currentLength % 2 == 1) {
                    // Store [start index, end index, length]
                    palindromeData.add(new int[]{left, right, currentLength});
                }
                left--;
                right++;
            }
        }

        return palindromeData;
    }

    // Calculates maximum power from two non-overlapping magical words
    public static int calculateMaxPower(String magicString) {
        List<int[]> allPalindromes = findOddPalindromes(magicString);
        int maxPower = 0;

        // Evaluate all possible pairs of palindromes
        for (int i = 0; i < allPalindromes.size(); i++) {
            int[] first = allPalindromes.get(i);
            for (int j = i + 1; j < allPalindromes.size(); j++) {
                int[] second = allPalindromes.get(j);

                // Verify the words don't overlap
                boolean nonOverlapping = (first[1] < second[0]) || (second[1] < first[0]);
                if (nonOverlapping) {
                    int currentPower = first[2] * second[2];
                    maxPower = Math.max(maxPower, currentPower);
                }
            }
        }

        return maxPower;
    }

    // Demonstration with test cases
    public static void main(String[] args) {
        // Test Case 1: Contains "zyz" (3) and "aba" (3)
        String test1 = "xyzyxabc";
        System.out.println("Test 1 - Maximum Power: " + calculateMaxPower(test1)); // Expect: 9

        // Test Case 2: Contains "racecar" (7) and "level" (5)
        String test2 = "levelwowracecar";
        System.out.println("Test 2 - Maximum Power: " + calculateMaxPower(test2)); // Expect: 35
    }
}