package com.takenoko.objective;

import com.takenoko.Board;
import com.takenoko.shape.Adjacent;
import com.takenoko.shape.Shape;
import com.takenoko.tile.Tile;
import com.takenoko.vector.Vector;

import java.util.List;
import java.util.Map;

/**
 * The objective is to have two tiles next to each other.
 * Of course, the pond is not taken into account as the bot would win when placing its first tile.
 */
public class TwoAdjacentTilesObjective implements Objective {
    private static final ObjectiveTypes type = ObjectiveTypes.TWO_ADJACENT_TILES;
    private ObjectiveState state;

    /**
     * Constructor for the class
     */
    public TwoAdjacentTilesObjective() {
        state = ObjectiveState.NOT_ACHIEVED;
    }

    public boolean isAchieved() {
        return state == ObjectiveState.ACHIEVED;
    }

    public ObjectiveTypes getType() {
        return type;
    }

    public ObjectiveState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "TwoAdjacentTilesObjective{" + "state=" + state + '}';
    }

    public void verify(Board board) {
        Shape adjacentShape = new Adjacent();
        Map<Vector, Tile> boardTiles = board.getTilesWithoutPond();
        List<Shape> matchingShapes = adjacentShape.match(boardTiles);
        if (!matchingShapes.isEmpty()) {
            state = ObjectiveState.ACHIEVED;
        }
    }
}
