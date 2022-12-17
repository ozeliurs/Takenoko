package com.takenoko.engine;

import com.takenoko.objective.EatBambooObjective;
import com.takenoko.objective.Objective;
import com.takenoko.bot.Action;
import com.takenoko.bot.Bot;
import com.takenoko.bot.TilePlacingBot;
import com.takenoko.ui.ConsoleUserInterface;

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

    protected BotManager(
            int numberOfActions,
            Objective objective,
            ConsoleUserInterface consoleUserInterface,
            String name,
            Bot bot) {
        this.numberOfActions = numberOfActions;
        this.objective = objective;
        this.consoleUserInterface = consoleUserInterface;
        this.name = name;
        this.bot = bot;
    }

    protected BotManager() {
        this(
                DEFAULT_NUMBER_OF_ACTIONS,
                DEFAULT_OBJECTIVE,
                DEFAULT_CONSOLE_USER_INTERFACE,
                DEFAULT_NAME,
                DEFAULT_BOT);
    }

    public BotManager(Bot bot) {
        this(
                DEFAULT_NUMBER_OF_ACTIONS,
                DEFAULT_OBJECTIVE,
                DEFAULT_CONSOLE_USER_INTERFACE,
                DEFAULT_NAME,
                bot);
    }

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

    public String getObjectiveDescription() {
        if (objective != null) {
            return objective.toString();
        }
        return "No current objective";
    }

    protected int getNumberOfActions() {
        return numberOfActions;
    }

    public void displayMessage(String message) {
        consoleUserInterface.displayMessage(message);
    }

    public boolean isObjectiveAchieved() {
        if (objective != null) {
            return objective.isAchieved();
        }
        return false;
    }

    public void verifyObjective(Board board) {
        if (objective != null) {
            objective.verify(board, this);
        }
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public String getName() {
        return name;
    }

    public int getEatenBambooCounter() {
        return eatenBambooCounter;
    }

    public void incrementBambooCounter() {
        eatenBambooCounter++;
    }
}
