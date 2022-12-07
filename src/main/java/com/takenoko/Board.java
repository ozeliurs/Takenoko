package com.takenoko;

import java.util.ArrayList;
import java.util.List;

/** Board class. The board contains the tiles. */
public class Board {
    private final ArrayList<Tile> tiles = new ArrayList<>();

    /**
     * Place a tile on the board.
     *
     * @param tile the tile to add to the board
     */
    public void placeTile(Tile tile) {
        tiles.add(tile);
    }

    /**
     * Get the tiles on the board.
     *
     * @return the tiles on the board
     */
    public List<Tile> getTiles() {
        return tiles;
    }
}
