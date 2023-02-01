package com.takenoko.shape;

import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;
import java.util.Objects;
import org.apache.commons.lang3.tuple.Pair;

/** Build a solid color Curve pattern */
public class Curve extends Pattern {
    private final TileColor color;

    public Curve(TileColor tileColor) {
        super(
                Pair.of(new PositionVector(0, 0, 0), new Tile(tileColor)),
                Pair.of(new PositionVector(1, 0, -1), new Tile(tileColor)),
                Pair.of(new PositionVector(2, -1, -1), new Tile(tileColor)));
        this.color = tileColor;
    }

    @Override
    public String toString() {
        return "Curve{" + color + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Curve curve = (Curve) o;

        return color == curve.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }
}
