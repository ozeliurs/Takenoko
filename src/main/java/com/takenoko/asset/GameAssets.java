package com.takenoko.asset;

import com.takenoko.weather.WeatherDice;
import java.util.Objects;

public class GameAssets {
    private final WeatherDice weatherDice;
    private final ImprovementDeck improvementDeck;
    private final TileDeck tileDeck;
    private final ObjectiveDeck objectiveDeck;

    private final IrrigationDeck irrigationDeck;

    public GameAssets() {
        this(new WeatherDice(), new ImprovementDeck());
    }

    public GameAssets(WeatherDice weatherDice) {
        this(weatherDice, new ImprovementDeck());
    }

    public GameAssets(WeatherDice weatherDice, TileDeck tileDeck) {
        this(
                weatherDice,
                new ImprovementDeck(),
                tileDeck,
                new IrrigationDeck(),
                new ObjectiveDeck());
    }

    public GameAssets(
            WeatherDice weatherDice,
            ImprovementDeck improvementDeck,
            TileDeck tileDeck,
            IrrigationDeck irrigationDeck,
            ObjectiveDeck objectiveDeck) {
        this.weatherDice = weatherDice;
        this.improvementDeck = improvementDeck;
        this.tileDeck = tileDeck;
        this.objectiveDeck = objectiveDeck;
        this.irrigationDeck = irrigationDeck;
    }

    public GameAssets(WeatherDice weatherDice, ImprovementDeck improvementDeck) {
        this.weatherDice = weatherDice;
        this.improvementDeck = improvementDeck;
        this.tileDeck = new TileDeck();
        this.objectiveDeck = new ObjectiveDeck();
        this.irrigationDeck = new IrrigationDeck();
    }

    public GameAssets(GameAssets gameAssets) {
        this(
                gameAssets.weatherDice,
                gameAssets.improvementDeck,
                gameAssets.tileDeck,
                gameAssets.irrigationDeck,
                gameAssets.objectiveDeck);
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

    public IrrigationDeck getIrrigationDeck() {
        return irrigationDeck;
    }

    public GameAssets copy() {
        return new GameAssets(this);
    }

    public TileDeck getTileDeck() {
        return tileDeck;
    }

    public ObjectiveDeck getObjectiveDeck() {
        return objectiveDeck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameAssets that = (GameAssets) o;
        return Objects.equals(weatherDice, that.weatherDice)
                && Objects.equals(improvementDeck, that.improvementDeck)
                && Objects.equals(tileDeck, that.tileDeck)
                && Objects.equals(objectiveDeck, that.objectiveDeck)
                && Objects.equals(irrigationDeck, that.irrigationDeck);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weatherDice, improvementDeck, tileDeck, objectiveDeck, irrigationDeck);
    }
}
