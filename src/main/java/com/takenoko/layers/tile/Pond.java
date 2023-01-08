package com.takenoko.layers.tile;

/** Class Pond represents a pond in the game. A pond has a type POND {@link TileType}. */
public class Pond extends Tile {
    public Pond() {
        super(TileType.POND);
    }

    @Override
    public Pond copy() {
        return new Pond();
    }
}
