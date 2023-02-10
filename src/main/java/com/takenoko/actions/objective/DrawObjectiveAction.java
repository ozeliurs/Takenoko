package com.takenoko.actions.objective;

import com.takenoko.actions.ActionResult;
import com.takenoko.actions.DefaultAction;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.engine.BotState;
import com.takenoko.objective.ObjectiveType;
import java.util.List;
import java.util.Objects;

/** This class is used to draw an objective card. */
@ActionAnnotation(ActionType.DEFAULT)
public class DrawObjectiveAction implements DefaultAction {
    private final ObjectiveType objectiveType;

    /**
     * Constructor. The parameter is mandatory as it allows to choose which type of objective you
     * want.
     *
     * @param objectiveType objectiveType
     */
    public DrawObjectiveAction(ObjectiveType objectiveType) {
        this.objectiveType = objectiveType;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {

        if (!board.hasObjectiveTypeInDeck(objectiveType)) {
            botManager.displayMessage("No more " + objectiveType + " in the deck");
            return new ActionResult(List.of(ChooseAndApplyWeatherAction.class), 0);
        }

        board.drawObjective(objectiveType);
        botManager.addObjective(board.peekObjectiveDeck());
        botManager.displayMessage(
                botManager.getName() + " drew objective " + board.peekObjectiveDeck());
        botManager.getSingleBotStatistics().updateActions(getClass().getSimpleName());
        return new ActionResult(List.of(DrawObjectiveAction.class), 1);
    }

    public static boolean canBePlayed(Board board, BotState botState) {
        return (botState.getObjectives().size()) < BotState.MAX_OBJECTIVES
                && !board.isObjectiveDeckEmpty();
    }

    /**
     * Get the objective type of the action
     *
     * @return ObjectiveType
     */
    public ObjectiveType getObjectiveType() {
        return objectiveType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrawObjectiveAction that = (DrawObjectiveAction) o;
        return objectiveType == that.objectiveType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectiveType);
    }
}
