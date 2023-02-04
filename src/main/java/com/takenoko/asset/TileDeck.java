package com.takenoko.asset;

import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/** The deck containing all the improvement chips you can place on the tiles. */
public class TileDeck extends ArrayList<Tile> {

    private final Random random;

    public TileDeck() {
        this(new SecureRandom());
    }

    public TileDeck(Random random) {
        this.random = random;

        // -------- GREEN TILES --------
        for (int i = 0; i < 6; i++) {
            this.add(new Tile(TileColor.GREEN));
        }
        this.add(new Tile(ImprovementType.ENCLOSURE, TileColor.GREEN));
        this.add(new Tile(ImprovementType.ENCLOSURE, TileColor.GREEN));
        this.add(new Tile(ImprovementType.FERTILIZER, TileColor.GREEN));
        this.add(new Tile(ImprovementType.WATERSHED, TileColor.GREEN));
        this.add(new Tile(ImprovementType.WATERSHED, TileColor.GREEN));

        // -------- PINK TILES --------
        for (int i = 0; i < 4; i++) {
            this.add(new Tile());
        }
        this.add(new Tile(ImprovementType.ENCLOSURE, TileColor.PINK));
        this.add(new Tile(ImprovementType.FERTILIZER, TileColor.PINK));
        this.add(new Tile(ImprovementType.WATERSHED, TileColor.PINK));

        // -------- YELLOW TILES --------
        for (int i = 0; i < 6; i++) {
            this.add(new Tile(TileColor.YELLOW));
        }
        this.add(new Tile(ImprovementType.ENCLOSURE, TileColor.YELLOW));
        this.add(new Tile(ImprovementType.FERTILIZER, TileColor.YELLOW));
        this.add(new Tile(ImprovementType.WATERSHED, TileColor.YELLOW));

        shuffle();
    }

    /**
     * Draw tiles from the deck.
     * @deprecated
     */
    @Deprecated(since = "04/02/22", forRemoval = true)
    public void draw() {
        // stays here for compatibility with the old code, but not needed
    }

    /**
     * Choose what tile to place on the board.
     *
     * @param tile the tile to place
     */
    public void choose(Tile tile) {
        // remove the tile from the deck and put the remaining tiles back at the end of the deck, do
        // not shuffle

        List<Tile> tiles = peek();
        if (!tiles.contains(tile)) {
            throw new IllegalArgumentException("This tile is not in the last drawn tiles.");
        }

        this.removeAll(tiles);
        tiles.remove(tile);
        this.addAll(tiles);
    }

    public List<Tile> peek() {
        return new ArrayList<>(this.stream().limit(3).toList());
    }

    private void shuffle() {
        for (int i = 0; i < this.size(); i++) {
            int j = random.nextInt(this.size());
            Tile temp = this.get(i);
            this.set(i, this.get(j));
            this.set(j, temp);
        }
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

    @Override
    public String toString() {
        return this.stream().map(Tile::toString).collect(Collectors.joining(", "));
    }
}
