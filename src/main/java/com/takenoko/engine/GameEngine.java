package com.takenoko.engine;

import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.tile.PlaceTileAction;
import com.takenoko.bot.FullRandomBot;
import com.takenoko.inventory.Inventory;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.objective.MultipleGardenerObjective;
import com.takenoko.objective.PatternObjective;
import com.takenoko.objective.SingleGardenerObjective;
import com.takenoko.shape.PatternFactory;
import com.takenoko.ui.ConsoleUserInterface;
import java.util.ArrayList;
import java.util.List;

/** The game engine is responsible for the gameplay throughout the game. */
public class GameEngine {
    // ATTRIBUTES
    public static final int DEFAULT_NUMBER_OF_ROUNDS = 100;
    public static final int DEFAULT_NUMBER_OF_OBJECTIVES_TO_WIN = 5;
    private Board board;
    private final ConsoleUserInterface consoleUserInterface;
    private GameState gameState;
    private final int numberOfRounds;
    private final List<BotManager> botManagers;
    private final Scoreboard scoreboard;

    public GameEngine(
            int numberOfRounds,
            Board board,
            ConsoleUserInterface consoleUserInterface,
            GameState gameState,
            List<BotManager> botManagerList,
            Scoreboard scoreboard) {
        // Assign values to the attributes
        this.numberOfRounds = numberOfRounds;
        this.board = board;
        this.consoleUserInterface = consoleUserInterface;
        this.gameState = gameState;
        this.botManagers = botManagerList;
        this.scoreboard = scoreboard;
        scoreboard.addBotManager(botManagerList);
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
                new ArrayList<>(
                        List.of(
                                new BotManager(
                                        new ConsoleUserInterface(),
                                        "Joe",
                                        new FullRandomBot(),
                                        new BotState(
                                                2,
                                                List.of(
                                                        new MultipleGardenerObjective(
                                                                new SingleGardenerObjective(
                                                                        3, TileColor.GREEN, 0),
                                                                2,
                                                                0)),
                                                new Inventory(),
                                                List.of(
                                                        MovePandaAction.class,
                                                        MoveGardenerAction.class,
                                                        PlaceTileAction.class))),
                                new BotManager(
                                        new ConsoleUserInterface(),
                                        "Bob",
                                        new FullRandomBot(),
                                        new BotState(
                                                2,
                                                List.of(
                                                        new PatternObjective(
                                                                PatternFactory.LINE.createPattern(
                                                                        TileColor.ANY),
                                                                0)),
                                                new Inventory(),
                                                List.of(
                                                        MovePandaAction.class,
                                                        MoveGardenerAction.class,
                                                        PlaceTileAction.class))))),
                new Scoreboard());
    }

    public GameEngine(List<BotManager> botManagers) {
        this(
                DEFAULT_NUMBER_OF_ROUNDS,
                new Board(),
                new ConsoleUserInterface(),
                GameState.INITIALIZED,
                botManagers,
                new Scoreboard());
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
        consoleUserInterface.displayLineSeparator();

        if (gameState != GameState.INITIALIZED && gameState != GameState.FINISHED) {
            throw new IllegalStateException(
                    "The game is already started. You must end the game first.");
        }

        consoleUserInterface.displayMessage("Welcome to Takenoko!");

        // Reset all the attributes that needs to be
        this.board = new Board();
        for (BotManager botManager : botManagers) {
            botManager.reset();
        }

        // Game is now ready to be started
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
        for (int i = 0; i < numberOfRounds; i++) {
            consoleUserInterface.displayMessage("===== Round " + (i + 1) + " =====");
            for (BotManager botManager : botManagers) {
                consoleUserInterface.displayMessage(
                        "===== <" + botManager.getName() + "> is playing =====");
                boolean gameIsFinished = botManager.playBot(board);

                if (gameIsFinished
                        || botManager.getAchievedObjectives().size()
                                >= DEFAULT_NUMBER_OF_OBJECTIVES_TO_WIN) {
                    scoreboard.incrementNumberOfVictory(botManager);
                    consoleUserInterface.displayMessage(
                            botManager.getName()
                                    + " has won the game by being the first one to complete "
                                    + DEFAULT_NUMBER_OF_OBJECTIVES_TO_WIN
                                    + " objectives !");
                    gameState = GameState.FINISHED;
                    return;
                }
            }
        }
    }

    /** This method is used to end the game correctly. */
    public void endGame() {
        if (gameState != GameState.PLAYING && gameState != GameState.FINISHED) {
            throw new IllegalStateException(
                    "The game is not started yet. You must first start the game.");
        }

        consoleUserInterface.displayMessage(scoreboard.toString());

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

    /** Run a whole game from initialization to end. */
    public void runGame() {
        newGame();
        startGame();
        playGame();
        endGame();
    }

    public void runGame(int numberOfGames) {
        for (int i = 0; i < numberOfGames; i++) {
            runGame();
        }
    }
}
