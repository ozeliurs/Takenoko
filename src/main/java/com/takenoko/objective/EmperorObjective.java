package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;

/** This class represents the emperor objective. */
public class EmperorObjective extends Objective {
    public static final int EMPEROR_BONUS = 2;

    public EmperorObjective() {
        super(ObjectiveType.EMPEROR, ObjectiveState.ACHIEVED, EMPEROR_BONUS);
    }

    @Override
    public void verify(Board board, BotState botState) {
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
    public float getCompletion(Board board, BotState botState) {
        return 1f;
    }

    @Override
    public String toString() {
        return "Emperor Objective";
    }
}
