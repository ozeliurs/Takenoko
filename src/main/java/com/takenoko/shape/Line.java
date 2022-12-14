package com.takenoko.shape;

import com.takenoko.vector.PositionVector;

public class Line extends Shape {
    public Line() {
        super(
                new PositionVector(0, 0, 0),
                new PositionVector(1, 0, -1),
                new PositionVector(2, 0, -2));
    }
}
