package com.takenoko.weather;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;

public interface Weather {
    /** Code to be executed when the weather is rolled and modifiers applied. */
    void apply(Board board, BotManager botManager);

    void revert(Board board, BotManager botManager);
}
