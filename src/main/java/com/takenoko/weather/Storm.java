package com.takenoko.weather;

import com.takenoko.actions.Action;
import com.takenoko.actions.actors.ForcedMovePandaAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.List;

public class Storm implements Weather {

    @Override
    public List<Class<? extends Action>> apply(Board board, BotManager botManager) {
        board.setWeather(this);
        return List.of(ForcedMovePandaAction.class);
    }

    @Override
    public void revert(Board board, BotManager botManager) {
        board.resetWeather();
    }
}
