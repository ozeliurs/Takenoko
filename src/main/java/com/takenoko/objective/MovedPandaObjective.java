package com.takenoko.objective;

import com.takenoko.Board;
import com.takenoko.actors.Panda;
import com.takenoko.vector.PositionVector;

public class MovedPandaObjective implements Objective {
    private static final ObjectiveTypes type = ObjectiveTypes.MOVED_PANDA;
    private ObjectiveState state;

    /** Constructor for the class 🤯 */
    public MovedPandaObjective() {
        state = ObjectiveState.NOT_ACHIEVED;
    }

    /**
     * This will check if the panda has moved from its initial position (0, 0, 0).
     *
     * @param board the current board of the game
     */
    @Override
    public void verify(Board board) {
        Panda panda = board.getPanda();
        if (!panda.getPosition().equals(new PositionVector(0, 0, 0))) {
            state = ObjectiveState.ACHIEVED;
        }
    }

    /**
     * Allows checking if the objective has been achieved
     *
     * @return whether the objective has been achieved or not
     */
    @Override
    public boolean isAchieved() {
        return state == ObjectiveState.ACHIEVED;
    }

    @Override
    public ObjectiveState getState() {
        return state;
    }

    @Override
    public ObjectiveTypes getType() {
        return type;
    }
}
