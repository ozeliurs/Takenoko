package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;

public abstract class Objective {

    private final ObjectiveTypes type;
    ObjectiveState state;
    private final int points;

    protected Objective(ObjectiveTypes type, ObjectiveState state, int points) {
        this.type = type;
        this.state = state;
        this.points = points;
    }

    /** Verify state of the objective. */
    public abstract void verify(Board board, BotState botState);

    /**
     * Whether the objective has been achieved.
     *
     * @return if the objective is achieved.
     */
    public boolean isAchieved() {
        return state == ObjectiveState.ACHIEVED;
    }

    public ObjectiveTypes getType() {
        return type;
    }

    public ObjectiveState getState() {
        return state;
    }

    public abstract void reset();

    public abstract Objective copy();

    /*
     * @return the completion of the objective (between 0 and 1)
     */
    public abstract float getCompletion(Board board, BotState botState);

    public int getPoints() {
        return points;
    }
}
