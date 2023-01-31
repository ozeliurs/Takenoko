package com.takenoko.actions.objective;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.objective.Objective;

/** This class is an action to redeem an objective. */
@ActionAnnotation(ActionType.DEFAULT)
public class RedeemObjectiveAction implements Action {
    Objective objective;

    public RedeemObjectiveAction(Objective objective) {
        this.objective = objective;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        botManager.redeemObjective(objective);
        botManager.displayMessage(
                botManager.getName()
                        + " redeemed objective"
                        + objective
                        + " and gained "
                        + objective.getPoints()
                        + "points");
        return new ActionResult();
    }
}
