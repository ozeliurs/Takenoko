package com.takenoko.actors;

import com.takenoko.Board;
import com.takenoko.vector.PositionVector;
import com.takenoko.vector.Vector;

/** Panda class. The panda is an actor that can move on the board. */
public class Panda {
    PositionVector position;

    /** Constructor for the Panda class. Instantiate the panda at the origin. */
    public Panda() {
        this.position = new PositionVector(0, 0, 0);
    }

    /**
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

    /**
     * Move the panda with a vector.
     *
     * @param vector the vector to move the panda
     */
    public void move(Vector vector, Board board) {
        isMovePossible(vector, board);
        position = position.add(vector).toPositionVector();
    }

    /**
     * Check if the move is possible.
     *
     * @param vector the vector to move the panda
     * @param board the board
     */
    private void isMovePossible(Vector vector, Board board) {
        // check if the panda is moving in a straight line
        if (!(vector.r() == 0 || vector.q() == 0 || vector.s() == 0)) {
            throw new IllegalArgumentException("The panda must move in a straight line");
        }

        // check if the panda is moving on a tile
        for (int i = 0; i < vector.length() + 1; i++) {
            PositionVector ray =
                    this.position.add(vector.normalize().multiply(i)).toPositionVector();
            if (!board.isTile(ray)) {
                throw new IllegalArgumentException("The panda must move on a tile");
            }
        }
    }
}
