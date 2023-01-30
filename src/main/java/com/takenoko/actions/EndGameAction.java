package com.takenoko.actions;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;

public class EndGameAction implements Action {
    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        return new ActionResult();
    }
}
