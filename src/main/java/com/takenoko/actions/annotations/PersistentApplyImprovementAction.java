package com.takenoko.actions.annotations;

import com.takenoko.actions.ApplyImprovementAction;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.vector.PositionVector;

@ActionAnnotation(ActionType.PERSISTENT)
public class PersistentApplyImprovementAction extends ApplyImprovementAction {
    public PersistentApplyImprovementAction(
            ImprovementType improvementType, PositionVector positionVector) {
        super(improvementType, positionVector);
    }
}
