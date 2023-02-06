package com.takenoko.shape;

import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;
import org.apache.commons.lang3.tuple.Pair;

/** Build a solid color Line pattern */
public class Line extends Pattern {

    public Line(TileColor color) {
        super(
                Pair.of(new PositionVector(0, 0, 0), new Tile(color)),
                Pair.of(new PositionVector(1, 0, -1), new Tile(color)),
                Pair.of(new PositionVector(2, 0, -2), new Tile(color)));
    }

    @Override
    public String toString() {
        return "Line{" + this.getColorsString() + "}";
    }
}
