package com.takenoko;

public class Tile {
    private final TileType type;

    public Tile() {
        this.type = TileType.OTHER;
    }

    public Tile(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return type;
    }
}
