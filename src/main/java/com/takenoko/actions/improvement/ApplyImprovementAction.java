package com.takenoko.actions.improvement;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.vector.PositionVector;

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
        if (!board.hasImprovementInDeck(improvementType)) {
            throw new IllegalStateException("Improvement not in deck");
        }
        board.applyImprovement(improvementType, positionVector);

        return new ActionResult(0);
    }
}
