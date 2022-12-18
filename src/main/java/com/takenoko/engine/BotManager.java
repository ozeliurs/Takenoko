package com.takenoko.engine;

import com.takenoko.bot.Action;
import com.takenoko.bot.Bot;
import com.takenoko.bot.TilePlacingBot;
import com.takenoko.inventory.Inventory;
import com.takenoko.objective.EatBambooObjective;
import com.takenoko.objective.Objective;
import com.takenoko.ui.ConsoleUserInterface;

/**
 * This class is used to manage one bot. It is responsible for managing all of its attributes :
 *
 * <ul>
 *   <li>name
 *   <li>objective
 *   <li>number of actions
 *   <li>bamboos eaten counter
 * </ul>
 */
public class BotManager {
    // DEFAULT VALUES
    private static final int DEFAULT_NUMBER_OF_ACTIONS = 2;
    private static final Objective DEFAULT_OBJECTIVE = new EatBambooObjective(1);
    private static final ConsoleUserInterface DEFAULT_CONSOLE_USER_INTERFACE =
            new ConsoleUserInterface();
    private static final String DEFAULT_NAME = "Joe";
    private static final Bot DEFAULT_BOT = new TilePlacingBot();
    // ATTRIBUTES
    private final int numberOfActions;
    private Objective objective;
    private final ConsoleUserInterface consoleUserInterface;
    private int eatenBambooCounter = 0;
    private final String name;
    private final Bot bot;
    private final Inventory inventory;

    /**
     * Constructor for the class
     *
     * @param numberOfActions number of actions the bot can do in a turn
     * @param objective the bot must achieve the objective to win
     * @param consoleUserInterface the console user interface
     * @param name the name of the bot
     * @param bot the bot
     */
    protected BotManager(
            int numberOfActions,
            Objective objective,
            ConsoleUserInterface consoleUserInterface,
            String name,
            Bot bot,
            Inventory inventory) {
        this.numberOfActions = numberOfActions;
        this.objective = objective;
        this.consoleUserInterface = consoleUserInterface;
        this.name = name;
        this.bot = bot;
        this.inventory = inventory;
    }

    /** Default constructor for the class */
    protected BotManager() {
        this(
                DEFAULT_NUMBER_OF_ACTIONS,
                DEFAULT_OBJECTIVE,
                DEFAULT_CONSOLE_USER_INTERFACE,
                DEFAULT_NAME,
                DEFAULT_BOT,
                new Inventory());
    }

    /**
     * Constructor for the class but this time specifying which bot algorithm must be used
     *
     * @param bot the bot
     */
    public BotManager(Bot bot) {
        this(
                DEFAULT_NUMBER_OF_ACTIONS,
                DEFAULT_OBJECTIVE,
                DEFAULT_CONSOLE_USER_INTERFACE,
                DEFAULT_NAME,
                bot,
                new Inventory());
    }

    /**
     * Ask for the bot to choose an action based on his algorithm and then execute the returned
     * action. Objectives are also verified in order to know if the bot has won.
     *
     * @param board
     */
    public void playBot(Board board) {
        for (int i = 0; i < this.getNumberOfActions(); i++) {
            Action action = bot.chooseAction(board);
            action.execute(board, this);
            verifyObjective(board);
            if (isObjectiveAchieved()) {
                return;
            }
        }
    }

    /**
     * @return the objective description or "No current objective" if there is no objective
     */
    public String getObjectiveDescription() {
        if (objective != null) {
            return objective.toString();
        }
        return "No current objective";
    }

    /**
     * @return number of actions the bot can do in a turn
     */
    protected int getNumberOfActions() {
        return numberOfActions;
    }

    /**
     * @param message the message to display
     */
    public void displayMessage(String message) {
        consoleUserInterface.displayMessage(message);
    }

    /**
     * @return boolean to know is the objective is achieved or not
     */
    public boolean isObjectiveAchieved() {
        if (objective != null) {
            return objective.isAchieved();
        }
        return false;
    }

    /**
     * Verify the objective using the game board
     *
     * @param board current board game
     */
    public void verifyObjective(Board board) {
        if (objective != null) {
            objective.verify(board, this);
        }
    }

    /**
     * Change or set the bot objective
     *
     * @param objective the new objective
     */
    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    /**
     * @return the bot name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the number of bamboo eaten by the bot
     */
    public int getEatenBambooCounter() {
        return eatenBambooCounter;
    }

    /** Increment by one the number of bamboo eaten by the bot */
    public void incrementBambooCounter() {
        eatenBambooCounter++;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
