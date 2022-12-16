package com.takenoko.actors.panda;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.bamboo.RemoveBambooAction;
import com.takenoko.player.Action;
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
        board.getActorsManager().getPanda().move(relativePositionVector);
        botManager.displayMessage(botManager + " moved the panda to " + relativePositionVector);
        PositionVector pandaPosition =
                board.getActorsManager().getPanda().getPosition().toPositionVector();

        // check if the panda can eat bamboo
        if (board.getLayerManager().getBambooLayer().getBambooAt(pandaPosition).getBambooCount()
                > 0) {
            // eat bamboo
            new RemoveBambooAction(pandaPosition).execute(board, botManager);
            botManager.incrementBambooCounter();
            botManager.displayMessage(
                    "The panda has eaten one bamboo on the tile at " + relativePositionVector);
        }
    }
}
