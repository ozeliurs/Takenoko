package com.takenoko.bot;

import com.takenoko.actions.*;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;

/** Simple bot that places a tile on the board. */
public class TilePlacingBot implements Bot {
    @Override
    public Action chooseAction(Board board, BotState botState) {
        if (botState.getAvailableActions().contains(ChooseIfApplyWeatherAction.class)) {
            return new ChooseIfApplyWeatherAction(false);
        } else if (botState.getAvailableActions().contains(DrawTileAction.class)) {
            // draw a tile
            return new DrawTileAction();
        } else if (botState.getAvailableActions().contains(PlaceTileAction.class)
                && !board.getAvailableTilePositions().isEmpty()
                && !board.peekTileDeck().isEmpty()) {
            // place a tile
            return new PlaceTileAction(
                    board.peekTileDeck().get(0), board.getAvailableTilePositions().get(0));
        } else if (botState.getAvailableActions().contains(MovePandaAction.class)) {
            // move the panda
            return new MovePandaAction(board.getPandaPossibleMoves().get(0));
        }
        throw new IllegalStateException("No action available");
    }
}
