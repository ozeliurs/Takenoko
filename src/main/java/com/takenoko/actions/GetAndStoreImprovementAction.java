package com.takenoko.actions;

import com.takenoko.actions.annotations.ForcedAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.ImprovementType;

public class GetAndStoreImprovementAction implements ForcedAction {
    private final ImprovementType improvementType;

    /**
     * Constructor for the GetAndStoreImprovementAction class.
     *
     * @param improvementType the improvement type
     */
    public GetAndStoreImprovementAction(ImprovementType improvementType) {
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
        return new ActionResult(1);
    }
}
