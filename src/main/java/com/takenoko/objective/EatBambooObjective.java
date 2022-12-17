package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.Objects;

public class EatBambooObjective implements Objective {
    private static final ObjectiveTypes type = ObjectiveTypes.NUMBER_OF_BAMBOOS_EATEN;
    private final int numberOfBamboosToEat;
    private ObjectiveState state;

    public EatBambooObjective(int numberOfBamboosToEat) {
        state = ObjectiveState.NOT_ACHIEVED;
        this.numberOfBamboosToEat = numberOfBamboosToEat;
    }

    public boolean isAchieved() {
        return state == ObjectiveState.ACHIEVED;
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

    public ObjectiveTypes getType() {
        return type;
    }

    public ObjectiveState getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EatBambooObjective objective = (EatBambooObjective) o;
        return numberOfBamboosToEat == objective.numberOfBamboosToEat && state == objective.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfBamboosToEat, type, state);
    }
}
