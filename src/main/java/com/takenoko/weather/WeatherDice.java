package com.takenoko.weather;

public class WeatherDice extends Dice {
    public WeatherDice() {
        super(6);
    }

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
