package com.takenoko.layers;

import com.takenoko.layers.bamboo.BambooLayer;
import com.takenoko.layers.tile.TileLayer;

public class LayerManager {
    private final TileLayer tileLayer = new TileLayer();
    private final BambooLayer bambooLayer = new BambooLayer();

    public TileLayer getTileLayer() {
        return tileLayer;
    }

    public BambooLayer getBambooLayer() {
        return bambooLayer;
    }
}
