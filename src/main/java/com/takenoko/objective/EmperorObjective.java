package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;

/** This class represents the emperor objective. */
public class EmperorObjective extends Objective {
    public EmperorObjective() {
        super(ObjectiveTypes.EMPEROR, ObjectiveState.ACHIEVED, 2);
    }

    @Override
    public void verify(Board board, BotManager botManager) {
        // Do nothing as the objective is always achieved.
    }

    @Override
    public void reset() {
        // Do nothing as the objective is always achieved.
    }

    @Override
    public Objective copy() {
        return new EmperorObjective();
    }

    @Override
    public float getCompletion(Board board, BotManager botManager) {
        return 1f;
    }
}
