package com.takenoko.weather;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;

public class Sunny implements Weather {
    @Override
    public void execute(Board board, BotManager botManager) {
        botManager.addAction();
    }
}
