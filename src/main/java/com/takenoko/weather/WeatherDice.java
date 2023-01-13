package com.takenoko.weather;

import java.util.Random;

/** Implementation of the WeatherDice of the game, it has 6 sides and each side is a weather. */
public class WeatherDice extends Dice {
    /** Default Constructor */
    public WeatherDice() {
        super(WeatherFactory.values().length);
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
        return WeatherFactory.values()[super.roll()].createWeather();
    }
}
