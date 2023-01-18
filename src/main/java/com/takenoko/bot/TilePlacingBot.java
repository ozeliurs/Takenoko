package com.takenoko.bot;

import com.takenoko.actions.Action;
import com.takenoko.actions.ChooseIfApplyWeatherAction;
import com.takenoko.actions.PlaceTileAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;

/** Simple bot that places a tile on the board. */
public class TilePlacingBot implements Bot {
    @Override
    public Action chooseAction(Board board, BotState botState) {
        if (botState.getAvailableActions().contains(ChooseIfApplyWeatherAction.class)) {
            return new ChooseIfApplyWeatherAction(false);
        }
        if (botState.getAvailableActions().contains(PlaceTileAction.class)) {
            return new PlaceTileAction(
                    board.getAvailableTiles().get(0), board.getAvailableTilePositions().get(0));
        }
        throw new IllegalStateException("No action available");
    }
}
