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
    protected PositionVector positionVector;

    public ApplyImprovementAction(PositionVector positionVector) {
        this.positionVector = positionVector;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        ImprovementType improvementType = board.peekImprovement();
        board.applyImprovement(improvementType, positionVector);
        botManager.displayMessage(
                botManager.getName()
                        + " applied improvement "
                        + improvementType
                        + " at "
                        + positionVector);
        return new ActionResult(0);
    }
}
