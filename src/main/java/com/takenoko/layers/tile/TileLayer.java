package com.takenoko.layers.tile;

import com.takenoko.engine.Board;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.vector.PositionVector;
import com.takenoko.vector.Vector;
import java.util.*;

/** The TileLayer class is used to manage the tiles on the board. */
public class TileLayer {
    final HashMap<PositionVector, Tile> tiles;
    final HashSet<PositionVector> availableTilePositions;

    /** Create a new TileLayer. */
    public TileLayer() {
        tiles = new HashMap<>();
        availableTilePositions = new HashSet<>();
        availableTilePositions.add(new PositionVector(0, 0, 0));
        tiles.put(new PositionVector(0, 0, 0), new Pond());
        updateAvailableTilePositions(new PositionVector(0, 0, 0));
    }

    public TileLayer(TileLayer tileLayer) {
        tiles =
                tileLayer.tiles.keySet().stream()
                        .collect(
                                HashMap::new,
                                (m, k) -> m.put(k.copy(), tileLayer.tiles.get(k).copy()),
                                HashMap::putAll);
        availableTilePositions =
                tileLayer.availableTilePositions.stream()
                        .map(PositionVector::copy)
                        .collect(HashSet::new, HashSet::add, HashSet::addAll);
    }

    /**
     * Place a tile on the board. and update the available tiles and the available tile positions.
     *
     * @param tile the tile to add to the board
     * @param position the position of the tile
     * @param board the board
     * @return the LayerBambooStack at the position of the tile
     */
    public LayerBambooStack placeTile(Tile tile, PositionVector position, Board board) {
        if (tiles.containsKey(position)) {
            throw new IllegalArgumentException("Tile already present at this position");
        }
        if (!availableTilePositions.contains(position)) {
            throw new IllegalArgumentException("Tile position not available");
        }
        tiles.put(position, tile);
        board.chooseTileInTileDeck(tile);
        updateAvailableTilePositions(position);
        board.updateAvailableIrrigationChannelPositions(position);
        return board.getBambooAt(position);
    }

    /**
     * Update the available tile positions after placing a tile.
     *
     * @param position the position of the tile to remove
     */
    private void updateAvailableTilePositions(PositionVector position) {
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
    private boolean isPositionAvailable(PositionVector position) {
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
     * Get the positions where an improvement can be placed.
     *
     * @return the positions where an improvement can be placed
     */
    public List<PositionVector> getAvailableImprovementPositions(Board board) {
        return board.getTiles().keySet().stream()
                .filter(position -> board.getTileAt(position).getType() != TileType.POND)
                .filter(position -> board.getTileAt(position).getImprovement().isEmpty())
                .filter(position -> (board.getBambooAt(position)).isEmpty())
                .toList();
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
        return getTiles().containsKey(position);
    }

    /**
     * Return the tile at the given position.
     *
     * @param positionVector the position of the tile
     * @return the tile at the given position
     */
    public Tile getTileAt(PositionVector positionVector) {
        return tiles.get(positionVector);
    }

    public TileLayer copy() {
        return new TileLayer(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TileLayer tileLayer = (TileLayer) o;
        return getTiles().equals(tileLayer.getTiles())
                && getAvailableTilePositions().equals(tileLayer.getAvailableTilePositions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTiles(), getAvailableTilePositions());
    }

    public void applyImprovement(
            ImprovementType improvementType, PositionVector positionVector, Board board) {
        if (!board.getAvailableImprovementPositions().contains(positionVector)) {
            throw new IllegalStateException("Tile not available for improvement");
        }
        Tile tile = board.getTileAt(positionVector);
        tile.setImprovement(improvementType);
    }
}
