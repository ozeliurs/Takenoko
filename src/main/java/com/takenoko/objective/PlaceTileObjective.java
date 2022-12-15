package com.takenoko.objective;

import com.takenoko.engine.Board;
import java.util.Objects;

public class PlaceTileObjective implements Objective {
    private static final ObjectiveTypes type = ObjectiveTypes.NUMBER_OF_TILES_PLACED;
    private final int numberOfTileToPlace;
    private ObjectiveState state;

    public PlaceTileObjective(int numberOfTileToPlace) {
        state = ObjectiveState.NOT_ACHIEVED;
        this.numberOfTileToPlace = numberOfTileToPlace;
    }

    /**
     * This method will be used each time the bot adds a tile, so we can track the progression
     * towards achieving the objective
     */
    public void verify(Board board) {
        // The -1 is there to take into account the pond tile which is there by default
        if (board.getTileLayer().getTiles().size() - 1 >= numberOfTileToPlace) {
            state = ObjectiveState.ACHIEVED;
        }
    }

    /**
     * This method is used to check whether the objective has been achieved yet
     *
     * @return true if the objective has been achieved, false otherwise
     */
    public boolean isAchieved() {
        return state == ObjectiveState.ACHIEVED;
    }

    @Override
    public String toString() {
        return "PlaceTileObjective{"
                + "numberOfTileToPlace="
                + numberOfTileToPlace
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

    public int getNumberOfTileToPlace() {
        return numberOfTileToPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceTileObjective objective = (PlaceTileObjective) o;
        return numberOfTileToPlace == objective.numberOfTileToPlace && state == objective.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfTileToPlace, state);
    }
}
