package com.takenoko;

import com.takenoko.ui.ConsoleUserInterface;
import com.takenoko.vector.Vector;
import org.apache.commons.lang3.tuple.Pair;

/** The game engine is responsible for the gameplay throughout the game. */
public class GameEngine {
    private final Board board;
    private final ConsoleUserInterface consoleUserInterface;
    private GameState gameState;
    private final Bot bot;

    /**
     * Constructor for the GameEngine class. Instantiate the board and the console user interface
     * used. It does not start the game ! It simply instantiates the objects needed for the game.
     */
    public GameEngine() {
        board = new Board();
        consoleUserInterface = new ConsoleUserInterface();
        gameState = GameState.INITIALIZED;
        bot = new Bot();
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
        if (gameState != GameState.INITIALIZED) {
            throw new IllegalStateException(
                    "The game is already started. You must end the game first.");
        }

        consoleUserInterface.displayMessage("Welcome to Takenoko!");
        gameState = GameState.READY;

        consoleUserInterface.displayMessage(
                "The new game has been set up. You can start the game !");
    }

    /** This method change the game state to playing and add the first tile to the board. */
    public void startGame() {
        if (gameState == GameState.INITIALIZED) {
            throw new IllegalStateException(
                    "The game is not ready yet. You must first create a new game.");
        }
        if (gameState != GameState.READY) {
            throw new IllegalStateException(
                    "The game is already started. You must create a new game to call this method.");
        }

        gameState = GameState.PLAYING;
        consoleUserInterface.displayMessage("The game has started !");
    }

    public void playGame() {
        consoleUserInterface.displayMessage(
                "Bot must achieve the following objective in order to win"
                        + bot.getObjective().toString());
        Pair<Vector, Tile> tileToPlace =
                bot.chooseTileToPlace(board.getAvailableTiles(), board.getAvailableTilePositions());
        board.placeTile(tileToPlace.getRight(), tileToPlace.getLeft());
    }

    /** This method is used to end the game correctly. */
    public void endGame() {
        if (gameState != GameState.PLAYING) {
            throw new IllegalStateException(
                    "The game is not started yet. You must first start the game.");
        }
        if (bot.getObjective().isAchieved()) {
            consoleUserInterface.displayMessage("Bot has achieved the objective, it has won");
        }
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

    /**
     * @param gameState the game state to set
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
