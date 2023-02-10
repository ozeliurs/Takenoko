package com.takenoko.bot.unitary;

import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
import com.takenoko.bot.PriorityBot;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.weather.Weather;
import com.takenoko.weather.WeatherFactory;
import java.util.Objects;

public class SmartChooseAndApplyWeather extends PriorityBot {

    private final transient Weather weather;

    public SmartChooseAndApplyWeather(Weather weather) {
        this.weather = weather;
    }

    public SmartChooseAndApplyWeather() {
        this.weather = WeatherFactory.SUNNY.createWeather();
    }

    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        if (!board.getWeather().map(v -> v.getClass().equals(weather.getClass())).orElse(false)) {
            this.addActionWithPriority(new ChooseAndApplyWeatherAction(weather), DEFAULT_PRIORITY);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartChooseAndApplyWeather that = (SmartChooseAndApplyWeather) o;
        return Objects.equals(weather, that.weather);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), weather);
    }
}
