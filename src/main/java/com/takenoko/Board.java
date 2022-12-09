package com.takenoko;

import java.util.ArrayList;
import java.util.List;

/** Board class. The board contains the tiles. */
public class Board {
    private final ArrayList<Tile> tiles;
    private final ArrayList<Tile> availableTiles;

    /** Constructor for the Board class. Instantiate the tiles and the available tiles. */
    public Board() {
        this.tiles = new ArrayList<>();
        Tile pond = new Tile();
        pond.setPond(true);
        this.tiles.add(pond);
        this.availableTiles = new ArrayList<>();
        this.availableTiles.add(new Tile());
    }

    /**
     * Place a tile on the board.
     *
     * @param tile the tile to add to the board
     */
    public void placeTile(Tile tile) {
        if (availableTiles.contains(tile)) {
            tiles.add(tile);
            availableTiles.remove(tile);
        } else {
            throw new IllegalArgumentException("The tile is not available.");
        }
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
     * Get the available tiles.
     *
     * @return the available tiles
     */
    public List<Tile> getAvailableTiles() {
        return availableTiles;
    }
}
