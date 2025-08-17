package com.gic.minesweeper;

import com.gic.minesweeper.config.MinesweeperProperties;
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
        assertNotNull(boardServiceImpl.getRevealed());
    }



    @Test
    public void testCountOfMines() {
        int size = 5;
        int mines = 3;
        boardServiceImpl.initializeBoard(size, mines);
        int countMines = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (boardServiceImpl.getMineField()[i][j] == props.getBomb()) {
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
                if (boardServiceImpl.getMineField()[i][j] == props.getBlank()) {
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
        boardServiceImpl.initializeBoard(3, 1);

        ReflectionTestUtils.setField(boardServiceImpl, "mineField", new char[][]{{props.getBomb(), props.getHidden(), props.getHidden()}, {props.getHidden(), props.getHidden(), props.getHidden()}, {props.getHidden(), props.getHidden(), props.getHidden()}});

        boolean result = boardServiceImpl.revealCell(0, 0);
        assertFalse(result);
        assertTrue(boardServiceImpl.getRevealed()[0][0]);
    }
    @Test
    void revealCell_onSafeCell_shouldReturnTrue() {
        boardServiceImpl.initializeBoard(3, 1);


        ReflectionTestUtils.setField(boardServiceImpl, "mineField", new char[][]{{props.getBomb(), props.getHidden(), props.getHidden()}, {props.getHidden(), props.getHidden(), props.getHidden()}, {props.getHidden(), props.getHidden(), props.getHidden()}});
        boolean result = boardServiceImpl.revealCell(1, 1);
        assertTrue(result);
        assertTrue(boardServiceImpl.getRevealed()[1][1]);
    }
}
