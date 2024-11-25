package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private int[][] tiles;
    private int size;
    private Point emptyTilePos;

    public Board(int size) {
        this.size = size;
        this.tiles = new int[size][size];
        initializeBoard();
    }

    private void initializeBoard() {
        int value = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = value++;
            }
        }
        tiles[size-1][size-1] = 0;
        emptyTilePos = new Point(size-1, size-1);
    }

    public void shuffle() {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            List<Point> possibleMoves = getPossibleMoves();
            Point move = possibleMoves.get(random.nextInt(possibleMoves.size()));
            moveTile(move.x, move.y);
        }
    }

    public boolean moveTile(int row, int col) {
        if (isValidMove(row, col)) {
            tiles[emptyTilePos.x][emptyTilePos.y] = tiles[row][col];
            tiles[row][col] = 0;
            emptyTilePos = new Point(row, col);
            return true;
        }
        return false;
    }

    private List<Point> getPossibleMoves() {
        List<Point> moves = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newRow = emptyTilePos.x + dir[0];
            int newCol = emptyTilePos.y + dir[1];
            if (isValidPosition(newRow, newCol)) {
                moves.add(new Point(newRow, newCol));
            }
        }
        return moves;
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    private boolean isValidMove(int row, int col) {
        return isValidPosition(row, col) &&
                (Math.abs(row - emptyTilePos.x) + Math.abs(col - emptyTilePos.y) == 1);
    }

    public boolean isSolved() {
        int value = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size-1 && j == size-1) {
                    if (tiles[i][j] != 0) return false;
                } else if (tiles[i][j] != value++) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getTileValue(int row, int col) {
        return tiles[row][col];
    }

    public int getSize() {
        return size;
    }
}