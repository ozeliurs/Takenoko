package com.takenoko.engine;

import com.takenoko.actors.panda.Panda;
import com.takenoko.layers.bamboo.BambooLayer;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileLayer;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Board class. The board contains the tiles. */
public class Board {
    private final TileLayer tileLayer;
    private final BambooLayer bambooLayer;
    private final Panda panda;

    /**
     * Constructor for the Board class.
     *
     * @param tileLayer the tile layer
     * @param bambooLayer the bamboo layer
     * @param panda the panda
     */
    public Board(TileLayer tileLayer, BambooLayer bambooLayer, Panda panda) {
        this.tileLayer = tileLayer;
        this.bambooLayer = bambooLayer;
        this.panda = panda;
    }

    /** Constructor for the Board class. */
    public Board() {
        this(new TileLayer(), new BambooLayer(), new Panda());
    }

    public Board(Board board) {
        this.tileLayer = board.tileLayer.copy();
        this.bambooLayer = board.bambooLayer.copy();
        this.panda = board.panda.copy();
    }

    /**
     * Return the list of available tiles.
     *
     * @return the list of available tiles
     */
    public List<Tile> getAvailableTiles() {
        return tileLayer.getAvailableTiles();
    }

    /**
     * Returns a copy of the hashmap of tiles.
     *
     * @return the map of tiles
     */
    public Map<PositionVector, Tile> getTiles() {
        return new HashMap<>(tileLayer.getTiles());
    }

    /**
     * Returns the available tile positions.
     *
     * @return the available tile positions
     */
    public List<PositionVector> getAvailableTilePositions() {
        return tileLayer.getAvailableTilePositions();
    }

    /**
     * Returns a copy of the hashmap of tiles without the pond.
     *
     * @return the map of tiles without the pond
     */
    public Map<PositionVector, Tile> getTilesWithoutPond() {
        return new HashMap<>(tileLayer.getTilesWithoutPond());
    }

    /**
     * Returns if the position is a tile.
     *
     * @param positionVector the position of the tile
     * @return if there is a tile at the position
     */
    public boolean isTile(PositionVector positionVector) {
        return tileLayer.isTile(positionVector);
    }

    /**
     * Returns the tile at the position.
     *
     * @param positionVector the position of the tile
     * @return the tile at the position
     */
    public Tile getTileAt(PositionVector positionVector) {
        return tileLayer.getTileAt(positionVector);
    }

    /**
     * Get the bamboo stack at the position.
     *
     * @param positionVector the position of the tile
     */
    public LayerBambooStack getBambooAt(PositionVector positionVector) {
        return bambooLayer.getBambooAt(positionVector, this);
    }

    /** Get the Panda */
    public Panda getPanda() {
        return panda;
    }

    /** Get the Position of the Panda */
    public PositionVector getPandaPosition() {
        return panda.getPosition();
    }

    public List<PositionVector> getPandaPossibleMoves() {
        return panda.getPossibleMoves(this);
    }

    public TileLayer getTileLayer() {
        return tileLayer;
    }

    public BambooLayer getBambooLayer() {
        return bambooLayer;
    }
}
