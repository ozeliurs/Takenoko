package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.PlayableManager;
import com.takenoko.vector.PositionVector;
import java.util.Objects;

public class MovePandaObjective implements Objective {
    private static final ObjectiveTypes type = ObjectiveTypes.MOVED_PANDA;

    private ObjectiveState state;

    public MovePandaObjective() {
        state = ObjectiveState.NOT_ACHIEVED;
    }

    /**
     * This method will be used to check whether the objective has been achieved yet
     *
     * @param board the board on which the objective is checked
     * @param playableManager
     */
    public void verify(Board board, PlayableManager playableManager) {
        if (!board.getActorsManager()
                .getPanda()
                .getPosition()
                .equals(new PositionVector(0, 0, 0))) {
            state = ObjectiveState.ACHIEVED;
        }
    }

    /**
     * This method is used to check whether the objective has been achieved yet
     *
     * @return true if the objective has been achieved, false otherwise
     */
    public boolean isAchieved() {
        return state == ObjectiveState.ACHIEVED;
    }

    @Override
    public String toString() {
        return "MovePandaObjective{" + "state=" + state + '}';
    }

    public ObjectiveTypes getType() {
        return type;
    }

    public ObjectiveState getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovePandaObjective that = (MovePandaObjective) o;
        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, state);
    }
}
