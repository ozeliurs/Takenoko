package com.takenoko.bot.unitary;

import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
import com.takenoko.bot.PriorityBot;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.weather.WeatherFactory;

public class SmartChooseAndApplyWeather extends PriorityBot {
    @Override
    protected void fillAction(Board board, BotState botState, History history) {
        // Always apply the sun
        this.addActionWithPriority(
                new ChooseAndApplyWeatherAction(WeatherFactory.SUNNY.createWeather()),
                DEFAULT_PRIORITY);
    }
}
