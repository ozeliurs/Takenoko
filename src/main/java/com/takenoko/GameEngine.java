package com.takenoko;

import com.takenoko.ui.ConsoleUserInterface;

public class GameEngine {
    private final Board board;
    private final ConsoleUserInterface consoleUserInterface;

    public GameEngine() {
        board = new Board();
        consoleUserInterface = new ConsoleUserInterface();
    }

    public void startGame() {
        consoleUserInterface.displayMessage("Welcome to Takenoko!");

        Tile tile = new Tile();
        board.placeTile(tile);

        consoleUserInterface.displayMessage(
                "The board is now set up with a single tile. You can start the game !");
    }

    public void endGame() {
        consoleUserInterface.displayMessage("The game is finished. Thanks for playing !");
    }

    /**
     * Returns the game board
     * @return board object
     */
    public Board getBoard() {
        return board;
    }
}
