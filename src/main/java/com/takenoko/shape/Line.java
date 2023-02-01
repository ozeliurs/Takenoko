package com.takenoko.shape;

import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;
import java.util.Objects;
import org.apache.commons.lang3.tuple.Pair;

/** Build a solid color Line pattern */
public class Line extends Pattern {
    private final TileColor color;

    public Line(TileColor color) {
        super(
                Pair.of(new PositionVector(0, 0, 0), new Tile(color)),
                Pair.of(new PositionVector(1, 0, -1), new Tile(color)),
                Pair.of(new PositionVector(2, 0, -2), new Tile(color)));
        this.color = color;
    }

    @Override
    public String toString() {
        return "Line{" + color + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Line line = (Line) o;

        return color == line.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }
}
