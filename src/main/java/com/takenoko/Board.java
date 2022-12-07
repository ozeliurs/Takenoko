package com.takenoko;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final ArrayList<Tile> tiles = new ArrayList<>();

    public void placeTile(Tile tile) {
        tiles.add(tile);
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}
