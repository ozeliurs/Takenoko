package com.takenoko.actions;

import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.vector.PositionVector;

@ActionAnnotation(ActionType.FORCED)
public class ApplyImprovementAction implements Action {
    ImprovementType improvementType;
    PositionVector positionVector;

    public ApplyImprovementAction(ImprovementType improvementType, PositionVector positionVector) {
        this.improvementType = improvementType;
        this.positionVector = positionVector;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        if (!botManager.getInventory().hasImprovement(improvementType)) {
            throw new IllegalStateException("Improvement not in inventory");
        }
        board.applyImprovement(improvementType, positionVector);

        return new ActionResult(0);
    }
}
