package com.takenoko.engine;

import com.takenoko.actors.ActorsManager;
import com.takenoko.layers.LayerManager;
import com.takenoko.layers.TileLayer;

/** Board class. The board contains the tiles. */
public class Board {
    private final ActorsManager actorsManager;
    private final LayerManager layerManager;

    /** Constructor for the Board class. */
    public Board() {
        this.layerManager = new LayerManager();
        this.actorsManager = new ActorsManager(this);
    }

    public TileLayer getTileLayer() {
        return layerManager.getTileLayer();
    }

    public ActorsManager getActorsManager() {
        return actorsManager;
    }
}
