package com.takenoko.asset;

import com.takenoko.weather.WeatherDice;
import java.util.Objects;

public class GameAssets {
    private final WeatherDice weatherDice;
    private final ImprovementDeck improvementDeck;
    private final TileDeck tileDeck;
    private final ObjectiveDeck objectiveDeck;

    public GameAssets() {
        this(new WeatherDice(), new ImprovementDeck());
    }

    public GameAssets(WeatherDice weatherDice) {
        this(weatherDice, new ImprovementDeck());
    }

    public GameAssets(WeatherDice weatherDice, ImprovementDeck improvementDeck) {
        this.weatherDice = weatherDice;
        this.improvementDeck = improvementDeck;
        this.tileDeck = new TileDeck();
        this.objectiveDeck = new ObjectiveDeck();
    }

    public GameAssets(GameAssets gameAssets) {
        this.weatherDice = gameAssets.weatherDice;
        this.improvementDeck = gameAssets.improvementDeck;
        this.tileDeck = gameAssets.tileDeck;
        this.objectiveDeck = gameAssets.objectiveDeck;
    }

    /**
     * @return the weather dice used by all bots
     */
    public WeatherDice getWeatherDice() {
        return weatherDice;
    }

    /**
     * @return the improvement deck used by all bots
     */
    public ImprovementDeck getImprovementDeck() {
        return improvementDeck;
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

    public TileDeck getTileDeck() {
        return tileDeck;
    }

    public ObjectiveDeck getObjectiveDeck() {
        return objectiveDeck;
    }
}
