package com.takenoko.actions.objective;

import com.takenoko.actions.ActionResult;
import com.takenoko.actions.DefaultAction;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.engine.BotState;
import java.util.List;

/** This class is used to draw an objective card. */
@ActionAnnotation(ActionType.DEFAULT)
public class DrawObjectiveAction implements DefaultAction {

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        board.drawObjective();
        botManager.addObjective(board.peekObjectiveDeck());
        botManager.displayMessage(
                botManager.getName() + " drew objective " + board.peekObjectiveDeck());
        return new ActionResult(List.of(DrawObjectiveAction.class), 1);
    }

    public static boolean canBePlayed(Board board, BotState botState) {
        return (botState.getObjectives().size()
                                + botState.getAchievedObjectives(board, botState).size())
                        < BotState.MAX_OBJECTIVES
                && !board.isObjectiveDeckEmpty();
    }
}
