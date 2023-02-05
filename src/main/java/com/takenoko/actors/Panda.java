package com.takenoko.actors;

import com.takenoko.engine.Board;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.vector.PositionVector;
import com.takenoko.weather.Storm;
import com.takenoko.weather.Weather;
import java.util.Map;
import java.util.Optional;

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

    /**
     * If the weather is a storm, the panda can move wherever he wants. Otherwise, the panda can
     * only move like a normal actor.
     *
     * @param vector the vector to move the panda
     * @param board the board on which the panda is moving
     * @return true if the move is possible, false otherwise
     */
    @Override
    protected boolean isMovePossible(PositionVector vector, Board board) {
        Optional<Weather> weather = board.getWeather();
        if (weather.isPresent() && weather.get().getClass().equals(Storm.class)) {
            return true;
        }
        return super.isMovePossible(vector, board);
    }

    /**
     * After the panda moves, he can eat bamboo if there is bamboo on the tile he moved to. If there
     * is bamboo, the panda eats it and a map with the bamboo stack is returned.
     *
     * @param board the board on which the panda is moving
     * @return the bamboo stack that the panda ate
     */
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
