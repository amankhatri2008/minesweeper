package com.gic.minesweeper.service;

public interface BoardService {
    void initializeBoard(int size, int numOfMines);


    void resetBoard();

    boolean revealCell(int row, int col);

    boolean allSafeCellsRevealed();

    void printBoard(boolean showAll);

}