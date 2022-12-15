package com.takenoko.layers.tile;

import com.takenoko.tile.Pond;
import com.takenoko.tile.Tile;
import com.takenoko.tile.TileType;
import com.takenoko.vector.PositionVector;
import com.takenoko.vector.Vector;
import java.util.*;

public class TileLayer {
    final HashMap<PositionVector, Tile> tiles;
    final HashSet<PositionVector> availableTilePositions;

    public TileLayer() {
        tiles = new HashMap<>();
        availableTilePositions = new HashSet<>();
        availableTilePositions.add(new PositionVector(0, 0, 0));
        placeTile(new Pond(), new PositionVector(0, 0, 0));
    }

    /**
     * Place a tile on the board. and update the available tiles and the available tile positions.
     *
     * @param tile the tile to add to the board
     * @param position the position of the tile
     */
    public void placeTile(Tile tile, PositionVector position) {
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
    void updateAvailableTilePositions(PositionVector position) {
        availableTilePositions.remove(position);
        for (Vector neighbor : position.getNeighbors()) {
            if (isPositionAvailable(neighbor.toPositionVector())) {
                availableTilePositions.add(neighbor.toPositionVector());
            }
        }
    }

    /**
     * Check if a position is available. A position is available if: - it is not already occupied by
     * a tile - it has at least 2 neighbors or 1 neighbor is a pond
     *
     * @param position the position to check
     */
    boolean isPositionAvailable(PositionVector position) {
        if (isTile(position)) {
            return false;
        }
        int nbNeighbors = 0;
        for (Vector neighbor : position.getNeighbors()) {

            if (isTile(neighbor.toPositionVector())) {
                if (tiles.get(neighbor.toPositionVector()).getType() == TileType.POND) {
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
    public Map<PositionVector, Tile> getTiles() {
        return tiles;
    }

    /**
     * Get the available tiles.
     *
     * @return the available tiles
     */
    public List<PositionVector> getAvailableTilePositions() {
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
    public Map<PositionVector, Tile> getTilesWithoutPond() {
        Map<PositionVector, Tile> filteredMap = new HashMap<>(tiles);
        filteredMap.remove(new PositionVector(0, 0, 0));
        return filteredMap;
    }

    /**
     * Check if there is a tile at the given position.
     *
     * @param position the position of the tile
     * @return if there is a tile at the position
     */
    public boolean isTile(PositionVector position) {
        return tiles.containsKey(position);
    }
}
