package com.takenoko.bot;

import com.takenoko.bot.unitary.SmartApplyWeather;
import com.takenoko.bot.unitary.SmartChooseAndApplyWeather;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;

import java.util.Objects;

public class WeatherMaster extends PriorityBot {
    private final int chooseIfApplyWeatherCoefficient;
    private final int chooseAndApplyWeatherCoefficient;

    public WeatherMaster(int applyWeatherCoefficient, int chooseAndApplyWeatherCoefficient) {
        this.chooseIfApplyWeatherCoefficient = applyWeatherCoefficient;
        this.chooseAndApplyWeatherCoefficient = chooseAndApplyWeatherCoefficient;
    }

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        this.addWithOffset(
                new SmartChooseAndApplyWeather().compute(board, botState, history),
                chooseAndApplyWeatherCoefficient);
        this.addWithOffset(
                new SmartApplyWeather()
                        .compute(board, botState, history)
                        .compute(board, botState, history),
                chooseIfApplyWeatherCoefficient);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WeatherMaster that = (WeatherMaster) o;
        return chooseIfApplyWeatherCoefficient == that.chooseIfApplyWeatherCoefficient && chooseAndApplyWeatherCoefficient == that.chooseAndApplyWeatherCoefficient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), chooseIfApplyWeatherCoefficient, chooseAndApplyWeatherCoefficient);
    }
}
