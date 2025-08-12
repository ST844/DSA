package MazeSolver;

import java.util.*;

public class MazeGenerator {
    // Constants representing maze cell states
    private static final int WALL = 1;  // Represents blocked cells
    private static final int PASSAGE = 0;  // Represents walkable cells

    // Generates a random maze with a guaranteed solution
    public static int[][] generateMaze(int rows, int cols) {
        // Initialize all cells as walls initially
        int[][] maze = new int[rows][cols];
        for (int[] row : maze) Arrays.fill(row, WALL);

        // First create a basic path connecting start to finish
        buildMainRoute(maze, rows, cols);

        // Then add additional passages to make it maze-like
        addBranchingPaths(maze, rows, cols, 0.4); // 40% chance to open new paths

        return maze;
    }

    // Creates a primary path from top-left to bottom-right
    private static void buildMainRoute(int[][] maze, int rows, int cols) {
        int currentRow = 0, currentCol = 0;
        maze[currentRow][currentCol] = PASSAGE; // Open starting point

        Random randomizer = new Random();

        // Keep moving until we reach the end
        while (currentRow != rows - 1 || currentCol != cols - 1) {
            // Randomly choose to move down or right
            if (randomizer.nextBoolean()) {
                if (currentRow < rows - 1) currentRow++;
            } else {
                if (currentCol < cols - 1) currentCol++;
            }
            maze[currentRow][currentCol] = PASSAGE; // Mark as open
        }
    }

    // Randomly opens additional paths in the maze
    private static void addBranchingPaths(int[][] maze, int rows, int cols, double chanceToOpen) {
        Random randomizer = new Random();

        // Scan through all cells in the maze
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Randomly convert walls to passages based on probability
                if (maze[i][j] == WALL && randomizer.nextDouble() < chanceToOpen) {
                    maze[i][j] = PASSAGE;
                }
            }
        }
    }
}