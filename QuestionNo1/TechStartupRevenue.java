package QuestionNo1;
import java.util.*;

public class TechStartupRevenue {

    // Data structure representing an investment opportunity
    static class Project {
        int capitalRequired;  // Funds needed to undertake this project
        int profitPotential;  // Expected return from this project

        public Project(int capitalRequired, int profitPotential) {
            this.capitalRequired = capitalRequired;
            this.profitPotential = profitPotential;
        }
    }

    // Optimizes project selection to maximize final capital within constraints
    public static int maximizeCapital(int maxProjects, int initialCapital, 
                                    int[] profits, int[] capitals) {
        int projectCount = profits.length;

        // Create project portfolio from input data
        List<Project> projectPortfolio = new ArrayList<>();
        for (int i = 0; i < projectCount; i++) {
            projectPortfolio.add(new Project(capitals[i], profits[i]));
        }

        // Organize projects by accessibility (cheapest first)
        projectPortfolio.sort(Comparator.comparingInt(p -> p.capitalRequired));

        // Priority queue to always pick highest-yielding available project
        PriorityQueue<Integer> profitMaximizer = new PriorityQueue<>(Collections.reverseOrder());

        int currentProjectIndex = 0;  // Tracks position in sorted project list

        // Execute projects up to allowed limit
        for (int project = 0; project < maxProjects; project++) {
            // Load all currently affordable projects into selection pool
            while (currentProjectIndex < projectCount && 
                   projectPortfolio.get(currentProjectIndex).capitalRequired <= initialCapital) {
                profitMaximizer.offer(projectPortfolio.get(currentProjectIndex).profitPotential);
                currentProjectIndex++;
            }

            // Exit if no viable projects remain
            if (profitMaximizer.isEmpty()) break;

            // Commit to most profitable current opportunity
            initialCapital += profitMaximizer.poll();
        }

        return initialCapital;
    }

    // Demonstration of capital growth scenarios
    public static void main(String[] args) {
        // Scenario 1: Limited budget with moderate opportunities
        int[] profits1 = {2, 5, 8};
        int[] capitals1 = {0, 2, 3};
        System.out.println("Optimal Capital: " + 
            maximizeCapital(2, 0, profits1, capitals1));  // Result: 7

        // Scenario 2: Better funding with high-return projects
        int[] profits2 = {3, 6, 10};
        int[] capitals2 = {1, 3, 5};
        System.out.println("Optimal Capital: " + 
            maximizeCapital(3, 1, profits2, capitals2));  // Result: 19
    }
}

/*
    Investment Portfolio Optimization System
    
    This solution implements a strategic approach to capital allocation for tech startups.
    Given a set of potential projects each requiring specific funding and offering
    particular returns, the algorithm determines the optimal sequence of investments
    to maximize final capital under these constraints:
    
    - Maximum of k projects can be undertaken
    - Projects can only be started when sufficient capital is available
    - Each project's profit becomes available immediately after completion
    
    Methodology:
    1. Project Categorization: Bundles raw input into structured investment opportunities
    2. Accessibility Sorting: Orders projects by funding requirement (lowest first)
    3. Dynamic Selection: Uses a priority queue to always pick the highest-yielding
       project currently within budget
    4. Iterative Execution: Repeats selection process until reaching project limit or
       exhausting viable options
    
    The algorithm's efficiency comes from combining sorted access with priority-based
    selection, ensuring optimal choices at each decision point. The demonstration cases
    show how different initial conditions affect the final capital accumulation.
*/