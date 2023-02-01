package com.takenoko.engine;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.EndGameAction;
import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.tile.DrawTileAction;
import com.takenoko.actions.weather.ChooseIfApplyWeatherAction;
import com.takenoko.bot.Bot;
import com.takenoko.bot.TilePlacingBot;
import com.takenoko.inventory.Inventory;
import com.takenoko.objective.EmperorObjective;
import com.takenoko.objective.Objective;
import com.takenoko.ui.ConsoleUserInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manage one bot.
 *
 * <ul>
 *   <li>name
 *   <li>objective
 *   <li>number of actions
 *   <li>bamboos eaten counter
 * </ul>
 */
public class BotManager {
    private static final ConsoleUserInterface DEFAULT_CONSOLE_USER_INTERFACE =
            new ConsoleUserInterface();
    private static final String DEFAULT_NAME = "Joe";
    private static final Bot DEFAULT_BOT = new TilePlacingBot();
    // ATTRIBUTES
    private final ConsoleUserInterface consoleUserInterface;
    private final BotState botState;
    private final String name;
    private final Bot bot;
    private final int defaultNumberOfActions;

    public static final List<Class<? extends Action>> DEFAULT_AVAILABLE_ACTIONS =
            List.of(MovePandaAction.class, MoveGardenerAction.class, DrawTileAction.class);

    /**
     * Constructor for the class
     *
     * @param consoleUserInterface the console user interface
     * @param name the name of the bot
     * @param bot the bot
     * @param botState the bot state
     */
    protected BotManager(
            ConsoleUserInterface consoleUserInterface, String name, Bot bot, BotState botState) {
        this.botState = botState;
        this.consoleUserInterface = consoleUserInterface;
        this.name = name;
        this.bot = bot;
        this.defaultNumberOfActions = botState.getNumberOfActions();
    }

    /** Default constructor for the class */
    protected BotManager() {
        this(DEFAULT_CONSOLE_USER_INTERFACE, DEFAULT_NAME, DEFAULT_BOT, new BotState());
    }

    /**
     * Constructor for the class but this time specifying which bot algorithm must be used
     *
     * @param bot the bot
     */
    public BotManager(Bot bot) {
        this(DEFAULT_CONSOLE_USER_INTERFACE, DEFAULT_NAME, bot, new BotState());
    }

    /**
     * Ask for the bot to choose an action based on his algorithm and then execute the returned
     * action. Objectives are also verified in order to know if the bot has won.
     *
     * @param board the board of the game
     * @return true if the bot has won, false otherwise
     */
    public boolean playBot(Board board) {
        botState.setAvailableActions(new ArrayList<>(DEFAULT_AVAILABLE_ACTIONS));

        botState.setNumberOfActions(defaultNumberOfActions);

        board.rollWeather();
        botState.addAvailableAction(ChooseIfApplyWeatherAction.class);
        while (canPlayBot()) {
            botState.update(board, this);

            Action action = bot.chooseAction(board.copy(), botState.copy());
            if (!botState.getAvailableActions().contains(action.getClass())) {
                throw new IllegalStateException(
                        "The action "
                                + action.getClass().getSimpleName()
                                + " is not available for the bot "
                                + name
                                + ". Please choose another action.");
            }

            ActionResult actionResult = action.execute(board, this);

            botState.updateAvailableActions(action, actionResult);

            if (actionResult.availableActions().contains(EndGameAction.class)) {
                displayMessage("The bot " + name + " has drawn the Emperor Objective!");
                Objective objective = new EmperorObjective();
                botState.addObjective(objective);
                botState.setObjectiveAchieved(objective);
                botState.redeemObjective(objective);
                return true;
            }
        }
        board.getWeather().ifPresent(value -> value.revert(board, this));
        return false;
    }

    private boolean canPlayBot() {
        return !botState.getAvailableActions().isEmpty() && botState.getNumberOfActions() > 0;
    }

    /**
     * @return number of actions the bot can do in a turn
     */
    protected int getNumberOfActions() {
        return botState.getNumberOfActions();
    }

    /**
     * @param message the message to display
     */
    public void displayMessage(String message) {
        consoleUserInterface.displayMessage(message);
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
        return botState.getEatenBambooCounter();
    }

    /**
     * Return the bot inventory
     *
     * @return the bot inventory
     */
    public Inventory getInventory() {
        return botState.getInventory();
    }

    public void addAction() {
        botState.addAction();
    }

    public void reset() {
        this.botState.reset();
    }

    public int getObjectiveScore() {
        return botState.getObjectiveScore();
    }

    public List<Objective> getAchievedObjectives() {
        return botState.getAchievedObjectives();
    }

    public void addObjective(Objective objective) {
        botState.addObjective(objective);
    }

    public void redeemObjective(Objective objective) {
        botState.redeemObjective(objective);
    }

    public int getPandaObjectiveScore() {
        return botState.getPandaObjectiveScore();
    }
}
