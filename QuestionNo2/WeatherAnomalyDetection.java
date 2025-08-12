package QuestionNo2;

public class WeatherAnomalyDetection {

    /*
     * Detects unusual weather patterns by analyzing temperature fluctuations.
     * 
     * The algorithm identifies all continuous time periods where the cumulative
     * temperature change falls within a specified range, indicating potential anomalies.
     * 
     * Key Features:
     * - Efficient sum calculation using prefix sums technique
     * - Handles both positive and negative temperature variations
     * - Flexible threshold boundaries for anomaly detection
     */

    public static int countValidPeriods(int[] tempDeltas, int minThreshold, int maxThreshold) {
        int numDays = tempDeltas.length;
        int anomalyCount = 0;

        // Precompute cumulative sums for efficient range queries
        int[] cumulativeSums = new int[numDays + 1];
        for (int i = 1; i <= numDays; i++) {
            cumulativeSums[i] = cumulativeSums[i - 1] + tempDeltas[i - 1];
        }

        // Examine all possible time intervals
        for (int startDay = 0; startDay < numDays; startDay++) {
            for (int endDay = startDay; endDay < numDays; endDay++) {
                // Calculate total change for current time window
                int periodSum = cumulativeSums[endDay + 1] - cumulativeSums[startDay];

                // Check if the change qualifies as an anomaly
                if (periodSum >= minThreshold && periodSum <= maxThreshold) {
                    anomalyCount++;  // Record detected anomaly
                }
            }
        }

        return anomalyCount;
    }

    // Demonstration of anomaly detection with sample data
    public static void main(String[] args) {
        // Test Case 1: Moderate temperature variations
        int[] weatherData1 = {3, -1, -4, 6, 2};
        int minRange1 = 2, maxRange1 = 5;
        System.out.println("Test Case 1 - Anomalies Found: " + 
            countValidPeriods(weatherData1, minRange1, maxRange1));  // Expected: 4

        // Test Case 2: Mixed positive/negative fluctuations
        int[] weatherData2 = {-2, 3, 1, -5, 4};
        int minRange2 = -1, maxRange2 = 2;
        System.out.println("Test Case 2 - Anomalies Found: " + 
            countValidPeriods(weatherData2, minRange2, maxRange2));  // Expected: 4
    }
}
