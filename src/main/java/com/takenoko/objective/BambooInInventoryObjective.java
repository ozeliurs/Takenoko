package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.Objects;

public class BambooInInventoryObjective extends Objective {
    private final int targetBambooInInventory;

    public BambooInInventoryObjective(int targetBambooInInventory) {
        super(ObjectiveTypes.NUMBER_OF_BAMBOOS_EATEN, ObjectiveState.NOT_ACHIEVED);
        this.targetBambooInInventory = targetBambooInInventory;
    }

    public void verify(Board board, BotManager botManager) {
        if ((botManager.getEatenBambooCounter()) >= targetBambooInInventory) {
            state = ObjectiveState.ACHIEVED;
        }
    }

    @Override
    public String toString() {
        return "BambooInInventoryObjective{"
                + "targetBambooInInventory="
                + targetBambooInInventory
                + ", state="
                + state
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BambooInInventoryObjective that = (BambooInInventoryObjective) o;
        return targetBambooInInventory == that.targetBambooInInventory
                && getType() == that.getType()
                && getState() == that.getState();
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetBambooInInventory, getType(), getState());
    }
}
