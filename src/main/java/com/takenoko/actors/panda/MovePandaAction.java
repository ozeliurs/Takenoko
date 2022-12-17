package com.takenoko.actors.panda;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.bamboo.RemoveBambooAction;
import com.takenoko.bot.Action;
import com.takenoko.vector.PositionVector;

/** This class represents the action of moving the panda. */
public class MovePandaAction implements Action {
    private final PositionVector positionVector;

    /**
     * Constructor for the MovePandaAction class.
     *
     * @param positionVector the position vector to move the panda
     */
    public MovePandaAction(PositionVector positionVector) {
        this.positionVector = positionVector;
    }

    /**
     * Move the panda with a vector on the board and display a message.
     *
     * @param board the board
     * @param botManager the bot manager
     */
    @Override
    public void execute(Board board, BotManager botManager) {
        board.getActorsManager().getPanda().move(positionVector);
        botManager.displayMessage(botManager + " moved the panda to " + positionVector);
        new RemoveBambooAction(positionVector).execute(board, botManager);
        botManager.incrementBambooCounter();
        botManager.displayMessage(
                "The panda has eaten one bamboo on the tile at " + positionVector);
    }
}
