package com.takenoko;

import java.util.List;

/**
 * The Playable interface will allow the class implementing it to interact with the game and play.
 */
public interface Playable {

    /**
     * This method chooses the tile to place on the board from the list of possible tiles.
     *
     * @param possibleTiles The list of possible tiles to place.
     * @return The chosen tile to place.
     */
    Tile chooseTileToPlace(List<Tile> possibleTiles);
}
