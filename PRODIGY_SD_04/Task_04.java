import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Task_04 extends JFrame {

    private JTextField[][] sudokuGrid;
    private JButton solveButton;
    private static final int SIZE = 9;

    public Task_04() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 500));
        JPanel sudokuPanel = new JPanel(new GridLayout(SIZE, SIZE));
        sudokuPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        sudokuGrid = new JTextField[SIZE][SIZE];
        Font gridFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sudokuGrid[i][j] = new JTextField(1);
                sudokuGrid[i][j].setHorizontalAlignment(JTextField.CENTER);
                sudokuGrid[i][j].setFont(gridFont);
                sudokuPanel.add(sudokuGrid[i][j]);
            }
        }
        add(sudokuPanel, BorderLayout.CENTER);
        solveButton = new JButton("Solve");
        solveButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveSudoku();
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        buttonPanel.add(solveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void solveSudoku() {
        int[][] grid = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                String text = sudokuGrid[i][j].getText().trim();
                if (!text.isEmpty()) {
                    grid[i][j] = Integer.parseInt(text);
                } else {
                    grid[i][j] = 0; 
                }
            }
        }

        if (solveSudoku(grid)) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    sudokuGrid[i][j].setText(Integer.toString(grid[i][j]));
                }
            }
            JOptionPane.showMessageDialog(this, "Sudoku puzzle solved successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "No solution exists for the Sudoku puzzle.");
        }
    }

    private static boolean solveSudoku(int[][] grid) {
        final int EMPTY = 0;
        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] == EMPTY) {
                    row = i;
                    col = j;
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        if (isEmpty) {
            return true;
        }

        for (int num = 1; num <= SIZE; num++) {
            if (isValidMove(grid, row, col, num)) {
                grid[row][col] = num;

                if (solveSudoku(grid)) {
                    return true;
                }

                grid[row][col] = EMPTY;
            }
        }
        return false;
    }

    private static boolean isValidMove(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (grid[row][i] == num || grid[i][col] == num) {
                return false;
            }
        }

        int subgridRowStart = row - row % 3;
        int subgridColStart = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[subgridRowStart + i][subgridColStart + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Task_04().setVisible(true);
            }
        });
    }
}
