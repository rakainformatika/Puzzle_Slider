import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SliderPuzzleGUI extends JFrame {
    private JButton[][] buttons;
    private int size;
    private int emptyRow;
    private int emptyCol;
    private JPanel puzzlePanel;
    private JLabel moveCountLabel;
    private int moveCount;
    private Timer timer;
    private JLabel timerLabel;
    private int secondsElapsed;

    public SliderPuzzleGUI(int size) {
        this.size = size;
        this.buttons = new JButton[size][size];
        this.moveCount = 0;
        this.secondsElapsed = 0;

        // Setup frame
        setTitle("Puzzle Slider Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create info panel for timer and move counter
        JPanel infoPanel = new JPanel(new FlowLayout());
        moveCountLabel = new JLabel("Moves: 0");
        timerLabel = new JLabel("Time: 0:00");
        infoPanel.add(moveCountLabel);
        infoPanel.add(Box.createHorizontalStrut(20));
        infoPanel.add(timerLabel);

        // Setup timer
        timer = new Timer(1000, e -> updateTimer());

        // Create puzzle panel
        puzzlePanel = new JPanel(new GridLayout(size, size, 5, 5));
        initializeButtons();
        shufflePuzzle();

        // Create control panel
        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> startNewGame());
        controlPanel.add(newGameButton);

        // Add panels to main panel
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(puzzlePanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void initializeButtons() {
        // Initialize buttons with numbers
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1) {
                    buttons[i][j] = new JButton("");
                    emptyRow = i;
                    emptyCol = j;
                } else {
                    buttons[i][j] = new JButton(String.valueOf(i * size + j + 1));
                }

                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 24));
                buttons[i][j].setPreferredSize(new Dimension(80, 80));

                final int row = i;
                final int col = j;
                buttons[i][j].addActionListener(e -> handleButtonClick(row, col));
                puzzlePanel.add(buttons[i][j]);
            }
        }
    }

    private void handleButtonClick(int row, int col) {
        // Check if clicked button is adjacent to empty space
        if (isAdjacent(row, col, emptyRow, emptyCol)) {
            // Swap button with empty space
            swapButtons(row, col, emptyRow, emptyCol);
            emptyRow = row;
            emptyCol = col;
            moveCount++;
            moveCountLabel.setText("Moves: " + moveCount);

            // Check if puzzle is solved
            if (isPuzzleSolved()) {
                timer.stop();
                JOptionPane.showMessageDialog(this,
                        "Congratulations! You solved the puzzle!\n" +
                                "Moves: " + moveCount + "\n" +
                                "Time: " + formatTime(secondsElapsed));
            }
        }
    }

    private boolean isAdjacent(int row1, int col1, int row2, int col2) {
        return (Math.abs(row1 - row2) == 1 && col1 == col2) ||
                (Math.abs(col1 - col2) == 1 && row1 == row2);
    }

    private void swapButtons(int row1, int col1, int row2, int col2) {
        String temp = buttons[row1][col1].getText();
        buttons[row1][col1].setText(buttons[row2][col2].getText());
        buttons[row2][col2].setText(temp);
    }

    private void shufflePuzzle() {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            // Get possible moves
            java.util.List<Point> possibleMoves = new java.util.ArrayList<>();

            // Check all four directions
            if (emptyRow > 0) possibleMoves.add(new Point(emptyRow - 1, emptyCol));
            if (emptyRow < size - 1) possibleMoves.add(new Point(emptyRow + 1, emptyCol));
            if (emptyCol > 0) possibleMoves.add(new Point(emptyRow, emptyCol - 1));
            if (emptyCol < size - 1) possibleMoves.add(new Point(emptyRow, emptyCol + 1));

            // Make random move
            Point move = possibleMoves.get(random.nextInt(possibleMoves.size()));
            swapButtons(move.x, move.y, emptyRow, emptyCol);
            emptyRow = move.x;
            emptyCol = move.y;
        }

        // Reset counters and start timer
        moveCount = 0;
        secondsElapsed = 0;
        moveCountLabel.setText("Moves: 0");
        timerLabel.setText("Time: 0:00");
        timer.start();
    }

    private boolean isPuzzleSolved() {
        int expectedValue = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                String buttonText = buttons[i][j].getText();
                if (i == size - 1 && j == size - 1) {
                    if (!buttonText.isEmpty()) return false;
                } else if (!buttonText.equals(String.valueOf(expectedValue))) {
                    return false;
                }
                expectedValue++;
            }
        }
        return true;
    }

    private void startNewGame() {
        timer.stop();
        shufflePuzzle();
    }

    private void updateTimer() {
        secondsElapsed++;
        timerLabel.setText("Time: " + formatTime(secondsElapsed));
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SliderPuzzleGUI puzzle = new SliderPuzzleGUI(3);
            puzzle.setVisible(true);
        });
    }
}