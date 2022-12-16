package com.takenoko.engine;

import com.takenoko.objective.EatBambooObjective;
import com.takenoko.objective.Objective;
import com.takenoko.ui.ConsoleUserInterface;

public abstract class PlayableManager {
    // CONSTANTS
    private static final int DEFAULT_NUMBER_OF_ACTIONS = 2;
    // ATTRIBUTES
    private final int numberOfActions;
    private Objective objective;
    private final ConsoleUserInterface consoleUserInterface;
    private int eatenBambooCounter = 0;

    protected PlayableManager() {
        numberOfActions = DEFAULT_NUMBER_OF_ACTIONS;
        objective = new EatBambooObjective(1);
        consoleUserInterface = new ConsoleUserInterface();
    }

    public String getObjectiveDescription() {
        if (objective != null) {
            return objective.toString();
        }
        return "No current objective";
    }

    protected int getNumberOfActions() {
        return numberOfActions;
    }

    public void displayMessage(String message) {
        consoleUserInterface.displayMessage(message);
    }

    public boolean isObjectiveAchieved() {
        if (objective != null) {
            return objective.isAchieved();
        }
        return false;
    }

    public void verifyObjective(Board board) {
        if (objective != null) {
            objective.verify(board, this);
        }
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public int getEatenBambooCounter() {
        return eatenBambooCounter;
    }

    public void incrementBambooCounter() {
        eatenBambooCounter++;
    }
}
