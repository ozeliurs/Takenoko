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
     * This method creates a blank new game.
     *
     * <ol>
     *   <li>Display the welcome message
     *   <li>Change game state to READY
     *   <li>Tell the user that the game is setup
     * </ol>
     */
    public void newGame() {
        consoleUserInterface.displayMessage("Welcome to Takenoko!");
        gameState = GameState.READY;

        consoleUserInterface.displayMessage(
                "The new game has been set up. You can start the game !");
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
