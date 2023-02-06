package com.takenoko.actions.improvement;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.ImprovementType;
import java.util.List;

/** Action to store an improvement in the inventory. */
@ActionAnnotation(ActionType.FORCED)
public class StoreImprovementAction implements Action {
    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        ImprovementType improvementType = board.peekImprovement();
        botManager.displayMessage(botManager.getName() + " stored improvement " + improvementType);
        botManager.getInventory().storeImprovement(improvementType);
        return new ActionResult(List.of(ApplyImprovementFromInventoryAction.class), 0);
    }
}
