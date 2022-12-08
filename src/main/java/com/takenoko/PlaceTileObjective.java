package com.takenoko;

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
        if (board.getTiles().size() >= numberOfTileToPlace) {
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
}
