package com.takenoko;

import java.util.ArrayList;
import java.util.List;

/** Board class. The board contains the tiles. */
public class Board {
    private final ArrayList<Tile> tiles = new ArrayList<>();
    private int availableTileNumber = 1;

    /**
     * Place a tile on the board.
     *
     * @param tile the tile to add to the board
     */
    public void placeTile(Tile tile) {
        if (availableTileNumber == 0) {
            throw new IllegalStateException("There is no more available tile.");
        }

        tiles.add(tile);
        availableTileNumber--;
    }

    /**
     * Get the tiles on the board.
     *
     * @return the tiles on the board
     */
    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     * Get the number of available tiles.
     *
     * @return int representing the number of available tiles
     */
    public int getAvailableTileNumber() {
        return availableTileNumber;
    }
}
