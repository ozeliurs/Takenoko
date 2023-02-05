package com.takenoko.engine;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.DefaultAction;
import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionCanBePlayedMultipleTimesPerTurn;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.actions.improvement.ApplyImprovementFromInventoryAction;
import com.takenoko.actions.irrigation.DrawIrrigationAction;
import com.takenoko.actions.irrigation.PlaceIrrigationFromInventoryAction;
import com.takenoko.actions.objective.DrawObjectiveAction;
import com.takenoko.actions.objective.RedeemObjectiveAction;
import com.takenoko.actions.tile.DrawTileAction;
import com.takenoko.inventory.Inventory;
import com.takenoko.objective.Objective;
import com.takenoko.weather.Windy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** This class is used to store the state of a bot. */
public class BotState {
    private static final int DEFAULT_NUMBER_OF_ACTIONS = 2;
    public static final int MAX_OBJECTIVES = 5;
    private final ObjectiveManager objectiveManager;

    private int numberOfActions;
    private final Inventory inventory;
    private List<Class<? extends Action>> availableActions;
    private List<Class<? extends Action>> alreadyDoneActions = new ArrayList<>();

    private static final List<Class<? extends DefaultAction>> DEFAULT_AVAILABLE_ACTIONS =
            List.of(
                    DrawObjectiveAction.class,
                    DrawTileAction.class,
                    DrawIrrigationAction.class,
                    MoveGardenerAction.class,
                    MovePandaAction.class,
                    RedeemObjectiveAction.class,
                    PlaceIrrigationFromInventoryAction.class,
                    ApplyImprovementFromInventoryAction.class);

    public BotState(
            int numberOfActions,
            Inventory inventory,
            List<Class<? extends Action>> availableActions,
            ObjectiveManager objectiveManager) {
        this.numberOfActions = numberOfActions;
        this.inventory = inventory;
        this.availableActions = availableActions;
        this.objectiveManager = objectiveManager;
    }

    public BotState() {
        this(DEFAULT_NUMBER_OF_ACTIONS, new Inventory(), new ArrayList<>(), new ObjectiveManager());
    }

    /**
     * Copy constructor
     *
     * @param botState the state to copy
     */
    public BotState(BotState botState) {
        this.numberOfActions = botState.numberOfActions;
        this.inventory = botState.getInventory().copy();
        this.availableActions = new ArrayList<>(botState.availableActions);
        this.alreadyDoneActions = new ArrayList<>(botState.alreadyDoneActions);
        this.objectiveManager = new ObjectiveManager(botState.objectiveManager);
    }

    // ------------------------------------------------------ //
    // ------------------- OBJECT METHODS ------------------- //
    // ------------------------------------------------------ //

    /** reset everything to the default values */
    public void reset() {
        this.objectiveManager.reset();
        this.inventory.clear();
        this.numberOfActions = DEFAULT_NUMBER_OF_ACTIONS;
        this.availableActions = new ArrayList<>();
        this.alreadyDoneActions.clear();
    }

    /**
     * make a copy of the current state
     *
     * @return the copy
     */
    public BotState copy() {
        return new BotState(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotState botState = (BotState) o;
        return getNumberOfActions() == botState.getNumberOfActions()
                && getObjectives().equals(botState.getObjectives())
                && getInventory().equals(botState.getInventory())
                && getObjectiveScore() == botState.getObjectiveScore()
                && getAchievedObjectives().equals(botState.getAchievedObjectives())
                && getObjectiveManager().equals(botState.getObjectiveManager());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getNumberOfActions(),
                getObjectives(),
                getInventory(),
                getAchievedObjectives(),
                getObjectiveScore(),
                getObjectiveManager());
    }

    // ------------------------------------------------------ //
    // ----------------- INVENTORY METHODS ------------------ //
    // ------------------------------------------------------ //

    /**
     * @return the number of bamboo eaten by the bot
     */
    public int getEatenBambooCounter() {
        return inventory.getBambooCount();
    }

    /**
     * Return the bot inventory
     *
     * @return the bot inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    // ------------------------------------------------------ //
    // -------------- METHOD RELATED TO ACTIONS ------------- //
    // ------------------------------------------------------ //

    public void setNumberOfActions(int numberOfActions) {
        this.numberOfActions = numberOfActions;
    }

    /**
     * @return number of actions the bot can do in a turn
     */
    protected int getNumberOfActions() {
        return numberOfActions;
    }


    /**
     * Return the list of available actions. If actions of FORCED type are available, only these
     * actions are returned else all available actions are returned.
     *
     * @return the list of available actions
     */
    public List<Class<? extends Action>> getAvailableActions() {
        List<Class<? extends Action>> forcedActions =
                availableActions.stream()
                        .filter(
                                action ->
                                        action.getAnnotation(ActionAnnotation.class).value()
                                                == ActionType.FORCED)
                        .toList();

        if (forcedActions.isEmpty()) {
            return availableActions;
        } else {
            return forcedActions;
        }
    }

    /**
     * Set the list of available actions
     *
     * @param availableActions the list of available actions
     */
    public void setAvailableActions(List<Class<? extends Action>> availableActions) {
        this.availableActions = availableActions;
    }

