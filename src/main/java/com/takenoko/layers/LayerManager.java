package com.takenoko.layers;

import com.takenoko.engine.Board;
import com.takenoko.layers.bamboo.BambooLayer;
import com.takenoko.layers.tile.TileLayer;

public class LayerManager {
    private final TileLayer tileLayer;
    private final BambooLayer bambooLayer;

    public LayerManager(TileLayer tileLayer, BambooLayer bambooLayer) {
        this.tileLayer = tileLayer;
        this.bambooLayer = bambooLayer;
    }

    public LayerManager(Board board) {
        this.tileLayer = new TileLayer();
        this.bambooLayer = new BambooLayer(board);
    }

    public TileLayer getTileLayer() {
        return tileLayer;
    }

    public BambooLayer getBambooLayer() {
        return bambooLayer;
    }
}
