package com.takenoko.layers.bamboo;

import com.takenoko.engine.Board;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import java.util.Map;

/** BambooLayer class. The bamboo layer contains the number of bamboo on each tile. */
public class BambooLayer {
    private final HashMap<PositionVector, Integer> bamboo;
    private final Board board;

    /**
     * Constructor for the BambooLayer class.
     *
     * @param board the board
     */
    public BambooLayer(Board board) {
        bamboo = new HashMap<>();
        this.board = board;
    }

    /**
     * Add bamboo to a tile. By default, the number of bamboo is 1 if the tile is irrigated.
     *
     * @param positionVector the position of the tile
     */
    void addBamboo(PositionVector positionVector) {
        bamboo.put(positionVector, bamboo.getOrDefault(positionVector, 1) + 1);
    }

    /**
     * Get the number of bamboo on a tile.
     *
     * @param positionVector the position of the tile
     * @return the number of bamboo on the tile
     */
    public int getBambooAt(PositionVector positionVector) {
        if (board.getLayerManager().getTileLayer().isTile(positionVector)) {
            return bamboo.getOrDefault(positionVector, 1);
        } else {
            throw new IllegalArgumentException("The position is not a tile");
        }
    }

    /**
     * Get a copy of the hashmap of bamboo.
     *
     * @return the bamboo
     */
    public Map<PositionVector, Integer> getBamboo() {
        return new HashMap<>(bamboo);
    }
}
