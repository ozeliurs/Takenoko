package com.takenoko.actions.actors;

import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.vector.PositionVector;

@ActionAnnotation(ActionType.FORCED)
public class ForcedMovePandaAction extends MovePandaAction {
    /**
     * Constructor for the MovePandaAction class.
     *
     * @param relativePositionVector the position vector to move the panda
     */
    public ForcedMovePandaAction(PositionVector relativePositionVector) {
        super(relativePositionVector);
    }

    @Override
    public String toString() {
        return "ForcedMovePandaAction{" + super.toString() + '}';
    }
}
