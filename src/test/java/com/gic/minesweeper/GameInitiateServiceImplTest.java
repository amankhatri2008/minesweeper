package com.gic.minesweeper;

import com.gic.minesweeper.service.imp.GameInitiateServiceImpl;
import com.gic.minesweeper.service.imp.PlayGameServiceImpl;
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
public class GameInitiateServiceImplTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @Mock
    private PlayGameServiceImpl playGameServiceImpl;
    @InjectMocks
    private GameInitiateServiceImpl gameInitiateServiceImpl;

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

        gameInitiateServiceImpl.start();

        verify(playGameServiceImpl, times(1)).start();
        String output = outputStreamCaptor.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Thanks for playing Minesweeper!"));
    }

    @Test
    void testGamePlaysTwiceThenQuits() {
        String simulatedInput = "\n" + "q\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        gameInitiateServiceImpl.start();

        verify(playGameServiceImpl, times(2)).start();
        String output = outputStreamCaptor.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Thanks for playing Minesweeper!"));
    }
}