package com.takenoko.bot;

import com.takenoko.actions.Action;
import com.takenoko.actions.ChooseIfApplyWeatherAction;
import com.takenoko.actions.MoveGardenerAction;
import com.takenoko.actions.PlaceTileAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import org.apache.commons.lang3.NotImplementedException;

/**
 * This class is a bot that will place a tile if it can't move the panda, and move the panda when it
 * can.
 */
public class TilePlacingAndGardenerMovingBot implements Bot {
    @Override
    public Action chooseAction(Board board, BotState botState) {
        if (botState.getAvailableActions().contains(ChooseIfApplyWeatherAction.class)) {
            return new ChooseIfApplyWeatherAction(false);
        }

        // check if the gardener can move
        if (botState.getAvailableActions().contains(MoveGardenerAction.class)
                && !board.getGardenerPossibleMoves().isEmpty()) {
            // Move the gardener
            return new MoveGardenerAction(board.getGardenerPossibleMoves().get(0));
        } else if (botState.getAvailableActions().contains(PlaceTileAction.class)) {
            // place a tile
            return new PlaceTileAction(
                    board.getAvailableTiles().get(0), board.getAvailableTilePositions().get(0));
        }
        throw new NotImplementedException("No action available");
    }
}
