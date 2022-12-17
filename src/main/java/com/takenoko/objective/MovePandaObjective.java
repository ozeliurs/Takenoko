package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.vector.PositionVector;
import java.util.Objects;

public class MovePandaObjective extends Objective {

    public MovePandaObjective() {
        super(ObjectiveTypes.MOVED_PANDA, ObjectiveState.NOT_ACHIEVED);
    }

    /**
     * This method will be used to check whether the objective has been achieved yet
     *
     * @param board the board on which the objective is checked
     * @param botManager the botManager on which the objective is checked
     */
    public void verify(Board board, BotManager botManager) {
        if (!board.getPandaPosition().equals(new PositionVector(0, 0, 0))) {
            state = ObjectiveState.ACHIEVED;
        }
    }

    @Override
    public String toString() {
        return "MovePandaObjective{" + "state=" + state + '}';
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
        return Objects.hash(getType(), getState());
    }
}
