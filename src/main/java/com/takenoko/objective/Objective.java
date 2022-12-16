package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.PlayableManager;

public interface Objective {

    /** Verify state of the objective. */
    void verify(Board board, PlayableManager playableManager);

    /**
     * Whether the objective has been achieved.
     *
     * @return if the objective is achieved.
     */
    boolean isAchieved();
}
