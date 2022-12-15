package com.takenoko.engine;

import com.takenoko.actors.ActorsManager;
import com.takenoko.layers.LayerManager;

/** Board class. The board contains the tiles. */
public class Board {
    private final ActorsManager actorsManager;
    private final LayerManager layerManager;

    public Board(ActorsManager actorsManager, LayerManager layerManager) {
        this.actorsManager = actorsManager;
        this.layerManager = layerManager;
    }

    /** Constructor for the Board class. */
    public Board() {
        this.actorsManager = new ActorsManager(this);
        this.layerManager = new LayerManager();
    }

    public ActorsManager getActorsManager() {
        return actorsManager;
    }

    public LayerManager getLayerManager() {
        return layerManager;
    }
}
