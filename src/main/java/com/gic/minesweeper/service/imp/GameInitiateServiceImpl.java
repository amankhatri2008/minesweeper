package com.gic.minesweeper.service.imp;

import com.gic.minesweeper.service.GameInitiateService;
import com.gic.minesweeper.service.PlayGameService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class GameInitiateServiceImpl implements GameInitiateService {

    private final PlayGameService playGameService;

    public GameInitiateServiceImpl(PlayGameService playGameService) {
        this.playGameService = playGameService;
    }

    public void start() {
        boolean playAgain = true;
        Scanner scanner = new Scanner(System.in);

        while (playAgain) {
            playGameService.start();
            System.out.println("Press ENTER to play again, or type 'q' to quit...");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q")) {
                playAgain = false;
            }
        }
        System.out.println("Thanks for playing Minesweeper!");
    }
}