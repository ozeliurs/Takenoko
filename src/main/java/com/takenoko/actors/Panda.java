package com.takenoko.actors;

import com.takenoko.vector.Vector;

/**
 * Panda class. The panda is an actor that can move on the board.
 */
public class Panda {
    Vector position;

    /**
     * Constructor for the Panda class. Instantiate the panda at the origin.
     */
    public Panda() {
        this.position = new Vector(0, 0, 0);
    }

    /**
     *
     * @return the position of the panda
     */
    public Vector getPosition() {
        return position;
    }

    /**
     * @return a string explaining where the panda is on the board
     */
    public String positionMessage() {
        return "The panda is at " + position;
    }
}
