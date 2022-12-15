package com.takenoko.player;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;

/** Action interface. An action is a command that can be executed by the Bot Manager. */
public interface Action {
    void execute(Board board, BotManager botManager);
}
