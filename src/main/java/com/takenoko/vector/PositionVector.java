package com.takenoko.vector;

public class PositionVector extends Vector {
    /**
     * Constructor for the Vector class. The vector is represented by its coordinates in a 2D
     * hexagonal grid. The vector must respect q+r+s=0.
     *
     * @param q The q coordinate of the vector.
     * @param r The r coordinate of the vector.
     * @param s The s coordinate of the vector.
     */
    public PositionVector(int q, int r, int s) {
        super(q, r, s);
    }

    public PositionVector(Vector vector) {
        super(vector.q(), vector.r(), vector.s());
    }

    public PositionVector copy() {
        return new PositionVector(this);
    }
}
