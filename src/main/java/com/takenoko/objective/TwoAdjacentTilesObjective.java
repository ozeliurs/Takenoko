package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.Tile;
import com.takenoko.shape.Adjacent;
import com.takenoko.shape.Shape;
import com.takenoko.vector.PositionVector;
import java.util.List;
import java.util.Map;

/**
 * The objective is to have two tiles next to each other. Of course, the pond is not taken into
 * account as the bot would win when placing its first tile.
 */
public class TwoAdjacentTilesObjective extends Objective {

    /** Constructor for the class */
    public TwoAdjacentTilesObjective() {
        super(ObjectiveTypes.TWO_ADJACENT_TILES, ObjectiveState.NOT_ACHIEVED);
    }

    @Override
    public String toString() {
        return "TwoAdjacentTilesObjective{" + "state=" + state + '}';
    }

    /**
     * This will try to match the tiles on the board to see if there are two of them adjacent to
     * each other.
     *
     * @param board the current board of the game
     * @param botManager the bot manager to display a message
     */
    public void verify(Board board, BotManager botManager) {
        Shape adjacentShape = new Adjacent();
        Map<PositionVector, Tile> boardTiles =
                board.getLayerManager().getTileLayer().getTilesWithoutPond();
        List<Shape> matchingShapes = adjacentShape.match(boardTiles);
        if (!matchingShapes.isEmpty()) {
            state = ObjectiveState.ACHIEVED;
        }
    }
}
