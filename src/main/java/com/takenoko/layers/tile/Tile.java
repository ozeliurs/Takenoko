package com.takenoko.layers.tile;

import java.util.Objects;
import java.util.Optional;

/** Class Tile represents a tile in the game. A tile has a type {@link TileType}. */
public class Tile {
    private final TileType type;
    private final TileColor color;
    private ImprovementType improvement;

    /**
     * Constructor of the class Tile. It creates a tile with the default type OTHER {@link
     * TileType}.
     */
    public Tile() {
        this(TileType.OTHER);
    }

    public Tile(TileColor color) {
        this(TileType.OTHER, null, color);
    }

    /**
     * Constructor of the class Tile. It creates a tile with the given type.
     *
     * @param type the type of the tile
     */
    public Tile(TileType type) {
        this(type, null, TileColor.PINK);
    }

    /**
     * Constructor of the class Tile. It creates a tile with the given type and improvement.
     *
     * @param type the type of the tile
     * @param color the color of the tile
     */
    public Tile(TileType type, ImprovementType improvement, TileColor color) {
        this.type = type;
        this.improvement = improvement;
        this.color = color;
    }

    /**
     * Copy Constructor
     *
     * @param tile the tile to copy
     */
    public Tile(Tile tile) {
        this.type = tile.type;
        this.improvement = tile.improvement;
        this.color = tile.color;
    }

    public Tile(ImprovementType improvementType) {
        this(TileType.OTHER, improvementType, TileColor.PINK);
    }

    public Tile(ImprovementType improvementType, TileColor tileColor) {
        this(TileType.OTHER, improvementType, tileColor);
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

    public TileColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;

        return type == tile.type
                && Objects.equals(improvement, tile.improvement)
                && color == tile.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getImprovement(), getColor());
    }

    public Tile copy() {
        return new Tile(this);
    }

    @Override
    public String toString() {
        return "Tile{" + "type=" + type + ", color=" + color + ", improvement=" + improvement + '}';
    }
}
