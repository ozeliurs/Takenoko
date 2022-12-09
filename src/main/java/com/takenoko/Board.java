package com.takenoko;

import com.takenoko.vector.Vector;
import java.util.*;

/** Board class. The board contains the tiles. */
public class Board {
    private final HashMap<Vector, Tile> tiles;
    private final HashSet<Vector> availableTilePositions;
    private final ArrayList<Tile> availableTiles;

    /** Constructor for the Board class. Instantiate the tiles and the available tile positions. */
    public Board() {
        this.tiles = new HashMap<>();
        this.tiles.put(new Vector(0, 0, 0), new Tile(TileType.POND));
        this.availableTilePositions = new HashSet<>();
        this.availableTilePositions.addAll(new Vector(0, 0, 0).getNeighbors());
        this.availableTiles = new ArrayList<>();
        this.availableTiles.add(new Tile(TileType.OTHER));
        this.availableTiles.add(new Tile(TileType.OTHER));
    }

    /**
     * Place a tile on the board.
     *
     * @param tile the tile to add to the board
     * @param position the position of the tile
     */
    public void placeTile(Tile tile, Vector position) {
        if (tiles.containsKey(position)) {
            throw new IllegalArgumentException("Tile already present at this position");
        }
        if (!availableTilePositions.contains(position)) {
            throw new IllegalArgumentException("Tile position not available");
        }
        if (!availableTiles.contains(tile)) {
            throw new IllegalArgumentException("The tile is not available");
        }
        tiles.put(position, tile);
        updateAvailableTilePositions(position);
        updateAvailableTiles(tile);
    }

    private void updateAvailableTiles(Tile tile) {
        availableTiles.remove(tile);
    }

    private void updateAvailableTilePositions(Vector position) {
        availableTilePositions.remove(position);
    }

    /**
     * Get the tiles on the board.
     *
     * @return the tiles on the board
     */
    public Map<Vector, Tile> getTiles() {
        return tiles;
    }

    /**
     * Get the available tiles.
     *
     * @return the available tiles
     */
    public List<Vector> getAvailableTilePositions() {
        return availableTilePositions.stream().toList();
    }

    public List<Tile> getAvailableTiles() {
        return availableTiles;
    }
}
