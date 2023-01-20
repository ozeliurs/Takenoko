package com.takenoko.actions;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.weather.Weather;

public class ChooseAndApplyWeatherAction implements Action {
    Weather weather;

    public ChooseAndApplyWeatherAction(Weather weather) {
        this.weather = weather;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        return new ActionResult(weather.apply(board, botManager));
    }
}
