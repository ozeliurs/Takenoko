package com.takenoko.layers.tile;

import com.takenoko.actions.Action;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.vector.PositionVector;

/** This class represents the action of placing a tile on the board. */
public class PlaceTileAction implements Action {
    private final Tile tile;
    private final PositionVector positionVector;

    /**
     * Constructor for the PlaceTileAction class.
     *
     * @param tile the tile to place
     * @param positionVector the position vector where to place the tile
     */
    public PlaceTileAction(Tile tile, PositionVector positionVector) {
        this.tile = tile;
        this.positionVector = positionVector;
    }

    /**
     * Place the tile on the board and display a message.
     *
     * @param board the board
     * @param botManager the bot manager
     */
    @Override
    public void execute(Board board, BotManager botManager) {
        if (!board.placeTile(tile, positionVector).isEmpty()) {
            botManager.displayMessage("Bamboo grew at " + positionVector);
        }
        botManager.displayMessage(botManager.getName() + " placed a tile at " + positionVector);
    }
}
