package com.takenoko.shape;

import com.takenoko.vector.Vector;

public class Adjacent extends Shape {
    public Adjacent() {
        super(new Vector(0, 0, 0), new Vector(1, 0, -1));
    }
}
