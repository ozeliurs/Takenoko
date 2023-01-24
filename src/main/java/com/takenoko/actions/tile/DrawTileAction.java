package com.takenoko.actions.tile;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
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
        board.drawTiles();
        return new ActionResult(List.of(PlaceTileAction.class), 0);
    }
}
