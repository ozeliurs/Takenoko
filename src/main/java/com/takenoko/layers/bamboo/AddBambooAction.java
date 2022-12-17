package com.takenoko.layers.bamboo;

import com.takenoko.bot.Action;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.vector.PositionVector;

/** This class represents the action of adding a bamboo on the board. */
public class AddBambooAction implements Action {
    private final PositionVector positionVector;

    /**
     * Constructor for the AddBambooAction class.
     *
     * @param positionVector the position vector where to add a bamboo
     */
    public AddBambooAction(PositionVector positionVector) {
        this.positionVector = positionVector;
    }

    /**
     * Add a bamboo on the board and display a message.
     *
     * @param board the board to add a bamboo
     * @param botManager the bot manager to display a message
     */
    @Override
    public void execute(Board board, BotManager botManager) {
        board.getLayerManager().getBambooLayer().addBamboo(positionVector);
        botManager.displayMessage("Added bamboo to " + positionVector);
    }
}
