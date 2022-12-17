package com.takenoko.bot;

import com.takenoko.actors.panda.MovePandaAction;
import com.takenoko.engine.Board;
import com.takenoko.layers.tile.PlaceTileAction;

public class TilePlacingAndPandaMovingBot implements Bot {
    @Override
    public Action chooseAction(Board board) {
        // check if the panda can move
        if (!board.getPanda().getPossibleMoves().isEmpty()) {
            // move the panda
            return new MovePandaAction(board.getPanda().getPossibleMoves().get(0));
        } else {
            // place a tile
            return new PlaceTileAction(
                    board.getAvailableTiles().get(0), board.getAvailableTilePositions().get(0));
        }
    }
}
