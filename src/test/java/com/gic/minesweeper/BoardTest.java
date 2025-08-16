package com.gic.minesweeper;

import com.gic.minesweeper.config.MinesweeperProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoardTest {

    @Autowired
    private Board board;

    @Autowired
    private MinesweeperProperties props;

    @Test
    void initializeBoard_shouldSetCorrectSizeAndMines() {
        int size = 5;
        int mines = 2;
        board.initializeBoard(size, mines);
        assertEquals(size, board.size);
        assertEquals(mines, board.numOfMines);
        assertNotNull(board.mineField);
        assertNotNull(board.revealed);
    }



    @Test
    public void testCountOfMines() {
        int size = 5;
        int mines = 3;
        board.initializeBoard(size, mines);
        int countMines = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.mineField[i][j] == props.getBomb()) {
                    countMines++;
                }
            }
        }
        assertEquals(board.numOfMines, countMines);

    }

    @Test
    public void testCountOfEmpty() {
        int size = 5;
        int mines = 3;
        board.initializeBoard(size, mines);
        int countEmpty = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.mineField[i][j] == props.getBlank()) {
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
        board.initializeBoard(size, mines);
        assertTrue(board.revealCell(0, 0));
        assertTrue(board.allSafeCellsRevealed());
    }

    @Test
    public void allSafeCellsRevealed_should_be_false_when_atleast_One_Mines() {
        int size = 4;
        int mines = 1;
        board.initializeBoard(size, mines);
        assertFalse(board.allSafeCellsRevealed());
    }


    @Test
    public void countAdjacentMines_shouldBeZero() {
        int size = 3;
        int mines = 0;
        board.initializeBoard(size, mines);
        board.revealCell(1, 1);
        assertEquals(0, board.countAdjacentMines(0, 0));
    }

    @Test
    void revealCell_onMine_shouldReturnFalse() {
        board.initializeBoard(3, 1);

        ReflectionTestUtils.setField(board, "mineField", new char[][]{{props.getBomb(), props.getHidden(), props.getHidden()}, {props.getHidden(), props.getHidden(), props.getHidden()}, {props.getHidden(), props.getHidden(), props.getHidden()}});

        boolean result = board.revealCell(0, 0);
        assertFalse(result);
        assertTrue(board.revealed[0][0]);
    }
    @Test
    void revealCell_onSafeCell_shouldReturnTrue() {
        board.initializeBoard(3, 1);


        ReflectionTestUtils.setField(board, "mineField", new char[][]{{props.getBomb(), props.getHidden(), props.getHidden()}, {props.getHidden(), props.getHidden(), props.getHidden()}, {props.getHidden(), props.getHidden(), props.getHidden()}});
        boolean result = board.revealCell(1, 1);
        assertTrue(result);
        assertTrue(board.revealed[1][1]);
    }
}
