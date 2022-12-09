package com.takenoko.vector;

import java.util.Objects;

/** The Vector class represents a vector in a 2D hexagonal grid. */
public record Vector(int q, int r, int s) {
    /**
     * Constructor for the Vector class. The vector is represented by its coordinates in a 2D
     * hexagonal grid. The vector must respect q+r+s=0.
     *
     * @param q The q coordinate of the vector.
     * @param r The r coordinate of the vector.
     * @param s The s coordinate of the vector.
     */
    public Vector {
        if (q + r + s != 0) {
            throw new IllegalArgumentException("q + r + s must be 0");
        }
    }

    /**
     * Add two vectors.
     *
     * @param other The vector to add to this vector.
     * @return The sum of this vector and the other vector.
     */
    public Vector add(Vector other) {
        return new Vector(q() + other.q(), r() + other.r(), s() + other.s());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return q() == vector.q() && r() == vector.r() && s() == vector.s();
    }

    @Override
    public int hashCode() {
        return Objects.hash(q(), r(), s());
    }
}
