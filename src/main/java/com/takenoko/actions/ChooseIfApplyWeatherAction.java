package com.takenoko.actions;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;

public class ChooseIfApplyWeatherAction implements Action {
    private final boolean applyWeather;

    public ChooseIfApplyWeatherAction(boolean applyWeather) {
        this.applyWeather = applyWeather;
    }

    @Override
    public void execute(Board board, BotManager botManager) {
        if (applyWeather) {
            botManager.getRolledWeather().execute(board, botManager);
        }
    }
}
