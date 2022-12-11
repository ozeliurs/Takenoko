package com.takenoko.shape;

import com.takenoko.vector.Vector;

public class Line extends Shape {
    public Line() {
        super(new Vector(0, 0, 0), new Vector(1, 0, -1), new Vector(2, 0, -2));
    }
}