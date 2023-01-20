package com.takenoko.actions;

import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;

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
            botManager.displayMessage("Bot " + botManager.getName() + " applied the weather");
            return new ActionResult(board.peekWeather().apply(board, botManager));
        }
        return new ActionResult();
    }
}
