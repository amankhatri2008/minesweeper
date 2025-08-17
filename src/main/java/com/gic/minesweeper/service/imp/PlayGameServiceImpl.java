package com.gic.minesweeper.service.imp;

import com.gic.minesweeper.config.MinesweeperProperties;
import com.gic.minesweeper.service.BoardService;
import com.gic.minesweeper.service.PlayGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class PlayGameServiceImpl implements PlayGameService {

    private final BoardService boardService;
    private final MinesweeperProperties props;
    private Scanner scanner;

    @Autowired
    public PlayGameServiceImpl(BoardService boardService, MinesweeperProperties props) {
        this.boardService = boardService;
        this.props = props;
        this.scanner = new Scanner(System.in);
    }


    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        boardService.resetBoard();
        System.out.println("Welcome to Minesweeper!");


        int size = 0;
        size = getSizeFromUserInput(size);


        int maxMines = (int) (size * size * props.getDefaultMaxMinePercentage());

        int mines = -1;
        while (mines < 0 || mines > maxMines) {
            mines = getMinesFromUserInput(maxMines, mines);
        }

        boardService.initializeBoard(size, mines);

        boolean gameOver = false;
        while (!gameOver) {
            boardService.printBoard(false);

            String move = getValidMove(size);

            int row = move.toUpperCase().charAt(0) - 'A';
            int col = Integer.parseInt(move.substring(1)) - 1;

            if (!boardService.revealCell(row, col)) {
                System.out.println("Oh no, you detonated a mine! Game over.");
                boardService.printBoard(true);
                gameOver = true;
            } else if (boardService.allSafeCellsRevealed()) {
                boardService.printBoard(true);
                System.out.println("Congratulations, you have won the game!");
                gameOver = true;
            }
        }
    }

    private int getMinesFromUserInput(int maxMines, int mines) {
        System.out.print("Enter number of mines (maximum is " + props.getDefaultMaxMinePercentage() * 100 + "% of the total squares, which is " + maxMines + "): ");
        String minesInput = scanner.nextLine().trim();
        if (minesInput.isEmpty()) {
            mines = props.getDefaultMines();
        } else {
            try {
                mines = Integer.parseInt(minesInput);
                if (mines < 0 || mines > maxMines) {
                    System.out.println("Number of mines must be between 0 and " + maxMines);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a numeric value.");
            }
        }
        return mines;
    }

    private int getSizeFromUserInput(int size) {
        while (size <= 0) {
            System.out.print("Enter the size of the grid (e.g. 4 for a 4x4 grid , default " + props.getDefaultSize() + "): ");
            String sizeInput = scanner.nextLine().trim();
            if (sizeInput.isEmpty()) {
                size = props.getDefaultSize();
            } else {
                try {
                    size = Integer.parseInt(sizeInput);
                    if (size <= 0) {
                        System.out.println("Grid size must be greater than 0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Enter a numeric value.");
                }
            }
        }
        return size;
    }

    public String getValidMove(int size) {
        while (true) {
            System.out.print("Select a square to reveal (e.g. A1): ");

            String input = scanner.nextLine().trim().toUpperCase();

            if (input.length() < 2) {
                System.out.println("Invalid input! Try again.");
                continue;
            }

            char rowChar = input.charAt(0);
            if (rowChar < 'A' || rowChar >= 'A' + size) {
                System.out.println("Invalid row! Use A-" + (char) ('A' + size - 1));
                continue;
            }

            String colPart = input.substring(1);
            int col;
            try {
                col = Integer.parseInt(colPart);
            } catch (NumberFormatException e) {
                System.out.println("Invalid column! Enter a number.");
                continue;
            }

            if (col < 1 || col > size) {
                System.out.println("Invalid column! Must be between 1 and " + size);
                continue;
            }

            return input;
        }
    }
}
