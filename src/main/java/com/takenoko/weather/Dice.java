package com.takenoko.weather;

import java.security.SecureRandom;
import java.util.Random;

public class Dice {
    private final Random random;
    private final int sides;

    private static final int DEFAULT_NUMBER_OF_SIDES = 6;

    /**
     * Parametrized Constructor
     *
     * @param sides number of sides of the dice
     * @param random random number generator
     */
    public Dice(int sides, Random random) {
        this.sides = sides;
        this.random = random;
    }

    /**
     * Parametrized Constructor
     *
     * @param sides number of sides of the dice
     */
    public Dice(int sides) {
        this(sides, new SecureRandom());
    }

    /** Default Constructor */
    public Dice() {
        this(DEFAULT_NUMBER_OF_SIDES, new SecureRandom());
    }

    /**
     * Roll the dice
     *
     * @return the result of the roll
     */
    protected int roll() {
        return random.nextInt(sides) + 1;
    }
}
