package com.takenoko.layers.bamboo;

import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import java.util.Map;

public class BambooLayer {
    private final HashMap<PositionVector, Integer> bamboo;

    public BambooLayer() {
        bamboo = new HashMap<>();
    }

    public void addBamboo(PositionVector positionVector) {
        bamboo.put(positionVector, bamboo.getOrDefault(positionVector, 0) + 1);
    }

    public int getBambooAt(PositionVector positionVector) {
        return bamboo.getOrDefault(positionVector, 0);
    }

    public Map<PositionVector, Integer> getBamboo() {
        return bamboo;
    }
}
