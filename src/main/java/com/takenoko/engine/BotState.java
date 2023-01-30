package com.takenoko.engine;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.inventory.Inventory;
import com.takenoko.objective.Objective;
import com.takenoko.objective.PandaObjective;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** This class is used to store the state of a bot. */
public class BotState { // DEFAULT VALUES
    private static final int DEFAULT_NUMBER_OF_ACTIONS = 2;
    private static final Objective DEFAULT_OBJECTIVE = new PandaObjective(0);

    private int numberOfActions;
    private List<Objective> objectives;
    private List<Objective> achievedObjectives;
    private final Inventory inventory;
    private List<Class<? extends Action>> availableActions;
    private int objectiveScore;

    public BotState(
            int numberOfActions,
            List<Objective> objectives,
            Inventory inventory,
            List<Class<? extends Action>> availableActions) {
        this.numberOfActions = numberOfActions;
        this.objectives = objectives;
        this.inventory = inventory;
        this.availableActions = availableActions;
    }

    public BotState() {
        this(DEFAULT_NUMBER_OF_ACTIONS, List.of(DEFAULT_OBJECTIVE), new Inventory(), new ArrayList<>());
    }

    public BotState(BotState botState) {
        this.numberOfActions = botState.numberOfActions;
        // Objectives
        this.objectives = new ArrayList<>();
        for (Objective objective : botState.objectives) {
            this.objectives.add(objective.copy());
        }
        // Achieved objectives
        this.achievedObjectives = new ArrayList<>();
        for (Objective objective : botState.achievedObjectives) {
            this.achievedObjectives.add(objective.copy());
        }
        this.inventory = botState.getInventory().copy();
        this.availableActions = new ArrayList<>(botState.availableActions);
        this.objectiveScore = botState.objectiveScore;
    }

    public void setNumberOfActions(int numberOfActions) {
        this.numberOfActions = numberOfActions;
    }

    /**
     * Get the current Objectives of the bot
     *
     * @return Objectives
     */
    public List<Objective> getObjectives() {
        return objectives;
    }

    /**
     * Set the current Objective of the bot
     *
     * @param objective the objectives
     */
    public void addObjective(Objective objective) {
        this.objectives.add(objective);
    }

    /**
     * @return number of actions the bot can do in a turn
     */
    protected int getNumberOfActions() {
        return numberOfActions;
    }

    /**
     * @return the number of bamboo eaten by the bot
     */
    public int getEatenBambooCounter() {
        return inventory.getBambooCount();
    }

    /**
     * Return the bot inventory
     *
     * @return the bot inventory
     */
    public Inventory getInventory() {
        return inventory;
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
     * Set the list of available actions
     *
     * @param availableActions the list of available actions
     */
    public void setAvailableActions(List<Class<? extends Action>> availableActions) {
        this.availableActions = availableActions;
    }

    public void addAvailableAction(Class<? extends Action> action) {
        this.availableActions.add(action);
    }

    public void addAvailableActions(List<Class<? extends Action>> actions) {
        this.availableActions.addAll(actions);
    }

    public void addAction() {
        numberOfActions++;
    }

    public void reset() {
        this.objectives.clear();
        this.inventory.clear();
        this.objectiveScore = 0;
    }

    public BotState copy() {
        return new BotState(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotState botState = (BotState) o;
        return getNumberOfActions() == botState.getNumberOfActions()
                && getObjectives().equals(botState.getObjectives())
                && getInventory().equals(botState.getInventory())
                && getObjectiveScore() == botState.getObjectiveScore();
    }

    public int getObjectiveScore() {
        return objectiveScore;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumberOfActions(), getObjectives(), getInventory());
    }

    private void clearForcedActions() {
        availableActions.removeIf(
                action ->
                        action.getAnnotation(ActionAnnotation.class).value() == ActionType.FORCED);
    }

    public void updateAvailableActions(Action action, ActionResult actionResult) {
        this.availableActions.remove(action.getClass());
        this.clearForcedActions();
        this.addAvailableActions(actionResult.availableActions());
        this.setNumberOfActions(this.getNumberOfActions() - actionResult.cost());
    }

    public void incrementScore(int i) {
        this.objectiveScore += i;
    }

    public void setObjectiveAchieved(Objective objective) {
        this.objectives.remove(objective);
        this.achievedObjectives.add(objective);
    }

    public List<Objective> getAchievedObjectives() {
        return achievedObjectives;
    }
}
