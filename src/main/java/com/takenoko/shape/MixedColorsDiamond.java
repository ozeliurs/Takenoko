package com.takenoko.shape;

import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.tuple.Pair;

/** Build a mixed color Diamond pattern */
public class MixedColorsDiamond extends Pattern {

    private final TileColor color;

    public MixedColorsDiamond(TileColor tileColor) {
        super(
                switch (tileColor) {
                    case PINK -> List.of(
                            Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.PINK)),
                            Pair.of(new PositionVector(1, 0, -1), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(0, 1, -1), new Tile(TileColor.PINK)),
                            Pair.of(new PositionVector(-1, 1, 0), new Tile(TileColor.YELLOW)));
                    case GREEN -> List.of(
                            Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.GREEN)),
                            Pair.of(new PositionVector(1, 0, -1), new Tile(TileColor.PINK)),
                            Pair.of(new PositionVector(0, 1, -1), new Tile(TileColor.GREEN)),
                            Pair.of(new PositionVector(-1, 1, 0), new Tile(TileColor.PINK)));
                    case YELLOW -> List.of(
                            Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(1, 0, -1), new Tile(TileColor.GREEN)),
                            Pair.of(new PositionVector(0, 1, -1), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(-1, 1, 0), new Tile(TileColor.GREEN)));
                    default -> throw new IllegalArgumentException("Unexpected value: " + tileColor);
                });
        this.color = tileColor;
    }

    @Override
    public String toString() {
        switch (color) {
            case PINK -> {
                return "MixedColorsDiamond{PINK, YELLOW}";
            }
            case GREEN -> {
                return "MixedColorsDiamond{GREEN, PINK}";
            }
            case YELLOW -> {
                return "MixedColorsDiamond{YELLOW, GREEN}";
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + color);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MixedColorsDiamond that = (MixedColorsDiamond) o;

        return color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }
}
