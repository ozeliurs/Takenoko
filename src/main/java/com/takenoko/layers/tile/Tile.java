package com.takenoko.layers.tile;

import java.util.Objects;
import java.util.Optional;

/** Class Tile represents a tile in the game. A tile has a type {@link TileType}. */
public class Tile {
    private final TileType type;
    private ChipType chip;

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
     * Constructor of the class Tile. It creates a tile with the given type and chip.
     *
     * @param type the type of the tile
     */
    public Tile(TileType type, ChipType chip) {
        this.type = type;
        this.chip = chip;
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
     * Get the chip of the tile.
     *
     * @return the chip of the tile
     */
    public Optional<ChipType> getChip() {
        return Optional.ofNullable(chip);
    }

    /** Set the chip of the tile. If the chip is null, throw an exception. */
    public void setChip(ChipType chip) {
        if (this.chip != null) {
            throw new IllegalStateException("The tile already has a chip");
        }
        this.chip = chip;
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
