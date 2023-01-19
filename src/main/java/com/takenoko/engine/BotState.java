package com.takenoko.engine;

import com.takenoko.actions.Action;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.inventory.Inventory;
import com.takenoko.objective.BambooInInventoryObjective;
import com.takenoko.objective.Objective;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BotState { // DEFAULT VALUES
    private static final int DEFAULT_NUMBER_OF_ACTIONS = 2;
    private static final Objective DEFAULT_OBJECTIVE = new BambooInInventoryObjective(2);

    private int numberOfActions;
    private Objective objective;
    private final Inventory inventory;
    private List<Class<? extends Action>> availableActions;

    public BotState(
            int numberOfActions,
            Objective objective,
            Inventory inventory,
            List<Class<? extends Action>> availableActions) {
        this.numberOfActions = numberOfActions;
        this.objective = objective;
        this.inventory = inventory;
        this.availableActions = availableActions;
    }

    public BotState() {
        this(DEFAULT_NUMBER_OF_ACTIONS, DEFAULT_OBJECTIVE, new Inventory(), new ArrayList<>());
    }

    public BotState(BotState botState) {
        this.numberOfActions = botState.numberOfActions;
        this.objective = botState.objective.copy();
        this.inventory = botState.getInventory().copy();
        this.availableActions = new ArrayList<>(botState.availableActions);
    }

    public void setNumberOfActions(int numberOfActions) {
        this.numberOfActions = numberOfActions;
    }

    /**
     * Get the current Objective of the bot
     *
     * @return Objective
     */
    public Objective getObjective() {
        return objective;
    }

    /**
     * Set the current Objective of the bot
     *
     * @param objective the objective
     */
    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    /**
     * @return number of actions the bot can do in a turn
     */
    protected int getNumberOfActions() {
        return numberOfActions;
    }

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

    public void addAvailableAction(Class<? extends Action> action) {
        this.availableActions.add(action);
    }

    public void addAvailableActions(List<Class<? extends Action>> actions) {
        this.availableActions.addAll(actions);
    }

    public void addAction() {
        numberOfActions++;
    }

    public void reset() {
        this.objective.reset();
        this.inventory.clear();
    }

    public BotState copy() {
        return new BotState(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotState botState = (BotState) o;
        return getNumberOfActions() == botState.getNumberOfActions()
                && getObjective().equals(botState.getObjective())
                && getInventory().equals(botState.getInventory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumberOfActions(), getObjective(), getInventory());
    }

    public void clearForcedActions() {
        availableActions.removeIf(
                action ->
                        action.getAnnotation(ActionAnnotation.class).value() == ActionType.FORCED);
    }
}
