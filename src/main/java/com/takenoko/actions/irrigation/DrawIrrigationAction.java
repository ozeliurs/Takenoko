package com.takenoko.actions.irrigation;

import com.takenoko.actions.ActionResult;
import com.takenoko.actions.DefaultAction;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.List;

@ActionAnnotation(ActionType.DEFAULT)
public class DrawIrrigationAction implements DefaultAction {

    public static boolean canBePlayed(Board board) {
        return board.hasIrrigation();
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        botManager.displayMessage(botManager.getName() + " drew irrigation");
        board.drawIrrigation();
        botManager.getInventory().collectIrrigationChannel();
        if (!board.getAvailableIrrigationPositions().isEmpty()) {
            return new ActionResult(
                    List.of(PlaceIrrigationAction.class, StoreIrrigationInInventoryAction.class),
                    0);
        }
        return new ActionResult(List.of(StoreIrrigationInInventoryAction.class), 0);
    }
}
