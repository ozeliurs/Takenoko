package com.takenoko.actors.gardener;

import com.takenoko.bot.Action;
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
     */
    @Override
    public void execute(Board board, BotManager botManager) {
        // move the Gardener

        BambooStack bambooStack = board.moveGardener(relativePositionVector);
        botManager.displayMessage(
                botManager.getName()
                        + " moved the Gardener"
                        + " with "
                        + relativePositionVector
                        + " to position "
                        + board.getGardenerPosition());

        if (!bambooStack.isEmpty()) {
            // grow bamboo
            botManager.incrementPlantedBambooCounter();
            botManager.displayMessage(botManager.getName() + " planted one bamboo");
        }
    }
}
