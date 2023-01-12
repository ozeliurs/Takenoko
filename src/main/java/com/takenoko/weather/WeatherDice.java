package com.takenoko.weather;

import java.util.Random;

public class WeatherDice extends Dice {
    /** Default Constructor */
    public WeatherDice() {
        super(6);
    }

    /**
     * Parametrized Constructor
     *
     * @param random random number generator
     */
    public WeatherDice(Random random) {
        super(6, random);
    }

    /**
     * Roll the dice to get a Weather
     *
     * @return the result of the roll
     */
    public Weather rollWeather() {
        int roll = super.roll();
        return switch (roll) {
            case 1 -> Weather.SUNNY;
            case 2 -> Weather.RAINY;
            case 3 -> Weather.CLOUDY;
            case 4 -> Weather.WINDY;
            case 5 -> Weather.STORM;
            case 6 -> Weather.RANDOM;
            default -> throw new IllegalStateException("Unexpected value: " + roll);
        };
    }
}
