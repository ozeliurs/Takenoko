package com.takenoko.engine;

import com.takenoko.bot.FullRandomBot;
import com.takenoko.bot.GeneralTacticBot;
import com.takenoko.objective.EmperorObjective;
import com.takenoko.objective.Objective;
import com.takenoko.ui.ConsoleUserInterface;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

/** The game engine is responsible for the gameplay throughout the game. */
public class GameEngine {
    // ATTRIBUTES
    public static final int DEFAULT_NUMBER_OF_ROUNDS = 200;
    public static final Map<Integer, Integer> DEFAULT_NUMBER_OF_OBJECTIVES_TO_WIN =
            Map.of(2, 9, 3, 8, 4, 7);
    private Board board;
    private final ConsoleUserInterface consoleUserInterface;
    private GameState gameState;
    private final int numberOfRounds;
    private final List<BotManager> botManagers;
    private final Scoreboard scoreboard;
    private final BotStatistics botStatistics;
    private History history;

    public GameEngine(
            int numberOfRounds,
            Board board,
            ConsoleUserInterface consoleUserInterface,
            GameState gameState,
            List<BotManager> botManagerList,
            Scoreboard scoreboard,
            BotStatistics botStatistics,
            History history) {
        // Assign values to the attributes
        this.numberOfRounds = numberOfRounds;
        this.board = board;
        this.consoleUserInterface = consoleUserInterface;
        this.gameState = gameState;
        this.botManagers = botManagerList;
        this.scoreboard = scoreboard;
        this.botStatistics = botStatistics;
        this.history = history;
        scoreboard.addBotManager(botManagerList);
        botStatistics.addBotManagers(botManagerList);
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
                                        new BotState(),
                                        new SingleBotStatistics()),
                                new BotManager(
                                        new ConsoleUserInterface(),
                                        "GeneralTactic",
                                        new GeneralTacticBot(),
                                        new BotState(),
                                        new SingleBotStatistics()))),
                new Scoreboard(),
                new BotStatistics(),
                new History());
    }

    public GameEngine(List<BotManager> botManagers) {
        this(
                DEFAULT_NUMBER_OF_ROUNDS,
                new Board(),
                new ConsoleUserInterface(),
                GameState.INITIALIZED,
                botManagers,
                new Scoreboard(),
                new BotStatistics(),
                new History());
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
        this.history = new History();

        for (BotManager botManager : botManagers) {
            botManager.reset();
            botManager.setStartingDeck(board.getStarterDeck());
            history.addBotManager(botManager);
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
        boolean isLastRound = false;
        BotManager botManagerWithEmperorObjective = null;

        for (int i = 0; i < numberOfRounds; i++) {
            consoleUserInterface.displayMessage(
                    "===== Round " + (board.getRoundNumber() + 1) + " =====");
            for (BotManager botManager : botManagers) {

                if (isLastRound && botManager.equals(botManagerWithEmperorObjective)) {
                    gameState = GameState.FINISHED;
                    consoleUserInterface.displayMessage("===== Game finished =====");
                    return;
                }
                consoleUserInterface.displayMessage(
                        "===== <" + botManager.getName() + "> is playing =====");
                botManager.playBot(board, history.getHistoryWithoutCurrentBotManager(botManager));

                if (botManager.getRedeemedObjectives().size()
                                >= DEFAULT_NUMBER_OF_OBJECTIVES_TO_WIN.get(botManagers.size())
                        && !isLastRound) {
                    consoleUserInterface.displayMessage(
                            "===== <" + botManager.getName() + "> finished the game =====");
                    Objective objective = new EmperorObjective();
                    consoleUserInterface.displayMessage(
                            botManager.getName() + " received the Emperor objective !");
                    botManager.addObjective(objective);
                    botManager.setObjectiveAchieved(objective);
                    botManager.redeemObjective(objective);
                    botManagerWithEmperorObjective = botManager;
                    isLastRound = true;
                    consoleUserInterface.displayMessage(
                            "==<Last round>==<Last round>==<Last round>==<Last round>==");
                }
            }
            board.nextRound();
        }
    }

    /** This method is used to end the game correctly. */
    public void endGame() {
        if (gameState != GameState.PLAYING && gameState != GameState.FINISHED) {
            throw new IllegalStateException(
                    "The game is not started yet. You must first start the game.");
        }
        Pair<List<BotManager>, EndGameState> winner = getWinner();

        switch (winner.getRight()) {
            case WIN_WITH_OBJECTIVE_POINTS -> consoleUserInterface.displayMessage(
                    "The winner is "
                            + winner.getLeft().get(0).getName()
                            + " with "
                            + winner.getLeft().get(0).getObjectiveScore()
                            + " points ! "
                            + "With the following objectives : "
                            + winner.getLeft().get(0).getRedeemedObjectives().stream()
                                    .map(Object::toString)
                                    .collect(Collectors.joining(", ")));
            case TIE -> consoleUserInterface.displayMessage(
                    "It's a tie !, the winners are : "
                            + winner.getLeft().stream()
                                    .map(BotManager::getName)
                                    .collect(Collectors.joining(", ")));
            case WIN_WITH_PANDA_OBJECTIVE_POINTS -> consoleUserInterface.displayMessage(
                    "The winner is "
                            + winner.getLeft().get(0).getName()
                            + " with "
                            + winner.getLeft().get(0).getPandaObjectiveScore()
                            + " points ! "
                            + "With the following objectives : "
                            + winner.getLeft().get(0).getRedeemedObjectives().stream()
                                    .map(Object::toString)
                                    .collect(Collectors.joining(", ")));
            default -> throw new IllegalStateException("Unexpected value: " + winner.getRight());
        }

        for (BotManager botManager : winner.getLeft()) {
            scoreboard.incrementNumberOfVictory(botManager);
            botStatistics.incrementWins(botManager);
        }
        for (BotManager botManager : botManagers) {
            if (!winner.getLeft().contains(botManager)) {
                botStatistics.incrementLosses(botManager);
            }
        }
        for (BotManager botManager : botManagers) {
            scoreboard.updateScore(botManager, botManager.getObjectiveScore());
            botStatistics.updateScore(botManager, botManager.getObjectiveScore());
            botManager.reset();
        }
        board.analyze();
        consoleUserInterface.displayFullStats(botStatistics.toString());
        consoleUserInterface.displayFullStats(board.getBoardStatistics().toString());
        consoleUserInterface.displayMessage("The game is finished. Thanks for playing !");
        gameState = GameState.FINISHED;
    }

    public String statSummary(int numberOfGames) {
        StringBuilder summary = new StringBuilder();
        String lineJump = "\n \t \t \t";
        summary.append("Summarized game statistics for ")
                .append(numberOfGames)
                .append(" games between ")
                .append(botManagers)
                .append(lineJump);
        summary.append("============== BotAverage ==============").append(lineJump);
        for (BotManager botManager : botManagers) {
            summary.append("< ")
                    .append(botManager.getName())
                    .append(" : Score avg per game- ")
                    .append(scoreboard.getTotalScore().get(botManager) / numberOfGames)
                    .append(" > | ")
                    .append(lineJump);
        }
        return summary.toString();
    }

    /**
     * Return the winner of the game.
     *
     * <p>If there is a tie, the reason of the end of the game is {@link EndGameState#TIE}. if there
     * is only one {@link BotManager} with the maximum number of points, the reason of the win is
     * {@link EndGameState#WIN_WITH_OBJECTIVE_POINTS}. if there is a tie between {@link BotManager}
     * with the maximum number of points and if there is a {@link BotManager} with the maximum
     * number of panda points, the reason of the win is {@link
     * EndGameState#WIN_WITH_PANDA_OBJECTIVE_POINTS}.
     *
     * @return a pair of the winner and the reason of the win.
     */
    public Pair<List<BotManager>, EndGameState> getWinner() {
        List<BotManager> botManagersWithMaxScore =
                botManagers.stream()
                        .filter(
                                botManager ->
                                        botManager.getObjectiveScore()
                                                == botManagers.stream()
                                                        .mapToInt(BotManager::getObjectiveScore)
                                                        .max()
                                                        .orElse(0))
                        .toList();
        if (botManagersWithMaxScore.size() == 1) {
            return Pair.of(
                    List.of(botManagersWithMaxScore.get(0)),
                    EndGameState.WIN_WITH_OBJECTIVE_POINTS);
        } else {
            List<BotManager> botManagersWithMaxPandaObjective =
                    botManagersWithMaxScore.stream()
                            .filter(
                                    botManager ->
                                            botManager.getPandaObjectiveScore()
                                                    == botManagersWithMaxScore.stream()
                                                            .mapToInt(
                                                                    BotManager
                                                                            ::getPandaObjectiveScore)
                                                            .max()
                                                            .orElse(0))
                            .toList();
            if (botManagersWithMaxPandaObjective.size() == 1) {
                return Pair.of(
                        List.of(botManagersWithMaxPandaObjective.get(0)),
                        EndGameState.WIN_WITH_PANDA_OBJECTIVE_POINTS);
            }
        }
        return Pair.of(botManagersWithMaxScore, EndGameState.TIE);
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
        consoleUserInterface.displayEnd("All " + numberOfGames + " games have been run :");
        consoleUserInterface.displayScoreBoard(scoreboard.toString());
        consoleUserInterface.displayStats(statSummary(numberOfGames));
    }
}
