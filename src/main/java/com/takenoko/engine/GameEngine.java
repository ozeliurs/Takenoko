package com.takenoko.engine;

import com.takenoko.player.TilePlacingAndPandaMovingBot;
import com.takenoko.player.TilePlacingBot;
import com.takenoko.ui.ConsoleUserInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * The game engine is responsible for the gameplay throughout the game.
 */
public class GameEngine {
    public static final int DEFAULT_NUMBER_OF_ROUNDS = 10;
    private final Board board;
    private final ConsoleUserInterface consoleUserInterface;
    private GameState gameState;
    private final int numberOfRounds;
    private final List<BotManager> botManagers;

    public GameEngine(
            int numberOfRounds,
            Board board,
            ConsoleUserInterface consoleUserInterface,
            GameState gameState,
            List<BotManager> botManagerList) {
        this.numberOfRounds = numberOfRounds;
        this.board = board;
        this.consoleUserInterface = consoleUserInterface;
        this.gameState = gameState;
        this.botManagers = botManagerList;
    }

    /**
     * Constructor for the GameEngine class. Instantiate the board and the console user interface
     * used. It does not start the game ! It simply instantiates the objects needed for the game.
     */
    public GameEngine() {
        this(
                DEFAULT_NUMBER_OF_ROUNDS,
                new Board(),
                new ConsoleUserInterface(),
                GameState.INITIALIZED,
                new ArrayList<>(List.of(
                        new BotManager(new TilePlacingBot()),
                        new BotManager(new TilePlacingAndPandaMovingBot()))));
    }

    public GameEngine(List<BotManager> botManagers) {
        this(
                DEFAULT_NUMBER_OF_ROUNDS,
                new Board(),
                new ConsoleUserInterface(),
                GameState.INITIALIZED,
                botManagers);
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

    /**
     * This method change the game state to playing and add the first tile to the board.
     */
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

        for (BotManager botManager : botManagers) {
            consoleUserInterface.displayMessage(
                    botManager.getName() + " has the objective : " + botManager.getObjectiveDescription());
        }
    }

    public void playGame() {
        for (int i = 0; i < numberOfRounds; i++) {
            consoleUserInterface.displayMessage("===== Round " + (i + 1) + " =====");
            for (BotManager botManager : botManagers) {
                consoleUserInterface.displayMessage("===== <" + botManager.getName() + "> is playing =====");
                botManager.playBot(board);
                if (botManager.isObjectiveAchieved()) {
                    consoleUserInterface.displayMessage(
                            botManager.getName()
                                    + " has achieved the objective "
                                    + botManager.getObjectiveDescription()
                                    + ", it has won");
                    return;
                }
            }
        }
    }

    /**
     * This method is used to end the game correctly.
     */
    public void endGame() {
        if (gameState != GameState.PLAYING) {
            throw new IllegalStateException(
                    "The game is not started yet. You must first start the game.");
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
