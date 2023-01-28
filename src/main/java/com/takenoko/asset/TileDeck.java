package com.takenoko.asset;

import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** The deck containing all the improvement chips you can place on the tiles. */
public class TileDeck extends ArrayList<Tile> {

    private final Random random;
    private final transient List<Tile> lastDrawnTiles;

    public TileDeck() {
        this(new SecureRandom());
    }

    public TileDeck(Random random) {
        this.random = random;

        for (int i = 0; i < 6; i++) { // Green Tiles
            this.add(new Tile(TileColor.GREEN));
        }
        this.add(new Tile(ImprovementType.ENCLOSURE, TileColor.GREEN));
        this.add(new Tile(ImprovementType.ENCLOSURE, TileColor.GREEN));
        this.add(new Tile(ImprovementType.FERTILIZER, TileColor.GREEN));

        for (int i = 0; i < 4; i++) { // Pink Tiles
            this.add(new Tile());
        }
        this.add(new Tile(ImprovementType.ENCLOSURE, TileColor.PINK));
        this.add(new Tile(ImprovementType.FERTILIZER, TileColor.PINK));

        for (int i = 0; i < 6; i++) { // Yellow Tiles
            this.add(new Tile(TileColor.YELLOW));
        }
        this.add(new Tile(ImprovementType.ENCLOSURE, TileColor.YELLOW));
        this.add(new Tile(ImprovementType.FERTILIZER, TileColor.YELLOW));
        lastDrawnTiles = new ArrayList<>();
    }

    /** Draw tiles from the deck. */
    public void draw() {
        lastDrawnTiles.clear();
        for (int i = 0; i < 3; i++) {
            lastDrawnTiles.add(this.get(random.nextInt(this.size())));
        }
    }

    /**
     * Choose what tile to place on the board.
     *
     * @param tile the tile to place
     */
    public void choose(Tile tile) {
        if (lastDrawnTiles.contains(tile)) {
            this.remove(tile);
            return;
        }
        throw new IllegalArgumentException("This tile is not in the last drawn tiles.");
    }

    public List<Tile> peek() {
        return new ArrayList<>(lastDrawnTiles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TileDeck)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
