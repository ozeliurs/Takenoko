package com.takenoko;

import com.takenoko.ui.ConsoleUserInterface;

/** The game engine is responsible for the gameplay throughout the game. */
public class GameEngine {
    private final Board board;
    private final ConsoleUserInterface consoleUserInterface;
    private GameState gameState;

    /**
     * Constructor for the GameEngine class. Instantiate the board and the console user interface
     * used.
     */
    public GameEngine() {
        board = new Board();
        consoleUserInterface = new ConsoleUserInterface();
        gameState = GameState.INITIALIZED;
    }

    /**
     * This method starts the game. 1. Display the welcome message 2. Create the board with a single
     * tile 3. Tell the user that the board has been created
     */
    public void startGame() {
        consoleUserInterface.displayMessage("Welcome to Takenoko!");

        Tile tile = new Tile();
        board.placeTile(tile);

        gameState = GameState.READY;

        consoleUserInterface.displayMessage(
                "The board is now set up with a single tile. You can start the game !");
    }

    /** This method is used to end the game correctly. */
    public void endGame() {
        consoleUserInterface.displayMessage("The game is finished. Thanks for playing !");
        gameState = GameState.FINISHED;
    }

    /**
     * Returns the game board
     *
     * @return board object
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Return the current game state
     *
     * @return {@link GameState} object
     */
    public GameState getGameState() {
        return gameState;
    }
}
