package com.takenoko.actors.panda;

import com.takenoko.engine.Board;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.vector.PositionVector;
import java.util.List;
import java.util.Objects;

/** Panda class. The panda is an actor that can move on the board. */
public class Panda {
    PositionVector position;

    /**
     * Constructor for the Panda class.
     *
     * @param position the position of the panda
     */
    public Panda(PositionVector position) {
        this.position = position;
    }

    /** Constructor for the Panda class. Instantiate the panda at the origin. */
    public Panda() {
        this(new PositionVector(0, 0, 0));
    }

    public Panda(Panda panda) {
        this(panda.position.copy());
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
     * Move the panda with a vector and try to eat bamboo.
     *
     * @param vector the vector to move the panda
     * @return bamboo stack of one if the panda ate a bamboo
     */
    public LayerBambooStack move(PositionVector vector, Board board) {
        if (!isMovePossible(vector, board)) {
            throw new IllegalArgumentException("This move is not possible");
        }
        position = position.add(vector).toPositionVector();

        // check if the panda can eat bamboo
        if (!board.getBambooAt(position).isEmpty()) {
            // eat bamboo
            board.eatBamboo(position);
            return new LayerBambooStack(1);
        }

        return new LayerBambooStack(0);
    }

    /**
     * Check if the move is possible. The panda can only move in a straight line on the board tiles.
     *
     * @param vector the vector to move the panda
     * @return true if the move is possible, false otherwise
     */
    private boolean isMovePossible(PositionVector vector, Board board) {
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
     * Returns possible moves for the panda.
     *
     * @return the possible moves
     */
    public List<PositionVector> getPossibleMoves(Board board) {
        return board.getTiles().keySet().stream()
                .map(v -> v.sub(position).toPositionVector())
                .filter(v -> isMovePossible(v, board))
                .filter(v -> !v.equals(new PositionVector(0, 0, 0)))
                .toList();
    }

    public Panda copy() {
        return new Panda(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Panda panda = (Panda) o;
        return getPosition().equals(panda.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition());
    }
}
