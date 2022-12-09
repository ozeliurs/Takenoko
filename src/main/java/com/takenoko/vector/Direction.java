package com.takenoko.vector;

/** The enum Direction represents the 6 directions in a 2D hexagonal grid. */
public enum Direction {
    NORTH(new Vector(0, -1, 1)),
    NORTH_EAST(new Vector(1, -1, 0)),
    SOUTH_EAST(new Vector(1, 0, -1)),
    SOUTH(new Vector(0, 1, -1)),
    SOUTH_WEST(new Vector(-1, 1, 0)),
    NORTH_WEST(new Vector(-1, 0, 1));
    private final Vector vector;

    Direction(Vector vector) {
        this.vector = vector;
    }

    public Vector getVector() {
        return vector;
    }
}
