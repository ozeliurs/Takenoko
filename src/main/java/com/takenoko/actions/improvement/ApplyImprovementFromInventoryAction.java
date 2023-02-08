package com.takenoko.actions.improvement;

import com.takenoko.actions.ActionResult;
import com.takenoko.actions.DefaultAction;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionCanBePlayedMultipleTimesPerTurn;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.engine.BotState;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.vector.PositionVector;
import java.util.Objects;

/** Action to apply an improvement from the inventory. */
@ActionAnnotation(ActionType.DEFAULT)
@ActionCanBePlayedMultipleTimesPerTurn
public class ApplyImprovementFromInventoryAction extends ApplyImprovementAction
        implements DefaultAction {
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

    public static boolean canBePlayed(Board board, BotState botState) {
        return botState.getInventory().hasImprovement()
                && !board.getAvailableImprovementPositions().isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ApplyImprovementFromInventoryAction that = (ApplyImprovementFromInventoryAction) o;
        return improvementType == that.improvementType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), improvementType);
    }
}
