package com.takenoko.player;

import com.takenoko.engine.Board;
import com.takenoko.layers.tile.PlaceTileAction;

public class Bot implements Playable {
    @Override
    public Action chooseAction(Board board) {
        return new PlaceTileAction(
                board.getTileLayer().getAvailableTiles().get(0),
                board.getTileLayer().getAvailableTilePositions().get(0));
    }
}
