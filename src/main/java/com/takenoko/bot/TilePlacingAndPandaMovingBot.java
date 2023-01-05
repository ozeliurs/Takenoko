package com.takenoko.bot;

import com.takenoko.actors.panda.MovePandaAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.layers.tile.PlaceTileAction;

/**
 * This class is a bot that will place a tile if it can't move the panda, and move the panda when it
 * can.
 */
public class TilePlacingAndPandaMovingBot implements Bot {
    @Override
    public Action chooseAction(Board board, BotState botState) {
        // check if the panda can move
        if (board.getPandaPossibleMoves().isEmpty()) {
            // place a tile
            return new PlaceTileAction(
                    board.getAvailableTiles().get(0), board.getAvailableTilePositions().get(0));
        } else {
            // move the panda
            return new MovePandaAction(board.getPandaPossibleMoves().get(0));
        }
    }
}
