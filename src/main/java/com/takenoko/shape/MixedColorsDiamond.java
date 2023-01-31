package com.takenoko.shape;

import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class MixedColorsDiamond extends Pattern {

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
    }
}
