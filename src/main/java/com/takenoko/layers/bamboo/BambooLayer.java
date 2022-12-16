package com.takenoko.layers.bamboo;

import com.takenoko.engine.Board;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import java.util.Map;

public class BambooLayer {
    private final HashMap<PositionVector, Integer> bamboo;
    private final Board board;

    public BambooLayer(Board board) {
        bamboo = new HashMap<>();
        this.board = board;
    }

    void addBamboo(PositionVector positionVector) {
        bamboo.put(positionVector, bamboo.getOrDefault(positionVector, 1) + 1);
    }

    public int getBambooAt(PositionVector positionVector) {
        if (board.getLayerManager().getTileLayer().isTile(positionVector)) {
            return bamboo.getOrDefault(positionVector, 1);
        } else {
            throw new IllegalArgumentException("The position is not a tile");
        }
    }

    public Map<PositionVector, Integer> getBamboo() {
        return bamboo;
    }
}
