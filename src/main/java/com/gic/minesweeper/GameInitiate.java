package com.gic.minesweeper;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class GameInitiate {

    private final PlayGame playGame;

    public GameInitiate(PlayGame playGame) {
        this.playGame = playGame;
    }

    public void start() {
        boolean playAgain = true;
        Scanner scanner = new Scanner(System.in);

        while (playAgain) {
            playGame.start();
            System.out.println("Press ENTER to play again, or type 'q' to quit...");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q")) {
                playAgain = false;
            }
        }
        System.out.println("Thanks for playing Minesweeper!");
    }
}