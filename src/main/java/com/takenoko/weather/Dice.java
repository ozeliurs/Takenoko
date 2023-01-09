package com.takenoko.weather;

import java.security.SecureRandom;

public class Dice {
    private SecureRandom random;
    private int sides;

    private static final int DEFAULT_NUMBER_OF_SIDES = 6;

    public Dice(int sides, SecureRandom random) {
        this.sides = sides;
        this.random = random;
    }

    public Dice(int sides) {
        this(sides, new SecureRandom());
    }

    public Dice(SecureRandom random) {
        this(DEFAULT_NUMBER_OF_SIDES, random);
    }

    public Dice() {
        this(DEFAULT_NUMBER_OF_SIDES, new SecureRandom());
    }

    public int roll() {
        return random.nextInt(sides) + 1;
    }
}
