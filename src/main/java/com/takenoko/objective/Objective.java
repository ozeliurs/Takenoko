package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;

public abstract class Objective {

    private final ObjectiveTypes type;
    ObjectiveState state;

    protected Objective(ObjectiveTypes type, ObjectiveState state) {
        this.type = type;
        this.state = state;
    }

    /** Verify state of the objective. */
    public abstract void verify(Board board, BotManager botManager);

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
}
