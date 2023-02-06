package com.takenoko.actions.irrigation;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.irrigation.EdgePosition;
import java.util.Objects;

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
        board.placeIrrigation(edgePosition);
        return new ActionResult(1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceIrrigationAction that = (PlaceIrrigationAction) o;
        return Objects.equals(edgePosition, that.edgePosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(edgePosition);
    }
}
