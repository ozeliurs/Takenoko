package com.takenoko.weather;

import com.takenoko.actions.Action;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.List;

/** Weather that allows the player to play the same action twice. */
public class Windy implements Weather {
    @Override
    public List<Class<? extends Action>> apply(Board board, BotManager botManager) {
        board.setWeather(this);
        return List.of();
    }

    @Override
    public void revert(Board board, BotManager botManager) {
        board.resetWeather();
    }
}
