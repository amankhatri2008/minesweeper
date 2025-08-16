package com.gic.minesweeper;

import com.gic.minesweeper.config.MinesweeperProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;

@Component
public class Board {

    private static final int[][] DIRECTIONS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
    };
    public final MinesweeperProperties props;
    public int size;
    public int numOfMines;
    public char[][] mineField;
    public boolean[][] revealed;


    public Board(MinesweeperProperties props) {
        this.props = props;
    }

    public void initializeBoard(int size, int numOfMines) {
        this.size = size;
        this.numOfMines = numOfMines;
        this.mineField = new char[size][size];
        this.revealed = new boolean[size][size];
        constructBaseBoard();
    }

    private void constructBaseBoard() {
        for (int i = 0; i < size; i++) {
            Arrays.fill(mineField[i], props.getBlank());
        }
        placeMinesAtRandomPositions();
    }

    private void placeMinesAtRandomPositions() {
        Random rand = new Random();
        int placed = 0;
        while (placed < numOfMines) {
            int randomR = rand.nextInt(size);
            int randomC = rand.nextInt(size);
            if (mineField[randomR][randomC] != props.getBomb()) {
                mineField[randomR][randomC] = props.getBomb();
                placed++;
            }
        }
    }

    public boolean revealCell(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size || revealed[row][col]) {
            return true;
        }
        revealed[row][col] = true;

        if (mineField[row][col] == props.getBomb()) {
            return false;
        }

        int count = countAdjacentMines(row, col);
        if (count == 0) {
            for (int[] dir : DIRECTIONS) {
                revealCell(row + dir[0], col + dir[1]);
            }
        }
        return true;
    }

    public int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int[] dir : DIRECTIONS) {
            int newR = row + dir[0];
            int newC = col + dir[1];
            if (newR >= 0 && newR < size && newC >= 0 && newC < size && mineField[newR][newC] == props.getBomb()) {
                count++;
            }
        }
        return count;
    }

    public boolean allSafeCellsRevealed() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (mineField[row][col] != props.getBomb() && !revealed[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard(boolean showAll) {
        System.out.print("  ");
        for (int col = 1; col <= size; col++) {
            System.out.print(col + " ");
        }
        System.out.println();

        for (int row = 0; row < size; row++) {
            char rowLabel = (char) ('A' + row);
            System.out.print(rowLabel + " ");
            for (int Col = 0; Col < size; Col++) {

                if (showAll) {
                    if (revealed[row][Col]) {
                        if (mineField[row][Col] == props.getBomb()) {
                            System.out.print(props.getBomb() + " ");
                        } else {
                            System.out.print(countAdjacentMines(row, Col) + " ");
                        }
                    } else {
                        System.out.print(mineField[row][Col] + " ");
                    }
                } else {
                    if (revealed[row][Col]) {
                        if (mineField[row][Col] == props.getBomb()) {
                            System.out.print(props.getBomb() + " ");
                        } else {
                            System.out.print(countAdjacentMines(row, Col) + " ");
                        }
                    } else {
                        System.out.print(props.getHidden() + " ");
                    }
                }
            }
            System.out.println();
        }
    }
}
