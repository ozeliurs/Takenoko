package com.takenoko.actions;

import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;

/** This class is used to end the game. */
@ActionAnnotation(ActionType.FORCED)
public class EndGameAction implements Action {
    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        return new ActionResult();
    }
}
