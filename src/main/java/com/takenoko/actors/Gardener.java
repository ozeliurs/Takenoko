package com.takenoko.actors;

import com.takenoko.engine.Board;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.vector.PositionVector;
import com.takenoko.vector.Vector;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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

    public Map<PositionVector, LayerBambooStack> afterMove(Board board) {
        Map<PositionVector, LayerBambooStack> growBambooMap = new HashMap<>();

        Stream.concat(
                        this.getPositionVector().getNeighbors().stream(),
                        Stream.of(this.getPositionVector()))
                .map(Vector::toPositionVector)
                .filter(board::isTile)
                .filter(
                        v ->
                                board.getTileAt(v)
                                        .getColor()
                                        .equals(
                                                board.getTileAt(this.getPositionVector())
                                                        .getColor()))
                .filter(board::isBambooGrowableAt)
                .forEach(
                        v -> {
                            board.growBamboo(v);
                            growBambooMap.put(v, board.getBambooAt(v));
                        });
        return growBambooMap;
    }

    public Gardener copy() {
        return new Gardener(this.getPosition());
    }
}
