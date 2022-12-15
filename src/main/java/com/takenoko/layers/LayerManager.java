package com.takenoko.layers;

import com.takenoko.layers.tile.TileLayer;

public class LayerManager {
    private final TileLayer tileLayer = new TileLayer();

    public TileLayer getTileLayer() {
        return tileLayer;
    }
}
