package view;

import model.Board;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private GameButton[][] buttons;
    private final int size;

    public GamePanel(int size, ActionListener buttonListener) {
        this.size = size;
        setLayout(new GridLayout(size, size, 5, 5));
        initializeButtons(buttonListener);
    }

    private void initializeButtons(ActionListener listener) {
        buttons = new GameButton[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new GameButton("");
                buttons[i][j].addActionListener(listener);
                buttons[i][j].setName(i + "," + j);
                add(buttons[i][j]);
            }
        }
    }

    public void updateButtons(Board board) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = board.getTileValue(i, j);
                buttons[i][j].setText(value == 0 ? "" : String.valueOf(value));
            }
        }
    }
}