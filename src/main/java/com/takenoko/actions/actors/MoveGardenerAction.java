package com.takenoko.actions.actors;

import com.takenoko.actions.ActionResult;
import com.takenoko.actions.DefaultAction;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.engine.BotState;
import com.takenoko.layers.bamboo.BambooStack;
import com.takenoko.vector.PositionVector;

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

    public static boolean canBePlayed(Board board, BotState botState) {
        return !board.getTiles().isEmpty()
                && !botState.getAlreadyDoneActions().contains(MoveGardenerAction.class);
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

        BambooStack bambooStack = board.moveGardener(relativePositionVector);
        botManager.displayMessage(
                botManager.getName()
                        + " moved the gardener"
                        + " with "
                        + relativePositionVector
                        + " to position "
                        + board.getGardenerPosition());

        if (!bambooStack.isEmpty()) {
            botManager.displayMessage("Gardener planted one bamboo");
        } else {
            botManager.displayMessage("Gardener didn't plant any bamboo");
        }
        return new ActionResult(1);
    }
}
