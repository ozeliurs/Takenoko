package com.takenoko.bot;

import com.takenoko.actions.Action;
import com.takenoko.actions.MoveGardenerAction;
import com.takenoko.actions.PlaceTileAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;

/**
 * This class is a bot that will place a tile if it can't move the panda, and move the panda when it
 * can.
 */
public class TilePlacingAndGardenerMovingBot implements Bot {
    @Override
    public Action chooseAction(Board board, BotState botState) {
        // check if the gardener can move
        if (board.getGardenerPossibleMoves().isEmpty()) {
            // place a tile
            return new PlaceTileAction(
                    board.getAvailableTiles().get(0), board.getAvailableTilePositions().get(0));
        } else {
            // move the panda
            return new MoveGardenerAction(board.getGardenerPossibleMoves().get(0));
        }
    }
}
