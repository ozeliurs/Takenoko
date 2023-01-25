package com.takenoko.actions.improvement;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.ImprovementType;
import java.util.List;

/**
 * Action to draw an improvement from the deck. if the improvement is not available in the deck, the
 * bot can choose to and apply a weather instead.
 */
@ActionAnnotation(ActionType.FORCED)
public class DrawImprovementAction implements Action {

    ImprovementType improvementType;

    public DrawImprovementAction(ImprovementType improvementType) {
        this.improvementType = improvementType;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        if (!board.hasImprovementInDeck(improvementType)) {
            botManager.displayMessage("No more " + improvementType + " in the deck");
            return new ActionResult(List.of(ChooseAndApplyWeatherAction.class));
        }
        board.drawImprovement(improvementType);
        botManager.displayMessage(botManager.getName() + " drew improvement " + improvementType);
        return new ActionResult(
                List.of(ApplyImprovementAction.class, StoreImprovementAction.class), 1);
    }
}
