package com.takenoko;

import java.util.List;

public class Bot implements Playable {
    private final Objective objective;

    public Bot() {
        objective = new PlaceTileObjective(1);
    }

    public Objective getObjective() {
        return objective;
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
