package com.takenoko.actions.improvement;

import com.takenoko.actions.ActionResult;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.vector.PositionVector;

/** Action to apply an improvement from the inventory. */
@ActionAnnotation(ActionType.PERSISTENT)
public class ApplyImprovementFromInventoryAction extends ApplyImprovementAction {
    private final ImprovementType improvementType;

    public ApplyImprovementFromInventoryAction(
            ImprovementType improvementType, PositionVector positionVector) {
        super(positionVector);
        this.improvementType = improvementType;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        botManager.displayMessage(
                botManager.getName()
                        + " applied improvement "
                        + improvementType
                        + " at "
                        + positionVector
                        + " from inventory");
        botManager.getInventory().useImprovement(improvementType);
        board.applyImprovement(improvementType, positionVector);

        return new ActionResult(0);
    }
}
