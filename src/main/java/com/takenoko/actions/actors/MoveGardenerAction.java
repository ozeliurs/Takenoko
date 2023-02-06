package com.takenoko.actions.actors;

import com.takenoko.actions.ActionResult;
import com.takenoko.actions.DefaultAction;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.vector.PositionVector;
import java.util.Map;

/** Action to move a gardener. */
@ActionAnnotation(ActionType.DEFAULT)
public class MoveGardenerAction implements DefaultAction {
    private final PositionVector relativePositionVector;

    /**
     * Constructor for the MoveGardenerAction class.
     *
     * @param relativePositionVector the position vector to move the Gardener
     */
    public MoveGardenerAction(PositionVector relativePositionVector) {
        this.relativePositionVector = relativePositionVector;
    }

    public static boolean canBePlayed(Board board) {
        return !board.getTilesWithoutPond().isEmpty();
    }

    /**
     * Move the Gardener with a vector on the board and display a message.
     *
     * @param board the board
     * @param botManager the bot manager
     * @return the action result
     */
    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        // move the Gardener

        Map<PositionVector, LayerBambooStack> bambooStacks =
                board.moveGardener(relativePositionVector);
        botManager.displayMessage(
                botManager.getName()
                        + " moved the gardener"
                        + " with "
                        + relativePositionVector
                        + " to position "
                        + board.getGardenerPosition());

        if (bambooStacks.isEmpty()) {
            botManager.displayMessage("Gardener didn't plant any bamboo");
        }

        bambooStacks.forEach(
                (positionVector, layerBambooStack) ->
                        botManager.displayMessage(
                                "Gardener planted bamboo at "
                                        + positionVector
                                        + ", the stack is now "
                                        + layerBambooStack.getBambooCount()
                                        + "high"));

        return new ActionResult(1);
    }
}
