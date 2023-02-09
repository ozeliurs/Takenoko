package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.shape.*;
import java.util.List;
import java.util.Objects;

public class PatternObjective extends Objective {
    private final Pattern pattern;

    public PatternObjective(Pattern pattern, int points) {
        super(ObjectiveTypes.SHAPE, ObjectiveState.NOT_ACHIEVED, points);
        this.pattern = pattern;
    }

    @Override
    public void verify(Board board, BotState botState) {
        if (!pattern.match(board).isEmpty()) {
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

    public List<Shape> getShapeToCompletePatternObjective(Board board) {
        return pattern.getShapesToCompletePatternObjective(board);
    }

    @Override
    public float getCompletion(Board board, BotState botState) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "Pattern Objective <" + pattern.toString() + ">";
    }

    /**
     * Return the pattern of the objective.
     *
     * @return the pattern of the objective
     */
    public Pattern getPattern() {
        return pattern;
    }
}
