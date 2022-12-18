package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.Objects;

public class EatBambooObjective extends Objective {
    private final int numberOfBamboosToEat;

    public EatBambooObjective(int numberOfBamboosToEat) {
        super(ObjectiveTypes.NUMBER_OF_BAMBOOS_EATEN, ObjectiveState.NOT_ACHIEVED);
        this.numberOfBamboosToEat = numberOfBamboosToEat;
    }

    public void verify(Board board, BotManager botManager) {
        if ((botManager.getEatenBambooCounter()) >= numberOfBamboosToEat) {
            state = ObjectiveState.ACHIEVED;
        }
    }

    @Override
    public String toString() {
        return "EatBambooObjective{"
                + "numberOfBamboosToEat="
                + numberOfBamboosToEat
                + ", state="
                + state
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EatBambooObjective that = (EatBambooObjective) o;
        return numberOfBamboosToEat == that.numberOfBamboosToEat
                && getType() == that.getType()
                && getState() == that.getState();
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfBamboosToEat, getType(), getState());
    }
}
