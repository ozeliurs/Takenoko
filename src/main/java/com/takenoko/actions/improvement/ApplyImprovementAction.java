package com.takenoko.actions.improvement;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.vector.PositionVector;

/**
 * Action to draw an improvement from the inventory. Throws an exception if the improvement is not
 * available.
 */
@ActionAnnotation(ActionType.FORCED)
public class ApplyImprovementAction implements Action {
    protected ImprovementType improvementType;
    protected PositionVector positionVector;

    public ApplyImprovementAction(ImprovementType improvementType, PositionVector positionVector) {
        this.improvementType = improvementType;
        this.positionVector = positionVector;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        board.applyImprovement(improvementType, positionVector);
        return new ActionResult(0);
    }
}
