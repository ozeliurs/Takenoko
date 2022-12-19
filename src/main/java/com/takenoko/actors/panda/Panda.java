package com.takenoko.actors.panda;

import com.takenoko.engine.Board;
import com.takenoko.vector.PositionVector;
import java.util.List;

/** Panda class. The panda is an actor that can move on the board. */
public class Panda {
    PositionVector position;
    List<PositionVector> possibleMoves;
    private final Board board;

    /** Constructor for the Panda class. Instantiate the panda at the origin. */
    public Panda(Board board) {
        this.position = new PositionVector(0, 0, 0);
        this.board = board;
        calculatePossibleMoves();
    }

    /**
     * @return the position of the panda
     */
    public PositionVector getPosition() {
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
    void move(PositionVector vector) {
        if (!isMovePossible(vector)) {
            throw new IllegalArgumentException("This move is not possible");
        }
        position = position.add(vector).toPositionVector();
        calculatePossibleMoves();
    }

    /**
     * Check if the move is possible.
     *
     * @param vector the vector to move the panda
     * @return true if the move is possible, false otherwise
     */
    private boolean isMovePossible(PositionVector vector) {
        // check if the panda is moving in a straight line
        if (!(vector.r() == 0 || vector.q() == 0 || vector.s() == 0)) {
            return false;
        }

        // check if the panda is moving on a tile
        for (int i = 0; i < vector.length() + 1; i++) {
            PositionVector ray =
                    this.position.add(vector.normalize().multiply(i)).toPositionVector();

            if (!board.isTile(ray)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get possible moves for the panda.
     *
     * @return the possible moves
     */
    public List<PositionVector> getPossibleMoves() {
        calculatePossibleMoves();
        return possibleMoves;
    }

    /** Calculate possible moves for the panda. */
    public void calculatePossibleMoves() {
        possibleMoves =
                board.getTiles().keySet().stream()
                        .map(v -> v.sub(position).toPositionVector())
                        .filter(this::isMovePossible)
                        .filter(v -> !v.equals(new PositionVector(0, 0, 0)))
                        .toList();
    }
}
