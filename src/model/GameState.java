package model;

public class GameState {
    private int moveCount;
    private int timeElapsed;
    private boolean isGameOver;

    public GameState() {
        reset();
    }

    public void reset() {
        moveCount = 0;
        timeElapsed = 0;
        isGameOver = false;
    }

    public void incrementMoves() {
        moveCount++;
    }

    public void incrementTime() {
        timeElapsed++;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}