package com.takenoko.actions.irrigation;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.irrigation.EdgePosition;

/** Action to place an irrigation channel on the board. */
@ActionAnnotation(ActionType.FORCED)
public class PlaceIrrigationAction implements Action {
    final EdgePosition edgePosition;

    public PlaceIrrigationAction(EdgePosition edgePosition) {
        this.edgePosition = edgePosition;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        botManager.displayMessage(
                botManager.getName() + " placed an irrigation channel at " + edgePosition);
        botManager.getSingleBotStatistics().incrementIrrigationsPlaced();
        botManager.getSingleBotStatistics().updateActions(getClass().getSimpleName());
        board.placeIrrigation(edgePosition);
        return new ActionResult(1);
    }
}
