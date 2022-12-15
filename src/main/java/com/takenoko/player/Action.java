package com.takenoko.player;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;

public interface Action {
    void execute(Board board, BotManager botManager);
}
