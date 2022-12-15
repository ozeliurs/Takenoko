package com.takenoko.layers.tile;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.player.Action;
import com.takenoko.tile.Tile;
import com.takenoko.vector.PositionVector;

public class PlaceTileAction implements Action {
    private final Tile tile;
    private final PositionVector positionVector;

    public PlaceTileAction(Tile tile, PositionVector positionVector) {
        this.tile = tile;
        this.positionVector = positionVector;
    }

    @Override
    public void execute(Board board, BotManager botManager) {
        board.getTileLayer().placeTile(tile, positionVector);
        botManager.displayMessage("The bot has placed a tile at " + positionVector);
    }
}
