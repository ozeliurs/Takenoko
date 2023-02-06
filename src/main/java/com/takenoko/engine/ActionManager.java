package com.takenoko.engine;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.DefaultAction;
import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionCanBePlayedMultipleTimesPerTurn;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.actions.improvement.ApplyImprovementFromInventoryAction;
import com.takenoko.actions.irrigation.DrawIrrigationAction;
import com.takenoko.actions.irrigation.PlaceIrrigationFromInventoryAction;
import com.takenoko.actions.objective.DrawObjectiveAction;
import com.takenoko.actions.objective.RedeemObjectiveAction;
import com.takenoko.actions.tile.DrawTileAction;
import com.takenoko.weather.Windy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** This class is used to manage the actions of a bot. */
public class ActionManager {
    private static final int DEFAULT_NUMBER_OF_ACTIONS = 2;

    private static final List<Class<? extends DefaultAction>> DEFAULT_AVAILABLE_ACTIONS =
            List.of(
                    DrawObjectiveAction.class,
                    DrawTileAction.class,
                    DrawIrrigationAction.class,
                    MoveGardenerAction.class,
                    MovePandaAction.class,
                    RedeemObjectiveAction.class,
                    PlaceIrrigationFromInventoryAction.class,
                    ApplyImprovementFromInventoryAction.class);

    private int numberOfActions;
    private List<Class<? extends Action>> availableActions;
    private List<Class<? extends Action>> alreadyDoneActions = new ArrayList<>();

    public ActionManager() {
        this.numberOfActions = DEFAULT_NUMBER_OF_ACTIONS;
        this.availableActions = new ArrayList<>();
    }

    public ActionManager(ActionManager actionManager) {
        this.numberOfActions = actionManager.numberOfActions;
        this.availableActions = new ArrayList<>(actionManager.availableActions);
        this.alreadyDoneActions = new ArrayList<>(actionManager.alreadyDoneActions);
    }

    public ActionManager(int numberOfActions, List<Class<? extends Action>> availableActions) {
        this.numberOfActions = numberOfActions;
        this.availableActions = availableActions;
    }

    /**
     * Set the number of actions the bot can do in a turn.
     *
     * @param numberOfActions
     */
    public void setNumberOfActions(int numberOfActions) {
        this.numberOfActions = numberOfActions;
    }

    /**
     * @return number of actions the bot can do in a turn
     */
    protected int getNumberOfActions() {
        return numberOfActions;
    }

    /**
     * Return the list of available actions. If actions of FORCED type are available, only these
     * actions are returned else all available actions are returned.
     *
     * @return the list of available actions
     */
    public List<Class<? extends Action>> getAvailableActions() {
        List<Class<? extends Action>> forcedActions =
                availableActions.stream()
                        .filter(
                                action ->
                                        action.getAnnotation(ActionAnnotation.class).value()
                                                == ActionType.FORCED)
                        .toList();

        if (forcedActions.isEmpty()) {
            return availableActions;
        } else {
            return forcedActions;
        }
    }

    /**
     * add an action to the list of available actions
     *
     * @param action the action to add
     */
    public void addAvailableAction(Class<? extends Action> action) {
        this.availableActions.add(action);
    }

    /**
     * add a list of actions to the list of available actions
     *
     * @param actions the list of actions to add
     */
    public void addAvailableActions(List<Class<? extends Action>> actions) {
        this.availableActions.addAll(actions);
    }

    /** add an action to the number of actions to plau this turn */
    public void addAction() {
        numberOfActions++;
    }

    /** clear the list of available actions of the FORCED type */
    private void clearForcedActions() {
        availableActions.removeIf(
                action ->
                        action.getAnnotation(ActionAnnotation.class).value() == ActionType.FORCED);
    }

    /**
     * update an action in available actions
     *
     * @param action the action to update
     * @param actionResult the result of the action
     */
    public void updateAvailableActions(Action action, ActionResult actionResult) {
        this.availableActions.remove(action.getClass());
        this.alreadyDoneActions.add(action.getClass());
        this.clearForcedActions();
        this.addAvailableActions(actionResult.availableActions());
        this.setNumberOfActions(this.getNumberOfActions() - actionResult.cost());
    }

    /**
     * update to the defaults actions
     *
     * @param board the board
     */
    public void updateDefaultActions(Board board, BotState botState) {
        availableActions.removeIf(
                action ->
                        action.getAnnotation(ActionAnnotation.class).value() == ActionType.DEFAULT);
        DEFAULT_AVAILABLE_ACTIONS.stream()
                .filter(
                        actionClass ->
                                DefaultAction.canBePlayed(board, botState, actionClass)
                                        && (actionClass.isAnnotationPresent(
                                                        ActionCanBePlayedMultipleTimesPerTurn.class)
                                                || !alreadyDoneActions.contains(actionClass)
                                                || board.getWeather()
                                                        .map(v -> v.getClass().equals(Windy.class))
                                                        .orElse(false)))
                .forEach(actionClass -> availableActions.add(actionClass));
    }

    /**
     * reset the available actions
     *
     * @param board the board
     */
    public void resetAvailableActions(Board board, BotState botState) {
        this.availableActions = new ArrayList<>(DEFAULT_AVAILABLE_ACTIONS);
        this.alreadyDoneActions = new ArrayList<>();
        updateDefaultActions(board, botState);
    }

    /**
     * get the list of already done actions
     *
     * @return the list of already done actions
     */
    public List<Class<? extends Action>> getAlreadyDoneActions() {
        return new ArrayList<>(alreadyDoneActions);
    }

    public void reset() {
        this.numberOfActions = DEFAULT_NUMBER_OF_ACTIONS;
        this.availableActions = new ArrayList<>();
        this.alreadyDoneActions = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionManager that = (ActionManager) o;
        return numberOfActions == that.numberOfActions
                && Objects.equals(availableActions, that.availableActions)
                && Objects.equals(alreadyDoneActions, that.alreadyDoneActions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfActions, availableActions, alreadyDoneActions);
    }
}
