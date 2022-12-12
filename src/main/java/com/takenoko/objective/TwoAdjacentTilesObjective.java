package com.takenoko.objective;

import com.takenoko.Board;
import com.takenoko.shape.Adjacent;
import com.takenoko.shape.Shape;
import com.takenoko.tile.Tile;
import com.takenoko.vector.Vector;
import java.util.List;
import java.util.Map;

/**
 * The objective is to have two tiles next to each other. Of course, the pond is not taken into
 * account as the bot would win when placing its first tile.
 */
public class TwoAdjacentTilesObjective implements Objective {
    private static final ObjectiveTypes type = ObjectiveTypes.TWO_ADJACENT_TILES;
    private ObjectiveState state;

    /** Constructor for the class */
    public TwoAdjacentTilesObjective() {
        state = ObjectiveState.NOT_ACHIEVED;
    }

    /**
     * Allows checking if the objective has been achieved
     *
     * @return whether the objective has been achieved or not
     */
    public boolean isAchieved() {
        return state == ObjectiveState.ACHIEVED;
    }

    /**
     * @return ObjectiveTypes of the current objective
     */
    public ObjectiveTypes getType() {
        return type;
    }

    /**
     * @return ObjectiveState of the current objective
     */
    public ObjectiveState getState() {
        return state;
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
     */
    public void verify(Board board) {
        Shape adjacentShape = new Adjacent();
        Map<Vector, Tile> boardTiles = board.getTilesWithoutPond();
        List<Shape> matchingShapes = adjacentShape.match(boardTiles);
        if (!matchingShapes.isEmpty()) {
            state = ObjectiveState.ACHIEVED;
        }
    }
}