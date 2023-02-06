package com.takenoko.actions.tile;

import com.takenoko.actions.ActionResult;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;

/**
 * This class represents the action of placing a tile on the board with an improvement from the
 * inventory.
 */
@ActionAnnotation(ActionType.FORCED)
public class PlaceTileWithImprovementAction extends PlaceTileAction {

    public PlaceTileWithImprovementAction(
            Tile tile, PositionVector positionVector, ImprovementType improvementType) {
        super(tile, positionVector);
        tile.setImprovement(improvementType);
    }

    /**
     * Apply the improvement on tile then place the tile on the board and display a message.
     *
     * @param board the board
     * @param botManager the bot manager
     * @return the action result
     */
    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        botManager.displayMessage(
                botManager.getName()
                        + " applied improvement "
                        + tile.getImprovement().orElseThrow()
                        + " on tile to place");
        botManager.getInventory().useImprovement(tile.getImprovement().orElseThrow());
        return super.execute(board, botManager);
    }
}
