package com.takenoko.actors;

import com.takenoko.engine.Board;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.vector.PositionVector;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Actor {
    PositionVector position;

    protected Actor(PositionVector position) {
        this.position = position;
    }

    /**
     * @return the position of the actor
     */
    public PositionVector getPosition() {
        return position;
    }

    /**
     * Check if the move is possible. The actor can only move in a straight line on the board tiles.
     *
     * @param vector the vector to move the actor
     * @return true if the move is possible, false otherwise
     */
    protected boolean isMovePossible(PositionVector vector, Board board) {
        // check if the actor is moving in a straight line
        if (!(vector.r() == 0 || vector.q() == 0 || vector.s() == 0)) {
            return false;
        }

        // check if the actor is moving on a tile
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
     * Returns possible moves for the actor.
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

    /**
     * Move the panda with a vector and try to eat bamboo.
     *
     * @param vector the vector to move the panda
     */
    public Map<PositionVector, LayerBambooStack> move(PositionVector vector, Board board) {
        if (!isMovePossible(vector, board)) {
            throw new IllegalArgumentException("This move is not possible");
        }
        position = position.add(vector).toPositionVector();
        return this.afterMove(board);
    }

    public abstract Map<PositionVector, LayerBambooStack> afterMove(Board board);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return getPosition().equals(actor.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition());
    }

    public PositionVector getPositionVector() {
        return position;
    }
}
