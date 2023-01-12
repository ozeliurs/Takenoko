package com.takenoko.weather;

import java.util.Random;

/** Implementation of the WeatherDice of the game, it has 6 sides and each side is a weather. */
public class WeatherDice extends Dice {
    /** Default Constructor */
    public WeatherDice() {
        super(6);
    }

    /**
     * Specify the Random Generator
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
