package com.takenoko.asset;

import com.takenoko.weather.WeatherDice;
import java.util.Objects;

public class GameAssets {
    private final WeatherDice weatherDice;

    public GameAssets() {
        this(new WeatherDice());
    }

    public GameAssets(WeatherDice weatherDice) {
        this.weatherDice = weatherDice;
    }

    public GameAssets(GameAssets gameAssets) {
        this.weatherDice = gameAssets.weatherDice;
    }

    /**
     * @return the weather dice used by all bots
     */
    public WeatherDice getWeatherDice() {
        return weatherDice;
    }

    public GameAssets copy() {
        return new GameAssets(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameAssets that = (GameAssets) o;
        return weatherDice.equals(that.weatherDice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weatherDice);
    }
}
