package com.takenoko.actions.tile;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.EndGameAction;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.List;

/** Action to draw a Tile from the deck. */
@ActionAnnotation(ActionType.DEFAULT)
public class DrawTileAction implements Action {

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        if (board.isTileDeckEmpty()) {
            return new ActionResult(List.of(EndGameAction.class), 0);
        }

        board.drawTiles();
        botManager.displayMessage(botManager.getName() + " drew tiles" + board.peekTileDeck());
        return new ActionResult(List.of(PlaceTileAction.class), 0);
    }
}
