package com.takenoko.layers.bamboo;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.player.Action;
import com.takenoko.vector.PositionVector;

public class AddBambooAction implements Action {
    private final PositionVector positionVector;

    public AddBambooAction(PositionVector positionVector) {
        this.positionVector = positionVector;
    }

    @Override
    public void execute(Board board, BotManager botManager) {
        board.getLayerManager().getBambooLayer().addBamboo(positionVector);
        botManager.displayMessage("Added bamboo to " + positionVector);
    }
}
