package com.takenoko.actions.irrigation;

import com.takenoko.actions.ActionResult;
import com.takenoko.actions.DefaultAction;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionCanBePlayedMultipleTimesPerTurn;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.engine.BotState;
import com.takenoko.layers.irrigation.EdgePosition;

@ActionAnnotation(ActionType.DEFAULT)
@ActionCanBePlayedMultipleTimesPerTurn
public class PlaceIrrigationFromInventoryAction implements DefaultAction {
    final EdgePosition positionVector;

    public PlaceIrrigationFromInventoryAction(EdgePosition positionVector) {
        this.positionVector = positionVector;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        botManager.displayMessage(
                botManager.getName()
                        + " placed irrigation at "
                        + positionVector
                        + " from inventory");
        botManager.getInventory().useIrrigationChannel();
        botManager.getSingleBotStatistics().incrementIrrigationsPlaced();
        board.placeIrrigation(positionVector);
        return new ActionResult(0);
    }

    public static boolean canBePlayed(Board board, BotState botState) {
        return botState.getInventory().getIrrigationChannelsCount() > 0
                && !board.getAvailableIrrigationPositions().isEmpty();
    }
}
