package com.takenoko.engine;

import com.takenoko.actors.ActorsManager;
import com.takenoko.actors.panda.Panda;
import com.takenoko.layers.LayerManager;
import com.takenoko.layers.bamboo.BambooStack;
import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Board class. The board contains the tiles. */
public class Board {
    private final ActorsManager actorsManager;
    private final LayerManager layerManager;

    public Board(ActorsManager actorsManager, LayerManager layerManager) {
        this.actorsManager = actorsManager;
        this.layerManager = layerManager;
    }

    /** Constructor for the Board class. */
    public Board() {
        this.layerManager = new LayerManager(this);
        this.actorsManager = new ActorsManager(this);
    }

    public ActorsManager getActorsManager() {
        return actorsManager;
    }

    public LayerManager getLayerManager() {
        return layerManager;
    }

    /**
     * Return the list of available tiles.
     *
     * @return the list of available tiles
     */
    public List<Tile> getAvailableTiles() {
        return layerManager.getTileLayer().getAvailableTiles();
    }

    /**
     * Returns a copy of the hashmap of tiles.
     *
     * @return the map of tiles
     */
    public Map<PositionVector, Tile> getTiles() {
        return new HashMap<>(layerManager.getTileLayer().getTiles());
    }

    /**
     * Returns the available tile positions.
     *
     * @return the available tile positions
     */
    public List<PositionVector> getAvailableTilePositions() {
        return layerManager.getTileLayer().getAvailableTilePositions();
    }

    /**
     * Returns a copy of the hashmap of tiles without the pond.
     *
     * @return the map of tiles without the pond
     */
    public Map<PositionVector, Tile> getTilesWithoutPond() {
        return new HashMap<>(layerManager.getTileLayer().getTilesWithoutPond());
    }

    /**
     * Returns if the position is a tile.
     *
     * @param positionVector the position of the tile
     * @return if there is a tile at the position
     */
    public boolean isTile(PositionVector positionVector) {
        return layerManager.getTileLayer().isTile(positionVector);
    }

    /**
     * Returns the tile at the position.
     *
     * @param positionVector the position of the tile
     * @return the tile at the position
     */
    public Tile getTileAt(PositionVector positionVector) {
        return layerManager.getTileLayer().getTileAt(positionVector);
    }

    /**
     * Get the bamboo stack at the position.
     *
     * @param positionVector the position of the tile
     */
    public BambooStack getBambooAt(PositionVector positionVector) {
        return layerManager.getBambooLayer().getBambooAt(positionVector);
    }

    /** Get a copy of the hashmap of bamboo. */
    public Map<PositionVector, BambooStack> getBamboo() {
        return new HashMap<>(layerManager.getBambooLayer().getBamboo());
    }

    /** Get the Panda */
    public Panda getPanda() {
        return actorsManager.getPanda();
    }
}
