package com.takenoko.layers.tile;

import java.util.Objects;

/** Class Tile represents a tile in the game. A tile has a type {@link TileType}. */
public class Tile {
    private final TileType type;

    /**
     * Constructor of the class Tile. It creates a tile with the default type OTHER {@link
     * TileType}.
     */
    public Tile() {
        this.type = TileType.OTHER;
    }

    /**
     * Constructor of the class Tile. It creates a tile with the given type.
     *
     * @param type the type of the tile
     */
    public Tile(TileType type) {
        this.type = type;
    }

    /**
     * Get the type of the tile.
     *
     * @return the type of the tile
     */
    public TileType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return getType() == tile.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType());
    }

    public Tile copy() {
        return new Tile(this.getType());
    }
}
