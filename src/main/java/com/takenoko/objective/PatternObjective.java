package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.shape.Pattern;
import java.util.Objects;

public class PatternObjective extends Objective {
    private final Pattern pattern;

    public PatternObjective(Pattern pattern, int points) {
        super(ObjectiveTypes.SHAPE, ObjectiveState.NOT_ACHIEVED, points);
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
        PatternObjective objective = (PatternObjective) o;
        return pattern.equals(objective.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern);
    }

    public PatternObjective copy() {
        PatternObjective objective = new PatternObjective(pattern, getPoints());
        objective.state = state;
        return objective;
    }

    @Override
    public float getCompletion(Board board, BotManager botManager) {
        return pattern.matchRatio(board.getTilesWithoutPond());
    }
}
