package com.takenoko.actions;

import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.List;

@ActionAnnotation(ActionType.DEFAULT)
public class DrawTileAction implements Action {

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        board.drawTiles();
        return new ActionResult(List.of(PlaceTileAction.class), 0);
    }
}
