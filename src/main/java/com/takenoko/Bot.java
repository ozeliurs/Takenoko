package com.takenoko;

import java.util.List;

public class Bot implements Playable {
    private PlaceTileObjective placeTileObjective;

    public Bot() {
        placeTileObjective = new PlaceTileObjective(1);
    }

    public PlaceTileObjective getObjective() {
        return placeTileObjective;
    }

    /**
     * This method is used to assign an Objective to the bot
     *
     * @param goal
     */
    public void setObjective(int goal) {
        placeTileObjective.setAction(goal);
    }

    /**
     * This method return the chosen tile to place on the board.
     *
     * @param possibleTiles The list of possible tiles to place.
     * @return The chosen tile to place.
     */
    @Override
    public Tile chooseTileToPlace(List<Tile> possibleTiles) {
        return possibleTiles.get(0);
    }
}
