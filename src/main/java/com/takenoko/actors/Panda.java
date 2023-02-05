package com.takenoko.actors;

import com.takenoko.engine.Board;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.vector.PositionVector;
import java.util.Map;

/** Panda class. The panda is an actor that can move on the board. */
public class Panda extends Actor {
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

    public Map<PositionVector, LayerBambooStack> afterMove(Board board) {
        // check if the panda can eat bamboo
        if (board.isBambooEatableAt(this.getPositionVector())) {
            // eat bamboo
            board.eatBamboo(this.getPositionVector());
            return Map.of(this.getPositionVector(), new LayerBambooStack(1));
        } else {
            return Map.of();
        }
    }

    public Panda copy() {
        return new Panda(this);
    }
}
