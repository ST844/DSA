package QuestionNo2;

import java.util.HashMap;
import java.util.Map;

public class UniqueDigitEquationChecker {

    /*
     * Validates cryptarithmetic puzzles where letters represent unique digits.
     * 
     * A cryptarithm must satisfy:
     * - Each letter maps to a distinct digit (0-9)
     * - Leading letters cannot be zero
     * - The translated equation must hold numerically
     */

    // Translates a letter-based word to its numeric value using provided mapping
    public static int wordToNumber(String word, Map<Character, Integer> mapping) {
        int result = 0;
        for (char letter : word.toCharArray()) {
            // Ensure all letters have been assigned a digit
            if (!mapping.containsKey(letter)) {
                throw new IllegalArgumentException("Missing digit mapping for: " + letter);
            }
            // Construct number digit by digit
            result = result * 10 + mapping.get(letter);
        }
        return result;
    }

    // Checks if the letter-digit mapping satisfies word1 + word2 = word3
    public static boolean verifyEquation(String word1, String word2, String word3, 
                                       Map<Character, Integer> mapping) {
        // Reject mappings that create leading zeros
        if (mapping.get(word1.charAt(0)) == 0 || 
            mapping.get(word2.charAt(0)) == 0 || 
            mapping.get(word3.charAt(0)) == 0) {
            System.out.println("Invalid mapping: Leading zero detected");
            return false;
        }

        // Convert each word to its numeric equivalent
        int firstTerm = wordToNumber(word1, mapping);
        int secondTerm = wordToNumber(word2, mapping);
        int sum = wordToNumber(word3, mapping);

        // Display conversion details for transparency
        System.out.printf("%s → %d\n%s → %d\n%s → %d\n", 
                         word1, firstTerm, word2, secondTerm, word3, sum);
        System.out.printf("%d + %d = %d\n", firstTerm, secondTerm, firstTerm + secondTerm);

        // Verify the arithmetic holds true
        return firstTerm + secondTerm == sum;
    }

    // Demonstrates the validator with sample cryptarithms
    public static void main(String[] args) {

        // Test Case 1: Valid cryptarithm
        // Mapping: S=8, T=4, A=2, R=5, M=7, O=1, N=9, I=6, G=3, H=0
        Map<Character, Integer> puzzle1 = new HashMap<>();
        puzzle1.put('S', 8);
        puzzle1.put('T', 4);
        puzzle1.put('A', 2);
        puzzle1.put('R', 5);
        puzzle1.put('M', 7);
        puzzle1.put('O', 1);
        puzzle1.put('N', 9);
        puzzle1.put('I', 6);
        puzzle1.put('G', 3);
        puzzle1.put('H', 0);

        System.out.println("Test Case 1: STAR + MOON = NIGHT");
        boolean isValid1 = verifyEquation("STAR", "MOON", "NIGHT", puzzle1);
        System.out.println("Solution valid? " + isValid1);
        System.out.println();

        // Test Case 2: Invalid cryptarithm (leading zero)
        // Mapping: C=1, O=0, D=5, E=7, B=3, U=9, G=2
        Map<Character, Integer> puzzle2 = new HashMap<>();
        puzzle2.put('C', 1);
        puzzle2.put('O', 0);
        puzzle2.put('D', 5);
        puzzle2.put('E', 7);
        puzzle2.put('B', 3);
        puzzle2.put('U', 9);
        puzzle2.put('G', 2);

        System.out.println("Test Case 2: CODE + BUG = DEBUG");
        boolean isValid2 = verifyEquation("CODE", "BUG", "DEBUG", puzzle2);
        System.out.println("Solution valid? " + isValid2);
    }
}

