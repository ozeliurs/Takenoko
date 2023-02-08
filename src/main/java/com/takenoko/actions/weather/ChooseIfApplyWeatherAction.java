package com.takenoko.actions.weather;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.Objects;

/** Action to choose whether to apply the weather or not */
@ActionAnnotation(ActionType.FORCED)
public class ChooseIfApplyWeatherAction implements Action {
    private final boolean applyWeather;

    public ChooseIfApplyWeatherAction(boolean applyWeather) {
        this.applyWeather = applyWeather;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        if (applyWeather) {
            botManager.displayMessage(
                    botManager.getName() + " applied the weather " + board.peekWeather());
            return new ActionResult(board.peekWeather().apply(board, botManager), 0);
        }
        botManager.displayMessage(
                botManager.getName() + " did not apply the weather " + board.peekWeather());
        return new ActionResult(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChooseIfApplyWeatherAction that = (ChooseIfApplyWeatherAction) o;
        return applyWeather == that.applyWeather;
    }

    @Override
    public int hashCode() {
        return Objects.hash(applyWeather);
    }
}
