package com.takenoko.bot.unitary;

import com.takenoko.actions.Action;
import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
import com.takenoko.actions.weather.ChooseIfApplyWeatherAction;
import com.takenoko.bot.PriorityBot;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.weather.WeatherFactory;

public class SmartWeather extends PriorityBot {
    @Override
    public Action chooseAction(Board board, BotState botState, History history) {
        // Always apply the weather
        this.addActionWithPriority(new ChooseIfApplyWeatherAction(true), DEFAULT_PRIORITY + 2);

        // Priority 7 : Apply sunny weather
        this.addActionWithPriority(
                new ChooseAndApplyWeatherAction(WeatherFactory.SUNNY.createWeather()),
                DEFAULT_PRIORITY + 1);

        // Priority 6 : Apply windy weather
        // TODO PRIORITY + 0

        return super.chooseAction(board, botState, history);
    }
}
