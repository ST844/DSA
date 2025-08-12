package QuestionNo4;

import java.util.*;

public class SecureTransmission {

    /*
     * Secure Network Communication Validator
     * 
     * Determines if secure message transmission is possible between offices
     * while avoiding compromised communication links (those with signal strength
     * exceeding security thresholds).
     */

    private Map<Integer, List<int[]>> officeConnections;  // Adjacency list representation

    // Initializes network with offices and their connections
    public SecureTransmission(int totalOffices, int[][] connections) {
        officeConnections = new HashMap<>();

        // Initialize empty connection lists for each office
        for (int i = 0; i < totalOffices; i++) {
            officeConnections.put(i, new ArrayList<>());
        }

        // Build bidirectional connections with their signal strengths
        for (int[] connection : connections) {
            int officeA = connection[0];
            int officeB = connection[1];
            int strength = connection[2];
            
            officeConnections.get(officeA).add(new int[]{officeB, strength});
            officeConnections.get(officeB).add(new int[]{officeA, strength});
        }
    }

    // Checks if secure path exists between two offices
    public boolean canTransmit(int sourceOffice, int destinationOffice, int securityThreshold) {
        Set<Integer> verifiedOffices = new HashSet<>();
        return verifyPath(sourceOffice, destinationOffice, securityThreshold, verifiedOffices);
    }

    // Recursive path verification with security constraints
    private boolean verifyPath(int currentOffice, int targetOffice, 
                             int maxAllowedStrength, Set<Integer> verified) {
        if (currentOffice == targetOffice) return true;
        verified.add(currentOffice);

        // Check all neighboring offices
        for (int[] connection : officeConnections.get(currentOffice)) {
            int neighbor = connection[0];
            int linkStrength = connection[1];

            // Only proceed if connection meets security requirements
            if (linkStrength < maxAllowedStrength && !verified.contains(neighbor)) {
                if (verifyPath(neighbor, targetOffice, maxAllowedStrength, verified)) {
                    return true;
                }
            }
        }

        return false;
    }

    // Demonstration of network security validation
    public static void main(String[] args) {
        // Create test network with 6 offices
        SecureTransmission network = new SecureTransmission(6, new int[][]{
            {0, 2, 4},  // Office 0-2 connection (strength 4)
            {2, 3, 1},   // Office 2-3 connection (strength 1)
            {2, 1, 3},   // Office 2-1 connection (strength 3)
            {4, 5, 5}    // Office 4-5 connection (strength 5)
        });

        // Test case 1: Can transmit through low-strength link
        System.out.println("Test 1 - Can transmit from 2 to 3 (threshold 2): " + 
            network.canTransmit(2, 3, 2));  // Expected: true

        // Test case 2: Blocked by high-strength requirement
        System.out.println("Test 2 - Can transmit from 1 to 3 (threshold 3): " + 
            network.canTransmit(1, 3, 3));   // Expected: false

        // Test case 3: Alternative secure path exists
        System.out.println("Test 3 - Can transmit from 2 to 0 (threshold 3): " + 
            network.canTransmit(2, 0, 3));   // Expected: true

        // Test case 4: No possible path
        System.out.println("Test 4 - Can transmit from 0 to 5 (threshold 6): " + 
            network.canTransmit(0, 5, 6));  // Expected: false
    }
}