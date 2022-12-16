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
    private final String name;

    protected PlayableManager(
            int numberOfActions,
            Objective objective,
            ConsoleUserInterface consoleUserInterface,
            String name) {
        this.numberOfActions = numberOfActions;
        this.objective = objective;
        this.consoleUserInterface = consoleUserInterface;
        this.name = name;
    }

    protected PlayableManager() {
        this(
                DEFAULT_NUMBER_OF_ACTIONS,
                new EatBambooObjective(2),
                new ConsoleUserInterface(),
                "Default Name");
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

    public String getName() {
        return name;
    }

    public int getEatenBambooCounter() {
        return eatenBambooCounter;
    }

    public void incrementBambooCounter() {
        eatenBambooCounter++;
    }
}
