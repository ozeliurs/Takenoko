package com.takenoko.actions.weather;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.weather.Weather;
import java.util.Objects;

/** Action to choose a weather and apply it. */
@ActionAnnotation(ActionType.FORCED)
public class ChooseAndApplyWeatherAction implements Action {
    final Weather weather;

    public ChooseAndApplyWeatherAction(Weather weather) {
        this.weather = weather;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        botManager.displayMessage(botManager.getName() + " chose and applied weather " + weather);
        return new ActionResult(weather.apply(board, botManager), 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChooseAndApplyWeatherAction that = (ChooseAndApplyWeatherAction) o;
        return Objects.equals(weather, that.weather);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weather);
    }
}
