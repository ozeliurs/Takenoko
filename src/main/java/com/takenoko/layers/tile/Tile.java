package com.takenoko.layers.tile;

import java.util.Objects;
import java.util.Optional;

/** Class Tile represents a tile in the game. A tile has a type {@link TileType}. */
public class Tile {
    private final TileType type;
    private ImprovementType improvement;

    /**
     * Constructor of the class Tile. It creates a tile with the default type OTHER {@link
     * TileType}.
     */
    public Tile() {
        this(TileType.OTHER);
    }

    /**
     * Constructor of the class Tile. It creates a tile with the given type.
     *
     * @param type the type of the tile
     */
    public Tile(TileType type) {
        this(type, null);
    }

    /**
     * Constructor of the class Tile. It creates a tile with the given type and improvement.
     *
     * @param type the type of the tile
     */
    public Tile(TileType type, ImprovementType improvement) {
        this.type = type;
        this.improvement = improvement;
    }

    /**
     * Copy Constructor
     *
     * @param tile the tile to copy
     */
    public Tile(Tile tile) {
        this.type = tile.type;
        this.improvement = tile.improvement;
    }

    /**
     * Get the type of the tile.
     *
     * @return the type of the tile
     */
    public TileType getType() {
        return type;
    }

    /**
     * Get the improvement of the tile.
     *
     * @return the improvement of the tile
     */
    public Optional<ImprovementType> getImprovement() {
        return Optional.ofNullable(improvement);
    }

    /** Set the improvement of the tile. If the improvement is null, throw an exception. */
    public void setImprovement(ImprovementType improvement) {
        if (this.improvement != null) {
            throw new IllegalStateException("The tile already has an improvement");
        }
        this.improvement = improvement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;

        return type == tile.type && Objects.equals(improvement, tile.improvement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getImprovement());
    }

    public Tile copy() {
        return new Tile(this);
    }
}
