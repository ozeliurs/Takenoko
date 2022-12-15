package com.takenoko.objective;

import com.takenoko.engine.Board;

public interface Objective {

    /** Verify state of the objective. */
    void verify(Board board);

    /**
     * Whether the objective has been achieved.
     *
     * @return if the objective is achieved.
     */
    boolean isAchieved();
}
