package com.takenoko.bot;

import com.takenoko.engine.Board;
import com.takenoko.layers.tile.PlaceTileAction;

/** Simple bot that places a tile on the board. */
public class TilePlacingBot implements Bot {
    @Override
    public Action chooseAction(Board board) {
        return new PlaceTileAction(
                board.getAvailableTiles().get(0), board.getAvailableTilePositions().get(0));
    }
}
