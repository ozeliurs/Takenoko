package com.takenoko.objective;

import com.takenoko.Board;
import com.takenoko.shape.Adjacent;
import com.takenoko.shape.Shape;
import java.util.List;

public class TwoAdjacentTilesObjective implements Objective {
    private static final ObjectiveTypes type = ObjectiveTypes.TWO_ADJACENT_TILES;
    private ObjectiveState state;

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
        List<Shape> matchingShapes = adjacentShape.match(board.getTiles());
        if (!matchingShapes.isEmpty()) {
            state = ObjectiveState.ACHIEVED;
        }
    }
}
