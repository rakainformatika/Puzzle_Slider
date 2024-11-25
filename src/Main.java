import controller.GameController;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Puzzle Slider");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            GameController gameController = new GameController(3);

            // Create main panel
            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add info panel
            JPanel infoPanel = new JPanel(new FlowLayout());
            infoPanel.add(gameController.getMoveLabel());
            infoPanel.add(Box.createHorizontalStrut(20));
            infoPanel.add(gameController.getTimeLabel());

            // Add control panel
            JPanel controlPanel = new JPanel();
            JButton newGameButton = new JButton("New Game");
            newGameButton.addActionListener(e -> gameController.startNewGame());
            controlPanel.add(newGameButton);

            // Assemble the frame
            mainPanel.add(infoPanel, BorderLayout.NORTH);
            mainPanel.add(gameController.getGamePanel(), BorderLayout.CENTER);
            mainPanel.add(controlPanel, BorderLayout.SOUTH);

            frame.add(mainPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Start the game
            gameController.startNewGame();
        });
    }
}