package com.takenoko.weather;

import java.security.SecureRandom;
import java.util.Random;

public class Dice {
    private final Random random;
    private final int sides;

    private static final int DEFAULT_NUMBER_OF_SIDES = 6;

    public Dice(int sides, Random random) {
        this.sides = sides;
        this.random = random;
    }

    public Dice(int sides) {
        this(sides, new SecureRandom());
    }

    public Dice() {
        this(DEFAULT_NUMBER_OF_SIDES, new SecureRandom());
    }

    protected int roll() {
        return random.nextInt(sides) + 1;
    }
}
