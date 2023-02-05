package com.takenoko.engine;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.inventory.Inventory;
import com.takenoko.objective.Objective;
import java.util.List;
import java.util.Objects;

/** This class is used to store the state of a bot. */
public class BotState {
    public static final int MAX_OBJECTIVES = 5;
    private final ObjectiveManager objectiveManager;
    private final ActionManager actionManager;
    private final Inventory inventory;

    public BotState(
            Inventory inventory, ObjectiveManager objectiveManager, ActionManager actionManager) {
        this.inventory = inventory;
        this.objectiveManager = objectiveManager;
        this.actionManager = actionManager;
    }

    public BotState() {
        this(new Inventory(), new ObjectiveManager(), new ActionManager());
    }

    /**
     * Copy constructor
     *
     * @param botState the state to copy
     */
    public BotState(BotState botState) {
        this.inventory = botState.getInventory().copy();
        this.actionManager = new ActionManager(botState.actionManager);
        this.objectiveManager = new ObjectiveManager(botState.objectiveManager);
    }

    // ------------------------------------------------------ //
    // ------------------- OBJECT METHODS ------------------- //
    // ------------------------------------------------------ //

    /** reset everything to the default values */
    public void reset() {
        this.objectiveManager.reset();
        this.actionManager.reset();
        this.inventory.clear();
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
        return getInventory().equals(botState.getInventory())
                && objectiveManager.equals(botState.objectiveManager)
                && actionManager.equals(botState.actionManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInventory(), objectiveManager, actionManager);
    }

    /**
     * update the objectives
     *
     * @param board the board
     */
    public void update(Board board) {
        this.objectiveManager.updateObjectives(board, this);
        this.actionManager.updateDefaultActions(board, this);
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
        this.actionManager.setNumberOfActions(numberOfActions);
    }

    /**
     * @return number of actions the bot can do in a turn
     */
    protected int getNumberOfActions() {
        return this.actionManager.getNumberOfActions();
    }

    /**
     * Return the list of available actions. If actions of FORCED type are available, only these
     * actions are returned else all available actions are returned.
     *
     * @return the list of available actions
     */
    public List<Class<? extends Action>> getAvailableActions() {
        return this.actionManager.getAvailableActions();
    }

    /**
     * add an action to the list of available actions
     *
     * @param action the action to add
     */
    public void addAvailableAction(Class<? extends Action> action) {
        this.actionManager.addAvailableAction(action);
    }

    /** add an action to the number of actions to plau this turn */
    public void addAction() {
        this.actionManager.addAction();
    }

    /**
     * update an action in available actions
     *
     * @param action the action to update
     * @param actionResult the result of the action
     */
    public void updateAvailableActions(Action action, ActionResult actionResult) {
        this.actionManager.updateAvailableActions(action, actionResult);
    }

    /**
     * reset the available actions
     *
     * @param board the board
     */
    public void resetAvailableActions(Board board) {
        this.actionManager.resetAvailableActions(board, this);
    }

    /**
     * get the list of already done actions
     *
     * @return the list of already done actions
     */
    public List<Class<? extends Action>> getAlreadyDoneActions() {
        return this.actionManager.getAlreadyDoneActions();
    }

    // ------------------------------------------------------ //
    // ------------ METHOD RELATED TO OBJECTIVES ------------ //
    // ------------------------------------------------------ //

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
     */
    public void verifyObjectives(Board board) {
        objectiveManager.verifyObjectives(board, this);
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
