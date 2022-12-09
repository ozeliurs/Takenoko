package com.takenoko.shape;

import com.takenoko.vector.Vector;
import java.util.ArrayList;
import java.util.List;

public class Shape {
    List<Vector> pattern;

    /** Constructor for the Shape class. */
    public Shape() {
        this(new ArrayList<>(List.of(new Vector(0, 0, 0))));
    }

    /**
     * Constructor for the Shape class.
     *
     * @param shape the pattern of the shape
     */
    public Shape(List<Vector> shape) {
        this.pattern = shape;

        if (!this.pattern.contains(new Vector(0, 0, 0))) {
            throw new IllegalArgumentException("The shape must contain the origin");
        }
    }
}
