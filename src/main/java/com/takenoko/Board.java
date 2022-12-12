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

    /** Constructor for the Board class. Instantiate the tiles and the available tile positions. */
    public Board() {
        this.tiles = new HashMap<>();
        this.tiles.put(new Vector(0, 0, 0), new Pond());
        this.availableTilePositions = new HashSet<>();
        updateAvailableTilePositions(new Vector(0, 0, 0));
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
        tiles.put(position, tile);
        updateAvailableTilePositions(position);
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
        // This list only contains one new tile for now
        // It will then be used when we have a specific number of tiles
        List<Tile> availableTiles = new ArrayList<>();
        availableTiles.add(new Tile());
        return availableTiles;
    }

    /**
     * Get the tile placed on the board but without the pond.
     *
     * @return the tile placed on the board but without the pond.
     */
    public Map<Vector, Tile> getTilesWithoutPond() {
        Map<Vector, Tile> filteredMap = new HashMap<>(tiles);
        filteredMap.remove(new Vector(0, 0, 0));
        return filteredMap;
    }
}
