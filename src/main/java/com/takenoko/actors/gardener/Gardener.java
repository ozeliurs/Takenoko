package com.takenoko.actors.gardener;

import com.takenoko.actors.Actor;
import com.takenoko.engine.Board;
import com.takenoko.vector.PositionVector;

/** Gardener class. The gardener is an actor that can move on the board. */
public class Gardener extends com.takenoko.actors.Actor {

    /**
     * Constructor for the Gardener class.
     *
     * @param position the position of the gardener
     */
    public Gardener(PositionVector position) {
        super(position);
    }

    /** Constructor for the Gardener class. Instantiate the gardener at the origin. */
    public Gardener() {
        this(new PositionVector(0, 0, 0));
    }

    public Gardener(Gardener gardener) {
        this(gardener.getPositionVector().copy());
    }

    /**
     * @return a string explaining where the gardener is on the board
     */
    public String positionMessage() {
        return "The gardener is at " + this.getPositionVector();
    }

    public void afterMove(Board board) {
        // check if the panda can eat bamboo
        board.growBamboo(this.getPositionVector());
    }

    public Actor copy() {
        return new com.takenoko.actors.gardener.Gardener(this);
    }
}
