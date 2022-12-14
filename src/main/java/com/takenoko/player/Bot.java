package com.takenoko.player;

import com.takenoko.tile.Tile;
import com.takenoko.vector.PositionVector;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class Bot implements Playable {
    /**
     * This method return the chosen tile to place on the board.
     *
     * @param availableTiles The list of available tiles to place.
     * @param availableTilePositions The list of available tile positions.
     * @return A pair containing the position and the tile to place.
     */
    @Override
    public Pair<PositionVector, Tile> chooseTileToPlace(
            List<Tile> availableTiles, List<PositionVector> availableTilePositions) {
        if (availableTiles.isEmpty()) {
            throw new IllegalArgumentException("No possible tiles");
        }
        return Pair.of(availableTilePositions.get(0), availableTiles.get(0));
    }
}
