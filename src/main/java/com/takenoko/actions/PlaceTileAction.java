package com.takenoko.actions;

import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;

/** This class represents the action of placing a tile on the board. */
@ActionAnnotation(ActionType.FORCED)
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
     * @return the action result
     */
    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        if (!board.placeTile(tile, positionVector).isEmpty()) {
            botManager.displayMessage("Bamboo grew at " + positionVector);
        }
        botManager.displayMessage(botManager.getName() + " placed a tile at " + positionVector);

        return new ActionResult(1);
    }
}
