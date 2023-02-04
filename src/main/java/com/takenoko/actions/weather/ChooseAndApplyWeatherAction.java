package com.takenoko.actions.weather;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.weather.Weather;

/** Action to choose a weather and apply it. */
@ActionAnnotation(ActionType.FORCED)
public class ChooseAndApplyWeatherAction implements Action {
    Weather weather;

    public ChooseAndApplyWeatherAction(Weather weather) {
        this.weather = weather;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        botManager.displayMessage(botManager.getName() + " chose and applied weather " + weather);
        return new ActionResult(weather.apply(board, botManager), 0);
    }
}
