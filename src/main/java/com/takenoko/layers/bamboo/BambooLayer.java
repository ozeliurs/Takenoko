package com.takenoko.layers.bamboo;

import com.takenoko.engine.Board;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import java.util.Objects;

/** BambooLayer class. The bamboo layer contains the number of bamboo on each tile. */
public class BambooLayer {
    private final HashMap<PositionVector, LayerBambooStack> bamboo;

    /** Constructor for the BambooLayer class. */
    public BambooLayer() {
        bamboo = new HashMap<>();
    }

    public BambooLayer(BambooLayer bambooLayer) {
        this();
        for (PositionVector positionVector : bambooLayer.bamboo.keySet()) {
            bamboo.put(positionVector, bambooLayer.bamboo.get(positionVector).copy());
        }
    }

    /**
     * Grow bamboo on a tile. By default, the number of bamboo is 1 if the tile is irrigated.
     *
     * @param positionVector the position of the tile
     * @param board the board
     */
    public void growBamboo(PositionVector positionVector, Board board) {
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
     * @param board the board
     * @return the number of bamboo on the tile
     */
    public LayerBambooStack getBambooAt(PositionVector positionVector, Board board) {
        if (board.isTile(positionVector)) {
            bamboo.computeIfAbsent(positionVector, k -> new LayerBambooStack(0));
            return bamboo.get(positionVector);
        } else {
            throw new IllegalArgumentException("The position is not a tile");
        }
    }

    /**
     * Eat a bamboo from a tile.
     *
     * @param positionVector the position of the tile
     * @param board the board
     */
    public void eatBamboo(PositionVector positionVector, Board board) {
        if (!board.getBambooAt(positionVector).isEmpty()) {
            bamboo.get(positionVector).eatBamboo();
        } else {
            throw new IllegalArgumentException("There is no bamboo on this tile");
        }
    }

    public BambooLayer copy() {
        return new BambooLayer(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BambooLayer that = (BambooLayer) o;
        return bamboo.equals(that.bamboo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bamboo);
    }
}
