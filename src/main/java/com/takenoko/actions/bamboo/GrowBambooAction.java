package com.takenoko.actions.bamboo;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.vector.PositionVector;

@ActionAnnotation(ActionType.FORCED)
public class GrowBambooAction implements Action {

    PositionVector position;

    public GrowBambooAction(PositionVector position) {
        this.position = position;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        if (board.isBambooGrowableAt(position)) {
            board.growBamboo(position);
            botManager.displayMessage("Grew bamboo at " + position);
            return new ActionResult();
        }
        throw new IllegalStateException("Cannot grow bamboo at " + position);
    }
}
