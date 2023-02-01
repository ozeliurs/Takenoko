package com.takenoko.shape;

import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;
import org.apache.commons.lang3.tuple.Pair;

/** Build a solid color Curve pattern */
public class Curve extends Pattern {

    public Curve(TileColor tileColor) {
        super(
                Pair.of(new PositionVector(0, 0, 0), new Tile(tileColor)),
                Pair.of(new PositionVector(1, 0, -1), new Tile(tileColor)),
                Pair.of(new PositionVector(2, -1, -1), new Tile(tileColor)));
    }

    @Override
    public String toString() {

        return "Curve{" + this.getColorsString() + "}";
    }
}
