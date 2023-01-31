package com.takenoko.shape;

import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;
import org.apache.commons.lang3.tuple.Pair;

public class Triangle extends Pattern {
    public Triangle(TileColor tileColor) {
        super(
                Pair.of(new PositionVector(0, 0, 0), new Tile(tileColor)),
                Pair.of(new PositionVector(1, 0, -1), new Tile(tileColor)),
                Pair.of(new PositionVector(0, 1, -1), new Tile(tileColor)));
    }
}
