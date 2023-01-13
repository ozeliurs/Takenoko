package com.takenoko.shape;

import com.takenoko.vector.PositionVector;

public class Adjacent extends Pattern {
    public Adjacent() {
        super(new PositionVector(0, 0, 0), new PositionVector(1, 0, -1));
    }
}
