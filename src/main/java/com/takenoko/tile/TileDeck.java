package com.takenoko.tile;

import java.util.ArrayList;
import java.util.List;

public class TileDeck {
    private final ArrayList<Tile> tiles;

    public TileDeck() {
        tiles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tiles.add(new Tile());
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void take(Tile tile) {
        if (!tiles.contains(tile)) {
            throw new IllegalArgumentException("Tile not in the deck");
        }
        tiles.remove(tile);
    }
}
