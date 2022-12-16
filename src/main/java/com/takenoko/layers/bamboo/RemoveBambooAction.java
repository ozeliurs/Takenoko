package com.takenoko.layers.bamboo;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.player.Action;
import com.takenoko.vector.PositionVector;

public class RemoveBambooAction implements Action {

    private final PositionVector positionVector;

    public RemoveBambooAction(PositionVector positionVector) {
        this.positionVector = positionVector;
    }

    /**
     * Remove Bamboo on the location of the player
     *
     * @param board the board
     * @param botManager the bot manager
     */
    @Override
    public void execute(Board board, BotManager botManager) {
        board.getLayerManager().getBambooLayer().removeBamboo(positionVector);
    }
}
