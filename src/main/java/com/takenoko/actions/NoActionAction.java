package com.takenoko.actions;

import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;

@ActionAnnotation(ActionType.DEFAULT)
public class NoActionAction implements Action {
    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        botManager.displayMessage(botManager.getName() + " skipped his turn");
        return new ActionResult(1);
    }
}
