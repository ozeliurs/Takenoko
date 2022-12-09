package com.takenoko;

public class Tile {
    private final TileType type;

    public Tile(TileType pond) {
        this.type = pond;
    }

    public TileType getType() {
        return type;
    }
}
