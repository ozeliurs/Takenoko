package com.takenoko.layers.bamboo;

import com.takenoko.engine.Board;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;

/** BambooLayer class. The bamboo layer contains the number of bamboo on each tile. */
public class BambooLayer {
    private final HashMap<PositionVector, LayerBambooStack> bamboo;
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
        if (positionVector.equals(new PositionVector(0, 0, 0))) {
            throw new IllegalArgumentException("The bamboo cannot be placed on the pond");
        }
        if (!board.isTile(positionVector)) {
            throw new IllegalArgumentException("The position is not on the board");
        }

        if (bamboo.containsKey(positionVector)) {
            bamboo.get(positionVector).growBamboo();
        } else {
            bamboo.put(positionVector, new LayerBambooStack(1));
        }
    }

    /**
     * Get the number of bamboo on a tile.
     *
     * @param positionVector the position of the tile
     * @return the number of bamboo on the tile
     */
    public LayerBambooStack getBambooAt(PositionVector positionVector) {
        if (board.isTile(positionVector)) {
            bamboo.computeIfAbsent(positionVector, k -> new LayerBambooStack(0));
            return bamboo.get(positionVector);
        } else {
            throw new IllegalArgumentException("The position is not a tile");
        }
    }

    /**
     * Remove bamboo from a tile.
     *
     * @param positionVector the position of the tile
     */
    public void removeBamboo(PositionVector positionVector) {
        if (getBambooAt(positionVector).getBambooCount() > 0) {
            bamboo.get(positionVector).eatBamboo();
        } else {
            throw new IllegalArgumentException("There is no bamboo on this tile");
        }
    }
}
