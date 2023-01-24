package com.takenoko.actions.improvement;

import com.takenoko.actions.ActionResult;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.vector.PositionVector;

@ActionAnnotation(ActionType.PERSISTENT)
public class ApplyImprovementFromInventoryAction extends ApplyImprovementAction {
    public ApplyImprovementFromInventoryAction(
            ImprovementType improvementType, PositionVector positionVector) {
        super(improvementType, positionVector);
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
