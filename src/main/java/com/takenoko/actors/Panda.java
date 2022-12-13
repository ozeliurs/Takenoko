package com.takenoko.actors;

import com.takenoko.vector.Vector;

/**
 * Panda class. The panda is an actor that can move on the board.
 */
public class Panda {
    Vector position;

    public Panda() {
        this.position = new Vector(0, 0, 0);
    }

    public Vector getPosition() {
        return position;
    }
}
