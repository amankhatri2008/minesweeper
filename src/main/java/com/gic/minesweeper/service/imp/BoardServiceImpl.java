package com.gic.minesweeper.service.imp;

import com.gic.minesweeper.config.MinesweeperProperties;
import com.gic.minesweeper.dto.Cell;
import com.gic.minesweeper.dto.CellState;
import com.gic.minesweeper.service.BoardService;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class BoardServiceImpl implements BoardService {

    private static final int[][] DIRECTIONS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
    };
    private final MinesweeperProperties props;
    private int size;
    private int numOfMines;
    private Cell[][] mineField;


    public BoardServiceImpl(MinesweeperProperties props) {
        this.props = props;
    }

    public void resetBoard() {
        this.size = 0;
        this.numOfMines = 0;
        this.mineField = null;

    }

    public void initializeBoard(int size, int numOfMines) {
        this.size = size;
        this.numOfMines = numOfMines;
        this.mineField = new Cell[size][size];

        constructBaseBoard();
    }

    private void constructBaseBoard() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                mineField[r][c] = new Cell(r, c, false);
            }
        }
        placeMinesAtRandomPositions();
    }

    private void placeMinesAtRandomPositions() {


        Random rand = new Random();
        int placed = 0;
        while (placed < numOfMines) {
            int randomR = rand.nextInt(size);
            int randomC = rand.nextInt(size);
            if (!mineField[randomR][randomC].isBomb()) {
                mineField[randomR][randomC].setBomb(true);
                placed++;
            }
        }
    }

    public boolean revealCell(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size || mineField[row][col].getState() == CellState.REVEALED) {
            return true;
        }
        mineField[row][col].setState(CellState.REVEALED);

        if (mineField[row][col].isBomb()) {
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
            if (newR >= 0 && newR < size && newC >= 0 && newC < size && mineField[newR][newC].isBomb()) {
                count++;
            }
        }
        return count;
    }

    public boolean allSafeCellsRevealed() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (!mineField[row][col].isBomb() && mineField[row][col].getState() == CellState.HIDDEN) {
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

                    if (mineField[row][Col].isBomb()) {
                        System.out.print(props.getBomb() + " ");
                    } else {
                        System.out.print(countAdjacentMines(row, Col) + " ");
                    }

                } else {
                    if (mineField[row][Col].getState() == CellState.REVEALED) {
                        if (mineField[row][Col].isBomb()) {
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


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumOfMines() {
        return numOfMines;
    }

    public void setNumOfMines(int numOfMines) {
        this.numOfMines = numOfMines;
    }

    public Cell[][] getMineField() {
        return mineField;
    }

    public void setMineField(Cell[][] mineField) {
        this.mineField = mineField;
    }


}

