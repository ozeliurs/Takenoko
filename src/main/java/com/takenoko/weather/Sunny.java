package com.takenoko.weather;

import com.takenoko.actions.Action;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.List;

/** Weather that adds a new action to the bot. */
public class Sunny extends Weather {
    @Override
    public List<Class<? extends Action>> apply(Board board, BotManager botManager) {
        board.setWeather(this);
        botManager.addAction();
        return List.of();
    }

    @Override
    public void revert(Board board, BotManager botManager) {
        board.resetWeather();
    }

    @Override
    public String toString() {
        return "Sunny";
    }
}
