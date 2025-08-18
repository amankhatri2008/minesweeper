package com.gic.minesweeper.dto;

public class Cell {

    private final int row;
    private final int col;
    private CellState state;
    private boolean isBomb;

    public Cell(int row, int col, boolean isBomb) {
        this.row = row;
        this.col = col;
        this.isBomb = isBomb;
        this.state = CellState.HIDDEN;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }


    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean isBomb) {
        this.isBomb = isBomb;
    }

}