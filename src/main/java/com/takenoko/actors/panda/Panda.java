package com.takenoko.actors.panda;

import com.takenoko.engine.Board;
import com.takenoko.vector.PositionVector;

/** Panda class. The panda is an actor that can move on the board. */
public class Panda extends com.takenoko.actors.Actor {
    /**
     * Constructor for the Panda class.
     *
     * @param position the position of the panda
     */
    public Panda(PositionVector position) {
        super(position);
    }

    /** Constructor for the Panda class. Instantiate the panda at the origin. */
    public Panda() {
        this(new PositionVector(0, 0, 0));
    }

    public Panda(Panda panda) {
        this(panda.getPositionVector().copy());
    }

    /**
     * @return a string explaining where the panda is on the board
     */
    public String positionMessage() {
        return "The panda is at " + this.getPositionVector();
    }

    public void afterMove(Board board) {
        // check if the panda can eat bamboo
        if (!board.getBambooAt(this.getPositionVector()).isEmpty()) {
            // eat bamboo
            board.eatBamboo(this.getPositionVector());
        }
    }

    public Panda copy() {
        return new Panda(this);
    }
}
