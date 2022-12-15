package com.takenoko.player;

import com.takenoko.actors.panda.MovePandaAction;
import com.takenoko.engine.Board;
import com.takenoko.layers.tile.PlaceTileAction;

public class TilePlacingAndPandaMovingBot implements Playable {
    @Override
    public Action chooseAction(Board board) {
        // check if the panda can move
        if (!board.getActorsManager().getPanda().getPossibleMoves().isEmpty()) {
            // move the panda
            return new MovePandaAction(
                    board.getActorsManager().getPanda().getPossibleMoves().get(0));
        } else {
            // place a tile
            return new PlaceTileAction(
                    board.getLayerManager().getTileLayer().getAvailableTiles().get(0),
                    board.getLayerManager().getTileLayer().getAvailableTilePositions().get(0));
        }
    }
}
