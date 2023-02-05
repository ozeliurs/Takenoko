package com.takenoko.actions.objective;

import com.takenoko.actions.ActionResult;
import com.takenoko.actions.DefaultAction;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionCanBePlayedMultipleTimesPerTurn;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.engine.BotState;
import com.takenoko.objective.Objective;

/** This class is an action to redeem an objective. */
@ActionAnnotation(ActionType.DEFAULT)
@ActionCanBePlayedMultipleTimesPerTurn()
public class RedeemObjectiveAction implements DefaultAction {
    Objective objective;

    public RedeemObjectiveAction(Objective objective) {
        this.objective = objective;
    }

    public static boolean canBePlayed(BotState botState, Board board) {
        return !botState.getAchievedObjectives(board, botState).isEmpty();
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        botManager.displayMessage(
                botManager.getName()
                        + " redeemed objective"
                        + objective
                        + " and gained "
                        + objective.getPoints()
                        + "points");
        botManager.redeemObjective(objective);
        return new ActionResult();
    }
}
