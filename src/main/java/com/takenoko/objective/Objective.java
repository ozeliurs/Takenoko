package com.takenoko.objective;

import com.takenoko.Board;

public interface Objective {

    /** Verify state of the objective. */
    void verify(Board board);

    /**
     * Whether the objective has been achieved.
     *
     * @return if the objective is achieved.
     */
    boolean isAchieved();

    /**
     * Get the state of the objective. (ACHIEVED, NOT_ACHIEVED)
     *
     * @return the description of the objective.
     */
    ObjectiveState getState();

    /**
     * Get the type of the objective. (MOVED_PANDA, TWO_ADJACENT_TILES)
     *
     * @return the description of the objective.
     */
    ObjectiveTypes getType();

    String toString();
}
