package com.takenoko.bot;

import com.takenoko.actions.*;
import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.tile.DrawTileAction;
import com.takenoko.actions.tile.PlaceTileAction;
import com.takenoko.actions.weather.ChooseIfApplyWeatherAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import org.apache.commons.lang3.NotImplementedException;

/**
 * This class is a bot that will place a tile if it can't move the panda, and move the panda when it
 * can.
 */
public class TilePlacingAndPandaMovingBot implements Bot {
    @Override
    public Action chooseAction(Board board, BotState botState) {
        if (botState.getAvailableActions().contains(ChooseIfApplyWeatherAction.class)) {
            return new ChooseIfApplyWeatherAction(false);
        }
        // check if the panda can move
        if (botState.getAvailableActions().contains(MovePandaAction.class)
                && !board.getPandaPossibleMoves().isEmpty()) {
            // move the panda
            return new MoveGardenerAction(board.getPandaPossibleMoves().get(0));
        } else if (botState.getAvailableActions().contains(DrawTileAction.class)) {
            // draw a tile
            return new DrawTileAction();
        } else if (botState.getAvailableActions().contains(PlaceTileAction.class)) {
            // place a tile
            return new PlaceTileAction(
                    board.peekTileDeck().get(0), board.getAvailableTilePositions().get(0));
        }
        throw new NotImplementedException("No action available");
    }
}
