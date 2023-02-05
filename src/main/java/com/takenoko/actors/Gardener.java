package com.takenoko.actors;

import com.takenoko.engine.Board;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.vector.PositionVector;
import com.takenoko.vector.Vector;

/** Gardener class. The gardener is an actor that can move on the board. */
public class Gardener extends Actor {

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

    /**
     * @return a string explaining where the gardener is on the board
     */
    public String positionMessage() {
        return "The gardener is at " + this.getPositionVector();
    }

    public LayerBambooStack afterMove(Board board) {

        // check if the gardener can grow bamboo on adjacent tiles
        for (Vector adjacent : this.getPositionVector().getNeighbors()) {
            tryToGrowBambooAt(board, adjacent.toPositionVector());
        }

        // check if the gardener can grow bamboo on the tile he's on
        if (tryToGrowBambooAt(board, this.getPositionVector())) {
            return new LayerBambooStack(1);
        } else {
            return new LayerBambooStack(0);
        }
    }

    private boolean tryToGrowBambooAt(Board board, PositionVector position) {
        // check if the gardener can grow bamboo
        if (board.isBambooGrowableAt(position)) {
            // grow bamboo
            board.growBamboo(position);
            return true;
        }
        return false;
    }

    public Gardener copy() {
        return new Gardener(this.getPosition());
    }
}