    /**
     * add an action to the list of available actions
     *
     * @param action the action to add
     */
    public void addAvailableAction(Class<? extends Action> action) {
        this.availableActions.add(action);
    }

    /**
     * add a list of actions to the list of available actions
     *
     * @param actions the list of actions to add
     */
    public void addAvailableActions(List<Class<? extends Action>> actions) {
        this.availableActions.addAll(actions);
    }

    /** add an action to the number of actions to plau this turn */
    public void addAction() {
        numberOfActions++;
    }

    /** clear the list of available actions of the FORCED type */
    private void clearForcedActions() {
        availableActions.removeIf(
                action ->
                        action.getAnnotation(ActionAnnotation.class).value() == ActionType.FORCED);
    }

    /**
     * update an action in available actions
     *
     * @param action the action to update
     * @param actionResult the result of the action
     */
    public void updateAvailableActions(Action action, ActionResult actionResult) {
        this.availableActions.remove(action.getClass());
        this.alreadyDoneActions.add(action.getClass());
        this.clearForcedActions();
        this.addAvailableActions(actionResult.availableActions());
        this.setNumberOfActions(this.getNumberOfActions() - actionResult.cost());
    }

    /**
     * update the objectives
     *
     * @param board the board
     * @param botManager the bot manager
     */
    public void update(Board board, BotManager botManager) {
        this.objectiveManager.updateObjectives(board, botManager);
        updateDefaultActions(board);
    }

    /**
     * update to the defaults actions
     *
     * @param board the board
     */
    private void updateDefaultActions(Board board) {
        availableActions.removeIf(
                action ->
                        action.getAnnotation(ActionAnnotation.class).value() == ActionType.DEFAULT);
        DEFAULT_AVAILABLE_ACTIONS.stream()
                .filter(
                        actionClass ->
                                DefaultAction.canBePlayed(board, this, actionClass)
                                        && (actionClass.isAnnotationPresent(
                                                        ActionCanBePlayedMultipleTimesPerTurn.class)
                                                || !alreadyDoneActions.contains(actionClass)
                                                || board.getWeather()
                                                        .map(v -> v.getClass().equals(Windy.class))
                                                        .orElse(false)))
                .forEach(actionClass -> availableActions.add(actionClass));
    }

    /**
     * reset the available actions
     *
     * @param board the board
     */
    public void resetAvailableActions(Board board) {
        this.availableActions = new ArrayList<>(DEFAULT_AVAILABLE_ACTIONS);
        this.alreadyDoneActions = new ArrayList<>();
        updateDefaultActions(board);
    }

    /**
     * get the list of already done actions
     *
     * @return the list of already done actions
     */
    public List<Class<? extends Action>> getAlreadyDoneActions() {
        return new ArrayList<>(alreadyDoneActions);
    }

    // ------------------------------------------------------ //
    // ------------ METHOD RELATED TO OBJECTIVES ------------ //
    // ------------------------------------------------------ //

    /**
     * Get the objective manager
     * @return ObjectiveManager
     */
    private ObjectiveManager getObjectiveManager() {
        return objectiveManager;
    }

    /**
     * Get the current Objectives of the bot
     *
     * @return Objectives
     */
    public List<Objective> getObjectives() {
        return objectiveManager.getObjectives();
    }

    /**
     * Set the current Objective of the bot
     *
     * @param objective the objectives
     */
    public void addObjective(Objective objective) {
        this.objectiveManager.addObjective(objective);
    }

    /**
     * get the score of the achieved objectives
     *
     * @return the score of the achieved objectives
     */
    public int getObjectiveScore() {
        return objectiveManager.getObjectiveScore();
    }

    /**
     * Get the list of the redeemed objectives
     *
     * @return the list of the redeemed objectives
     */
    public List<Objective> getRedeemedObjectives() {
        return objectiveManager.getRedeemedObjectives();
    }

    /**
     * get the list of achieved objectives
     *
     * @return the list of achieved objectives
     */
    public List<Objective> getAchievedObjectives() {
        return objectiveManager.getAchievedObjectives();
    }

    /**
     * for each objective, check if it is achieved
     *
     * @param board the board
     * @param botManager the bot manager
     */
    public void verifyObjectives(Board board, BotManager botManager) {
        objectiveManager.verifyObjectives(board, botManager);
    }

    /**
     * set an objective as not achieved
     *
     * @param objective the objective
     */
    public void setObjectiveNotAchieved(Objective objective) {
        this.objectiveManager.setObjectiveNotAchieved(objective);
    }

    /**
     * set an objective as achieved
     *
     * @param objective the objective
     */
    public void setObjectiveAchieved(Objective objective) {
        this.objectiveManager.setObjectiveAchieved(objective);
    }

    /**
     * redeem an objective
     *
     * @param objective the objective
     */
    public void redeemObjective(Objective objective) {
        this.objectiveManager.redeemObjective(objective, this);
    }

    /**
     * Returns the sum of the points of all the panda objectives
     *
     * @return the sum of the points of all the panda objectives
     */
    public int getPandaObjectiveScore() {
        return this.objectiveManager.getPandaObjectiveScore();
    }
}
