package com.takenoko.weather;

import java.util.Random;

/** Implementation of the WeatherDice of the game, it has 6 sides and each side is a weather. */
public class WeatherDice extends Dice {
    /** Default Constructor */
    public WeatherDice() {
        super(6);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
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
        return Weather.values()[super.roll()];
    }
}
