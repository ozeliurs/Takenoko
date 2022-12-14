package com.takenoko.layers;

import com.takenoko.actors.Panda;

/** Board class. The board contains the tiles. */
public class Board {
    private final Panda panda;
    private final TileLayer tileLayer = new TileLayer();

    /** Constructor for the Board class. Instantiate the tiles and the available tile positions. */
    public Board() {

        // PANDA RELATED
        this.panda = new Panda(this);
    }

    /**
     * Get the panda
     *
     * @return Panda
     */
    public Panda getPanda() {
        return panda;
    }

    public TileLayer getTileLayer() {
        return tileLayer;
    }
}
