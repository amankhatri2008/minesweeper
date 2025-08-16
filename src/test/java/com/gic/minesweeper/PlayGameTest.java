package com.gic.minesweeper;

import com.gic.minesweeper.config.MinesweeperProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PlayGameTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    @Autowired
    private PlayGame playGame;


    @Mock
    private Board board;

    @Mock
    private MinesweeperProperties props;

    private PrintStream originalOut;

    @BeforeEach
    void setup() {
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    private String getOutput() {
        return outContent.toString();
    }

    @Test
    void testWinningScenario() {

        String input = "2\n0\nA1\nA2\nB1\nB2\n";
        Scanner scanner = new Scanner(input);
        playGame.setScanner(scanner);

        playGame.start();

        String output = getOutput();
        assertTrue(output.contains("Congratulations, you have won the game!"));
    }

    @Test
    void testLosingScenario() {

        String input = "2\n1\nA1\nB1\nA2\nB2\n";
        Scanner scanner = new Scanner(input);
        playGame.setScanner(scanner);

        playGame.start();

        String output = getOutput();
        assertTrue(output.contains("Oh no, you detonated a mine!"));
    }

    @Test
    void getValidMove_withCorrectInput_shouldReturnMove() {
        String simulatedInput = "A1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        playGame.setScanner(new Scanner(System.in));
        String result = playGame.getValidMove(4);
        assertTrue(result.equalsIgnoreCase("A1"));
    }

    @Test
    void getValidMove_withInvalidThenCorrectInput_shouldHandleError() {
        String simulatedInput = "Z1\nA1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        playGame.setScanner(new Scanner(System.in));
        String result = playGame.getValidMove(4);
        assertTrue(outContent.toString().contains("Invalid row!"));
        assertTrue(result.equalsIgnoreCase("A1"));
    }

    @Test
    void testNonNumericGridSize() {

        String input = "abc\n3\n0\nA1\n"; //
        Scanner scanner = new Scanner(input);
        playGame.setScanner(scanner);

        playGame.start();

        String output = getOutput();
        assertTrue(output.contains("Invalid input! Enter a numeric value."));
    }

}
