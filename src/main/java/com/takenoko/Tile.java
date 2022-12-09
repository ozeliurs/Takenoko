package com.takenoko;

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
}
