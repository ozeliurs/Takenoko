package com.takenoko;

import com.takenoko.vector.Vector;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

/**
 * The Playable interface will allow the class implementing it to interact with the game and play.
 */
public interface Playable {

    /**
     * This method chooses the tile to place on the board from the list of possible tiles.
     *
     * @param possibleTiles The list of possible tiles to place.
     * @param availableTilePositions The list of available tile positions.
     * @return A pair containing the position and the tile to place.
     */
    Pair<Vector, Tile> chooseTileToPlace(
            List<Tile> possibleTiles, List<Vector> availableTilePositions);
}
