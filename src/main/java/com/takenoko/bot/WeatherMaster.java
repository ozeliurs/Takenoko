package com.takenoko.bot;

import com.takenoko.actions.Action;
import com.takenoko.bot.unitary.SmartApplyWeather;
import com.takenoko.bot.unitary.SmartChooseAndApplyWeather;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;

public class WeatherMaster extends PriorityBot {
    private final int chooseIfApplyWeatherCoefficient;
    private final int chooseAndApplyWeatherCoefficient;

    public WeatherMaster(int applyWeatherCoefficient, int chooseAndApplyWeatherCoefficient) {
        this.chooseIfApplyWeatherCoefficient = applyWeatherCoefficient;
        this.chooseAndApplyWeatherCoefficient = chooseAndApplyWeatherCoefficient;
    }

    @Override
    public Action chooseAction(Board board, BotState botState, History history) {
        this.addWithOffset(new SmartChooseAndApplyWeather(), chooseAndApplyWeatherCoefficient);
        this.addWithOffset(new SmartApplyWeather(), chooseIfApplyWeatherCoefficient);

        return super.chooseAction(board, botState, history);
    }
}
