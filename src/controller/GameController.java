package controller;

// Tambahkan import ini di bagian atas file
import view.GameButton;  // Import GameButton dari package view
import model.Board;
import model.GameState;
import view.GamePanel;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameController {
    private final Board board;
    private final GameState gameState;
    private final GamePanel gamePanel;
    private final Timer timer;
    private final JLabel moveLabel;
    private final JLabel timeLabel;

    public GameController(int size) {
        this.board = new Board(size);
        this.gameState = new GameState();
        this.gamePanel = new GamePanel(size, this::handleButtonClick);
        this.moveLabel = new JLabel("Moves: 0");
        this.timeLabel = new JLabel("Time: 0:00");
        this.timer = new Timer(1000, e -> updateTimer());
    }

    public void startNewGame() {
        gameState.reset();
        board.shuffle();
        updateView();
        timer.start();
    }

    private void handleButtonClick(ActionEvent e) {
        if (gameState.isGameOver()) return;

        GameButton button = (GameButton) e.getSource();
        String[] coordinates = button.getName().split(",");
        int row = Integer.parseInt(coordinates[0]);
        int col = Integer.parseInt(coordinates[1]);

        if (board.moveTile(row, col)) {
            gameState.incrementMoves();
            updateView();

            if (board.isSolved()) {
                handleGameOver();
            }
        }
    }

    private void handleGameOver() {
        timer.stop();
        gameState.setGameOver(true);
        showCongratulations();
    }

    private void updateTimer() {
        gameState.incrementTime();
        updateTimerLabel();
    }

    private void updateView() {
        gamePanel.updateButtons(board);
        moveLabel.setText("Moves: " + gameState.getMoveCount());
        updateTimerLabel();
    }

    private void updateTimerLabel() {
        int seconds = gameState.getTimeElapsed();
        timeLabel.setText("Time: " + String.format("%d:%02d", seconds / 60, seconds % 60));
    }

    private void showCongratulations() {
        JOptionPane.showMessageDialog(null,
                "Congratulations! You solved the puzzle!\n" +
                        "Moves: " + gameState.getMoveCount() + "\n" +
                        "Time: " + String.format("%d:%02d",
                        gameState.getTimeElapsed() / 60,
                        gameState.getTimeElapsed() % 60));
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }

    public JLabel getMoveLabel() {
        return moveLabel;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }
}