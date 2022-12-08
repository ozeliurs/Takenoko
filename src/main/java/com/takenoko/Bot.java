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
