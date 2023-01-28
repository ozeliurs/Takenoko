package com.takenoko.weather;

import com.takenoko.actions.Action;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.List;

/** Interface for the weather. */
public interface Weather {
    /**
     * Code to be executed when the weather is rolled and modifiers applied.
     *
     * @return List of actions to be executed after the weather is applied.
     */
    List<Class<? extends Action>> apply(Board board, BotManager botManager);

    void revert(Board board, BotManager botManager);
}
