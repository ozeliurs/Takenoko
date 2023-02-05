package com.takenoko.weather;

import com.takenoko.actions.Action;
import com.takenoko.actions.actors.ForcedMovePandaAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.List;

/**
 * The sky rumbles and lightning strikes, frightening the panda. The player can put the panda on the
 * plot of his choice. To recover from his fear, the shy animal eats a section of bamboo.
 */
public class Stormy implements Weather {

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
