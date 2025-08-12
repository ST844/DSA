package QuestionNo1;
public class SecurePinPolicy {

    // Calculates minimum modifications needed to strengthen a weak PIN
    public static int strongPinChanges(String pinCode) {
        int length = pinCode.length();
        boolean hasLowercase = false, hasUppercase = false, hasNumber = false;

        int sequenceCount = 0;
        int[] sequences = new int[length]; // Tracks lengths of repeating character runs

        // Analyze PIN composition: character types and repeating sequences
        for (int i = 0; i < length; ) {
            char current = pinCode.charAt(i);

            // Check character categories
            if (Character.isLowerCase(current)) hasLowercase = true;
            if (Character.isUpperCase(current)) hasUppercase = true;
            if (Character.isDigit(current)) hasNumber = true;

            // Measure length of current character sequence
            int j = i;
            while (j < length && pinCode.charAt(j) == current) j++;

            int seqLength = j - i;
            if (seqLength >= 3) {
                sequences[sequenceCount++] = seqLength;
            }
            i = j;
        }

        // Determine which required character types are missing
        int missingCategories = 0;
        if (!hasLowercase) missingCategories++;
        if (!hasUppercase) missingCategories++;
        if (!hasNumber) missingCategories++;

        // Case 1: PIN is too short (less than 6 characters)
        if (length < 6) {
            return Math.max(missingCategories, 6 - length);
        }

        // Calculate required sequence breaks (for runs of 3+ same characters)
        int requiredChanges = 0;
        for (int i = 0; i < sequenceCount; i++) {
            requiredChanges += sequences[i] / 3; // One change per triplet
        }

        // Case 2: PIN is within valid length range (6-20 characters)
        if (length <= 20) {
            return Math.max(missingCategories, requiredChanges);
        }

        // Case 3: PIN is too long (more than 20 characters)
        int excessChars = length - 20;
        int remainingExcess = excessChars;

        // Prioritize removing characters from long sequences first
        for (int i = 0; i < sequenceCount && remainingExcess > 0; i++) {
            if (sequences[i] >= 3) {
                int reduction = Math.min(remainingExcess, sequences[i] - 2);
                sequences[i] -= reduction;
                remainingExcess -= reduction;
            }
        }

        // Recalculate needed changes after length reduction
        requiredChanges = 0;
        for (int i = 0; i < sequenceCount; i++) {
            if (sequences[i] >= 3) {
                requiredChanges += sequences[i] / 3;
            }
        }

        return excessChars + Math.max(missingCategories, requiredChanges);
    }

    // Demonstration of PIN strength evaluation
    public static void main(String[] args) {
        System.out.println("Test Case 1:");
        System.out.println("Input: X1!");
        System.out.println("Output: " + strongPinChanges("X1!")); // Result: 3

        System.out.println("\nTest Case 2:");
        System.out.println("Input: 123456");
        System.out.println("Output: " + strongPinChanges("123456")); // Result: 2

        System.out.println("\nTest Case 3:");
        System.out.println("Input: Aa1234!");
        System.out.println("Output: " + strongPinChanges("Aa1234!")); // Result: 0
    }
}

/* 
    This security utility assesses PIN codes against strict complexity requirements. A compliant
    PIN must satisfy four criteria: proper length (6-20 characters), mixed case letters, at least
    one digit, and no extended character repetitions. The algorithm evaluates these requirements
    through a multi-stage analysis:

    1) Composition Scanning: Identifies existing character types and locates repeating sequences
    2) Deficiency Calculation: Counts missing character categories and excessive repetitions
    3) Length Adjustment: Handles cases where PINs are too short or too long differently
    4) Optimization: For long PINs, strategically removes characters to minimize total changes

    The solution efficiently combines these checks to determine the minimal edit distance to
    compliance. Test cases demonstrate handling of various scenarios:
    - Very short PINs needing both length extension and content improvement
    - Medium-length PINs requiring only content adjustments
    - Already compliant PINs needing no modifications
*/