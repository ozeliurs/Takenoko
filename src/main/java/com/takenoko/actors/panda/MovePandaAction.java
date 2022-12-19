package com.takenoko.actors.panda;

import com.takenoko.bot.Action;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.bamboo.EatBambooAction;
import com.takenoko.vector.PositionVector;

/** This class represents the action of moving the panda. */
public class MovePandaAction implements Action {
    private final PositionVector relativePositionVector;

    /**
     * Constructor for the MovePandaAction class.
     *
     * @param relativePositionVector the position vector to move the panda
     */
    public MovePandaAction(PositionVector relativePositionVector) {
        this.relativePositionVector = relativePositionVector;
    }

    /**
     * Move the panda with a vector on the board and display a message.
     *
     * @param board the board
     * @param botManager the bot manager
     */
    @Override
    public void execute(Board board, BotManager botManager) {
        // move the panda
        board.getPanda().move(relativePositionVector);
        botManager.displayMessage(
                botManager.getName()
                        + " moved the panda with "
                        + relativePositionVector
                        + " to position "
                        + board.getPandaPosition());

        // check if the panda can eat bamboo
        if (board.getBambooAt(board.getPandaPosition()).getBambooCount() > 0) {
            // eat bamboo
            new EatBambooAction(board.getPandaPosition()).execute(board, botManager);
        }
    }
}
