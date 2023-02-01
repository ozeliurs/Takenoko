package com.takenoko.shape;

import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;
import java.util.Objects;
import org.apache.commons.lang3.tuple.Pair;

/** Build a solid color Line pattern */
public class Triangle extends Pattern {
    private final TileColor color;

    public Triangle(TileColor tileColor) {
        super(
                Pair.of(new PositionVector(0, 0, 0), new Tile(tileColor)),
                Pair.of(new PositionVector(1, 0, -1), new Tile(tileColor)),
                Pair.of(new PositionVector(0, 1, -1), new Tile(tileColor)));
        this.color = tileColor;
    }

    @Override
    public String toString() {
        return "Triangle{" + color + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Triangle triangle = (Triangle) o;

        return color == triangle.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }
}
