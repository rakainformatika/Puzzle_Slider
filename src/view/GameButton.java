package view;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Dimension;

public class GameButton extends JButton {
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Dimension BUTTON_SIZE = new Dimension(80, 80);

    public GameButton(String text) {
        super(text);
        setupButton();
    }

    private void setupButton() {
        setFont(BUTTON_FONT);
        setPreferredSize(BUTTON_SIZE);
        setFocusPainted(false);
    }
}