package com.takenoko;

import com.takenoko.tile.Pond;
import com.takenoko.tile.Tile;
import com.takenoko.tile.TileType;
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
        this.tiles.put(new Vector(0, 0, 0), new Pond());
        this.availableTilePositions = new HashSet<>();
        updateAvailableTilePositions(new Vector(0, 0, 0));
        this.availableTiles = new ArrayList<>();
        this.availableTiles.add(new Tile(TileType.OTHER));
        this.availableTiles.add(new Tile(TileType.OTHER));
        this.availableTiles.add(new Tile(TileType.OTHER));
    }

    /**
     * Place a tile on the board. and update the available tiles and the available tile positions.
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

    /**
     * Update the available tile positions after placing a tile.
     *
     * @param tile the tile to remove
     */
    private void updateAvailableTiles(Tile tile) {
        availableTiles.remove(tile);
    }

    /**
     * Update the available tile positions after placing a tile.
     *
     * @param position the position of the tile to remove
     */
    private void updateAvailableTilePositions(Vector position) {
        availableTilePositions.remove(position);
        for (Vector neighbor : position.getNeighbors()) {
            if (isPositionAvailable(neighbor)) {
                availableTilePositions.add(neighbor);
            }
        }
    }

    /**
     * Check if a position is available. A position is available if: - it is not already occupied by
     * a tile - it has at least 2 neighbors or 1 neighbor is a pond
     *
     * @param position the position to check
     */
    private boolean isPositionAvailable(Vector position) {
        if (tiles.containsKey(position)) {
            return false;
        }
        int nbNeighbors = 0;
        for (Vector neighbor : position.getNeighbors()) {

            if (tiles.containsKey(neighbor)) {
                if (tiles.get(neighbor).getType() == TileType.POND) {
                    return true;
                }
                nbNeighbors++;
            }
        }
        return nbNeighbors >= 2;
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

    /**
     * Get the available tiles.
     *
     * @return the list of available tiles
     */
    public List<Tile> getAvailableTiles() {
        return availableTiles;
    }
}
