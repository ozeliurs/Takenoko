package com.takenoko.actions;

import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.actions.annotations.PersistentApplyImprovementAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.ImprovementType;
import java.util.List;

@ActionAnnotation(ActionType.FORCED)
public class StoreImprovementAction implements Action {
    private final ImprovementType improvementType;

    /**
     * Constructor for the StoreImprovementAction class.
     *
     * @param improvementType the improvement type
     */
    public StoreImprovementAction(ImprovementType improvementType) {
        this.improvementType = improvementType;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        if (!board.hasImprovementInDeck(improvementType)) {
            botManager.displayMessage("No more " + improvementType + " in the deck");
            return new ActionResult(0);
        }

        board.drawImprovement(improvementType);
        botManager.displayMessage(botManager.getName() + " got an improvement");
        botManager.getInventory().storeImprovement(improvementType);
        return new ActionResult(List.of(PersistentApplyImprovementAction.class), 1);
    }
}
