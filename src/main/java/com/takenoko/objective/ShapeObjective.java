package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.shape.Shape;
import java.util.Objects;

public class ShapeObjective extends Objective {
    private final Shape pattern;

    public ShapeObjective(Shape pattern) {
        super(ObjectiveTypes.SHAPE, ObjectiveState.NOT_ACHIEVED);
        this.pattern = pattern;
    }

    @Override
    public void verify(Board board, BotManager botManager) {
        if (!pattern.match(board.getTilesWithoutPond()).isEmpty()) {
            state = ObjectiveState.ACHIEVED;
        }
    }

    @Override
    public void reset() {
        state = ObjectiveState.NOT_ACHIEVED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShapeObjective objective = (ShapeObjective) o;
        return pattern.equals(objective.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern);
    }

    public ShapeObjective copy() {
        ShapeObjective objective = new ShapeObjective(pattern);
        objective.state = state;
        return objective;
    }
}
