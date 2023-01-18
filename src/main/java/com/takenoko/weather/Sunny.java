package com.takenoko.weather;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;

public class Sunny implements Weather {
    @Override
    public void apply(Board board, BotManager botManager) {
        board.setWeather(this);
        botManager.addAction();
    }

    @Override
    public void revert(Board board, BotManager botManager) {
        board.resetWeather();
    }
}
