package com.takenoko.engine;

import com.takenoko.inventory.Inventory;
import com.takenoko.objective.BambooInInventoryObjective;
import com.takenoko.objective.Objective;
import java.util.Objects;

public class BotState { // DEFAULT VALUES
    private static final int DEFAULT_NUMBER_OF_ACTIONS = 2;
    private static final Objective DEFAULT_OBJECTIVE = new BambooInInventoryObjective(2);
    private final int numberOfActions;
    private Objective objective;

    public Objective getObjective() {
        return objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    private final Inventory inventory;

    public BotState(int numberOfActions, Objective objective, Inventory inventory) {
        this.numberOfActions = numberOfActions;
        this.objective = objective;
        this.inventory = inventory;
    }

    public BotState() {
        this(DEFAULT_NUMBER_OF_ACTIONS, DEFAULT_OBJECTIVE, new Inventory());
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
        return inventory.getBambooStack().getBambooCount();
    }

    /**
     * Return the bot inventory
     *
     * @return the bot inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    public void reset() {
        this.objective.reset();
        this.inventory.clear();
    }

    public BotState(BotState botState) {
        this.numberOfActions = botState.numberOfActions;
        this.objective = botState.objective.copy();
        this.inventory = botState.getInventory().copy();
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
}
