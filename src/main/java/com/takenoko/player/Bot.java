package com.takenoko.player;

import com.takenoko.engine.Board;
import com.takenoko.layers.tile.PlaceTileAction;

/** Simple bot that places a tile on the board. */
public class Bot implements Playable {
    @Override
    public Action chooseAction(Board board) {
        return new PlaceTileAction(
                board.getLayerManager().getTileLayer().getAvailableTiles().get(0),
                board.getLayerManager().getTileLayer().getAvailableTilePositions().get(0));
    }
}
