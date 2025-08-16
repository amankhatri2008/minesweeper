package com.gic.minesweeper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GameInitiateTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @Mock
    private PlayGame playGame;
    @InjectMocks
    private GameInitiate gameInitiate;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void testGamePlaysOnceThenQuits() {
        String simulatedInput = "q\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        gameInitiate.start();

        verify(playGame, times(1)).start();
        String output = outputStreamCaptor.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Thanks for playing Minesweeper!"));
    }

    @Test
    void testGamePlaysTwiceThenQuits() {
        String simulatedInput = "\n" + "q\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        gameInitiate.start();

        verify(playGame, times(2)).start();
        String output = outputStreamCaptor.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Thanks for playing Minesweeper!"));
    }
}