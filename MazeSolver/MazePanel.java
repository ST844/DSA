package MazeSolver;

import javax.swing.*;
import java.awt.*;

// Panel containing maze visualization and control buttons
public class MazePanel extends JPanel {
    // Maze configuration constants
    private final int rows = 20;          // Number of rows in maze
    private final int cols = 20;          // Number of columns in maze
    private final int cellSize = 30;      // Pixel size of each cell

    private int[][] maze;                 // 2D array representing maze structure
    private MazeSolver solver;            // Maze solving algorithm handler

    // Start and end positions (top-left to bottom-right)
    private Point start = new Point(0, 0);
    private Point end = new Point(rows - 1, cols - 1);

    private MazeCanvas mazeCanvas;        // Custom drawing surface for maze

    // Initialize maze panel components
    public MazePanel() {
        // Generate initial maze and solver
        maze = MazeGenerator.generateMaze(rows, cols);
        solver = new MazeSolver(maze, start, end);
        mazeCanvas = new MazeCanvas();

        // Create control buttons with their actions
        JButton dfsButton = createSolveButton("Solve with DFS", () -> {
            solver.solveDFS();
            mazeCanvas.repaint();
        });

        JButton bfsButton = createSolveButton("Solve with BFS", () -> {
            solver.solveBFS();
            mazeCanvas.repaint();
        });

        JButton generateButton = new JButton("Generate New Maze");
        generateButton.addActionListener(e -> {
            maze = MazeGenerator.generateMaze(rows, cols);
            solver = new MazeSolver(maze, start, end);
            mazeCanvas.setSolver(solver);
            mazeCanvas.repaint();
        });

        // Arrange buttons in panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(dfsButton);
        buttonPanel.add(bfsButton);
        buttonPanel.add(generateButton);

        // Set up main panel layout
        setLayout(new BorderLayout());
        add(mazeCanvas, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Helper method to create solve buttons
    private JButton createSolveButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        return button;
    }

    // Inner class for maze visualization
    class MazeCanvas extends JPanel {
        public MazeCanvas() {
            // Set canvas to match maze dimensions
            setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));
        }

        // Update solver reference when maze changes
        public void setSolver(MazeSolver newSolver) {
            solver = newSolver;
        }

        // Render maze and solution path
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            solver.draw(g, cellSize);
        }
    }
}
