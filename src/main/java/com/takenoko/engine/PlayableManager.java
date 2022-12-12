package com.takenoko.engine;

import com.takenoko.Board;
import com.takenoko.objective.Objective;
import com.takenoko.objective.TwoAdjacentTilesObjective;
import com.takenoko.ui.ConsoleUserInterface;

public abstract class PlayableManager {
    // CONSTANTS
    private static final int DEFAULT_NUMBER_OF_ACTIONS = 2;
    private static final int DEFAULT_NUMBER_OF_ROUNDS = 10;
    // ATTRIBUTES
    private final int numberOfActions;
    private final int numberOfRounds;
    private Objective objective;
    private final ConsoleUserInterface consoleUserInterface;

    protected PlayableManager() {
        numberOfActions = DEFAULT_NUMBER_OF_ACTIONS;
        numberOfRounds = DEFAULT_NUMBER_OF_ROUNDS;
        objective = new TwoAdjacentTilesObjective();
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

    protected int getNumberOfRounds() {
        return numberOfRounds;
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
            objective.verify(board);
        }
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }
}
