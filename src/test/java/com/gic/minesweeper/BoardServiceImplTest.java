package com.gic.minesweeper;

import com.gic.minesweeper.config.MinesweeperProperties;
import com.gic.minesweeper.dto.Cell;
import com.gic.minesweeper.dto.CellState;
import com.gic.minesweeper.service.imp.BoardServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoardServiceImplTest {

    @Autowired
    private BoardServiceImpl boardServiceImpl;

    @Autowired
    private MinesweeperProperties props;

    @Test
    void initializeBoard_shouldSetCorrectSizeAndMines() {
        int size = 5;
        int mines = 2;
        boardServiceImpl.initializeBoard(size, mines);
        assertEquals(size, boardServiceImpl.getSize());
        assertEquals(mines, boardServiceImpl.getNumOfMines());
        assertNotNull(boardServiceImpl.getMineField());

    }


    @Test
    public void testCountOfMines() {
        int size = 5;
        int mines = 3;
        boardServiceImpl.initializeBoard(size, mines);
        int countMines = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (boardServiceImpl.getMineField()[i][j].isBomb()) {
                    countMines++;
                }
            }
        }
        assertEquals(boardServiceImpl.getNumOfMines(), countMines);

    }

    @Test
    public void testCountOfEmpty() {
        int size = 5;
        int mines = 3;
        boardServiceImpl.initializeBoard(size, mines);
        int countEmpty = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!boardServiceImpl.getMineField()[i][j].isBomb()) {
                    countEmpty++;
                }
            }

        }
        assertEquals(size * size - mines, countEmpty);
    }

    @Test
    public void allSafeCellsRevealed_should_be_true_when_No_Mines() {
        int size = 4;
        int mines = 0;
        boardServiceImpl.initializeBoard(size, mines);
        assertTrue(boardServiceImpl.revealCell(0, 0));
        assertTrue(boardServiceImpl.allSafeCellsRevealed());
    }

    @Test
    public void allSafeCellsRevealed_should_be_false_when_atleast_One_Mines() {
        int size = 4;
        int mines = 1;
        boardServiceImpl.initializeBoard(size, mines);
        assertFalse(boardServiceImpl.allSafeCellsRevealed());
    }


    @Test
    public void countAdjacentMines_shouldBeZero() {
        int size = 3;
        int mines = 0;
        boardServiceImpl.initializeBoard(size, mines);
        boardServiceImpl.revealCell(1, 1);
        assertEquals(0, boardServiceImpl.countAdjacentMines(0, 0));
    }

    @Test
    void revealCell_onMine_shouldReturnFalse() {
        int size = 3;
        int mines = 1;
        boardServiceImpl.initializeBoard(size, mines);

        Cell[][] cell = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cell[i][j] = new Cell(i, j, false);
            }
        }
        cell[0][0].setBomb(true);
        ReflectionTestUtils.setField(boardServiceImpl, "mineField", cell);

        boolean result = boardServiceImpl.revealCell(0, 0);
        assertFalse(result);
        assertSame(CellState.REVEALED, boardServiceImpl.getMineField()[0][0].getState());
    }


    @Test
    void revealCell_onSafeCell_shouldReturnTrue() {

        int size = 3;
        int mines = 1;
        boardServiceImpl.initializeBoard(size, mines);
        Cell[][] cell = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cell[i][j] = new Cell(i, j, false);
            }
        }
        cell[0][0].setBomb(true);

        ReflectionTestUtils.setField(boardServiceImpl, "mineField", cell);
        boolean result = boardServiceImpl.revealCell(1, 1);
        assertTrue(result);
        assertSame(CellState.REVEALED, boardServiceImpl.getMineField()[1][1].getState());
    }


    @Test
    void revealCell_onSafeCell_check_Neighbor_MineValues_1Mine_should_return_1() {

        int size = 3;
        int mines = 1;
        boardServiceImpl.initializeBoard(size, mines);
        Cell[][] cell = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cell[i][j] = new Cell(i, j, false);
            }
        }
        cell[0][0].setBomb(true);

        ReflectionTestUtils.setField(boardServiceImpl, "mineField", cell);
        boolean result = boardServiceImpl.revealCell(1, 1);
        assertTrue(result);
        assertSame(CellState.REVEALED, boardServiceImpl.getMineField()[1][1].getState());

        assertSame(1, boardServiceImpl.countAdjacentMines(1, 1));
    }

    @Test
    void revealCell_onSafeCell_check_Neighbor_MineValues_2Mine_should_return_2() {

        int size = 3;
        int mines = 1;
        boardServiceImpl.initializeBoard(size, mines);
        Cell[][] cell = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cell[i][j] = new Cell(i, j, false);
            }
        }
        cell[0][0].setBomb(true);
        cell[0][1].setBomb(true);
        ReflectionTestUtils.setField(boardServiceImpl, "mineField", cell);
        boolean result = boardServiceImpl.revealCell(1, 1);
        assertTrue(result);
        assertSame(CellState.REVEALED, boardServiceImpl.getMineField()[1][1].getState());

        assertSame(2, boardServiceImpl.countAdjacentMines(1, 1));
    }
}
