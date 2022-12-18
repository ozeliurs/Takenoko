package com.takenoko.layers.bamboo;

import com.takenoko.bot.Action;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.inventory.CollectBambooAction;
import com.takenoko.vector.PositionVector;

/** This action will remove a bamboo from a tile. */
public class RemoveBambooAction implements Action {

    private final PositionVector positionVector;

    public RemoveBambooAction(PositionVector positionVector) {
        this.positionVector = positionVector;
    }

    /**
     * Remove Bamboo on the given PositionVector.
     *
     * @param board the board
     * @param botManager the bot manager
     */
    @Override
    public void execute(Board board, BotManager botManager) {
        board.getLayerManager().getBambooLayer().removeBamboo(positionVector);
        new CollectBambooAction().execute(board, botManager);
    }
}
