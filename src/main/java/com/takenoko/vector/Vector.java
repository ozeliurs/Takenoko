package com.takenoko.vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/** The Vector class represents a vector in a 2D hexagonal grid. */
public class Vector {
    private final double q;
    private final double r;
    private final double s;

    /**
     * Constructor for the Vector class. The vector is represented by its coordinates in a 2D
     * hexagonal grid. The vector must respect q+r+s=0.
     *
     * @param q The q coordinate of the vector.
     * @param r The r coordinate of the vector.
     * @param s The s coordinate of the vector.
     */
    public Vector(double q, double r, double s) {
        if (q + r + s != 0) {
            throw new IllegalArgumentException("q + r + s must be 0");
        }
        this.q = q;
        this.r = r;
        this.s = s;
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

    /**
     * Subtract two vectors.
     *
     * @param other The vector to subtract to this vector.
     * @return The difference of this vector and the other vector.
     */
    public Vector sub(Vector other) {
        return new Vector(q() - other.q(), r() - other.r(), s() - other.s());
    }

    /**
     * Rotate the vector by 60 degrees.
     *
     * @return The rotated vector.
     */
    public Vector rotate60() {
        return new Vector(-r(), -s(), -q());
    }

    /**
     * Determine the length of the vector.
     *
     * @return The length of the vector.
     */
    public double length() {
        return ((Math.abs(q()) + Math.abs(r()) + Math.abs(s())) / 2.0f);
    }

    /**
     * Determine the distance between two vectors.
     *
     * @param other The other vector.
     * @return The distance between the two vectors.
     */
    public double distance(Vector other) {
        return sub(other).length();
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

    /**
     * Determine the neighbors of the vector.
     *
     * @return The neighbors around the vector.
     */
    public Collection<Vector> getNeighbors() {
        ArrayList<Vector> neighbors = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            neighbors.add(add(direction.getVector()));
        }
        return neighbors;
    }

    /**
     * Multiply the vector by a scalar.
     *
     * @param i The number of times to multiply the vector.
     * @return The multiplied vector.
     */
    public Vector multiply(double i) {
        return new Vector(q() * i, r() * i, s() * i);
    }

    /**
     * Normalize the vector.
     *
     * @return The normalized vector.
     */
    public Vector normalize() {
        double length = length();
        if (length == 0) {
            return new Vector(0, 0, 0);
        }
        return new Vector(q() / length, r() / length, s() / length);
    }

    public double q() {
        return q;
    }

    public double r() {
        return r;
    }

    public double s() {
        return s;
    }

    @Override
    public String toString() {
        return "Vector[" + "q=" + q + ", " + "r=" + r + ", " + "s=" + s + ']';
    }

    public PositionVector toPositionVector() {
        return new PositionVector(this);
    }
}
