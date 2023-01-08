package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.Objects;

public class PlaceTileObjective extends Objective {
    private final int numberOfTileToPlace;

    public PlaceTileObjective(int numberOfTileToPlace) {
        super(ObjectiveTypes.NUMBER_OF_TILES_PLACED, ObjectiveState.NOT_ACHIEVED);
        this.numberOfTileToPlace = numberOfTileToPlace;
    }

    /**
     * This method will be used each time the bot adds a tile, so we can track the progression
     * towards achieving the objective
     */
    public void verify(Board board, BotManager botManager) {
        // The -1 is there to take into account the pond tile which is there by default
        if (board.getTiles().size() - 1 >= numberOfTileToPlace) {
            state = ObjectiveState.ACHIEVED;
        }
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
        return Objects.hash(numberOfTileToPlace, getType(), getState());
    }

    @Override
    public void reset() {
        state = ObjectiveState.NOT_ACHIEVED;
    }

    @Override
    public Objective copy() {
        return new PlaceTileObjective(numberOfTileToPlace);
    }
}
