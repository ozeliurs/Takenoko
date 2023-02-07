package com.takenoko.bot.unitary;

import com.takenoko.actions.Action;
import com.takenoko.actions.weather.ChooseIfApplyWeatherAction;
import com.takenoko.bot.PriorityBot;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;

public class SmartApplyWeather extends PriorityBot {
    public Action chooseAction(Board board, BotState botState, History history) {
        // Always apply the weather
        this.addActionWithPriority(new ChooseIfApplyWeatherAction(true), DEFAULT_PRIORITY);

        // TODO: Add wind management | Priority : 0

        return super.chooseAction(board, botState, history);
    }
}
