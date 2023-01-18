package com.takenoko.actions;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;

/**
 * Action to choose whether to apply the weather or not
 */
public class ChooseIfApplyWeatherAction implements Action {
    private final boolean applyWeather;

    public ChooseIfApplyWeatherAction(boolean applyWeather) {
        this.applyWeather = applyWeather;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        if (applyWeather) {
            board.peekWeather().apply(board, botManager);
        }
        return new ActionResult();
    }
}
