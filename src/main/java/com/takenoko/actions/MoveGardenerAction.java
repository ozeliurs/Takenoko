package com.takenoko.actions;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.bamboo.BambooStack;
import com.takenoko.vector.PositionVector;

public class MoveGardenerAction implements Action {
    private final PositionVector relativePositionVector;

    /**
     * Constructor for the MoveGardenerAction class.
     *
     * @param relativePositionVector the position vector to move the Gardener
     */
    public MoveGardenerAction(PositionVector relativePositionVector) {
        this.relativePositionVector = relativePositionVector;
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
            botManager.displayMessage(botManager.getName() + " planted one bamboo");
        }
        return new ActionResult(1);
    }

    @Override
    public ActionType getType() {
        return ActionType.DEFAULT;
    }
}
