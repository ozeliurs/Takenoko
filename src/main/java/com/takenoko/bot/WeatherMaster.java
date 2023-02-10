package com.takenoko.bot;

import com.takenoko.bot.unitary.SmartApplyWeather;
import com.takenoko.bot.unitary.SmartChooseAndApplyWeather;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import java.util.Map;
import java.util.Objects;

public class WeatherMaster extends PriorityBot {

    private final Map<String, Integer> params;

    static final Map<String, Integer> DEFAULT_PARAMS =
            Map.of(
                    "chooseIfApplyWeatherCoefficient", 2,
                    "chooseAndApplyWeatherCoefficient", 1);

    public WeatherMaster(Map<String, Integer> params) {
        this.params = params;
    }

    public WeatherMaster() {
        this.params = DEFAULT_PARAMS;
    }

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        this.addWithOffset(
                new SmartChooseAndApplyWeather().compute(board, botState, history),
                params.get("chooseAndApplyWeatherCoefficient"));
        this.addWithOffset(
                new SmartApplyWeather().compute(board, botState, history),
                params.get("chooseIfApplyWeatherCoefficient"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WeatherMaster that = (WeatherMaster) o;
        return Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), params);
    }
}
