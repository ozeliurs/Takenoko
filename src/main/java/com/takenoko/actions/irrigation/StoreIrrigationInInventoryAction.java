package com.takenoko.actions.irrigation;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;

@ActionAnnotation(ActionType.FORCED)
public class StoreIrrigationInInventoryAction implements Action {
    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        botManager.displayMessage(
                botManager.getName() + " stored an irrigation channel in inventory");
        botManager.getInventory().collectIrrigationChannel();
        botManager.getSingleBotStatistics().updateActions(getClass().getSimpleName());
        return new ActionResult(1);
    }
}
