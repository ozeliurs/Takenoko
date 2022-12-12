package com.takenoko.engine;

import com.takenoko.Board;
import com.takenoko.objective.Objective;
import com.takenoko.objective.PlaceTileObjective;
import com.takenoko.ui.ConsoleUserInterface;

public abstract class PlayableManager {
    // CONSTANTS
    private static final int DEFAULT_NUMBER_OF_ACTIONS = 2;
    private static final int DEFAULT_NUMBER_OF_ROUNDS = 10;
    // ATTRIBUTES
    private final int numberOfActions;
    private final int numberOfRounds;
    private final Objective placeTileObjective;
    private final ConsoleUserInterface consoleUserInterface;

    protected PlayableManager() {
        numberOfActions = DEFAULT_NUMBER_OF_ACTIONS;
        numberOfRounds = DEFAULT_NUMBER_OF_ROUNDS;
        placeTileObjective = new PlaceTileObjective(2);
        consoleUserInterface = new ConsoleUserInterface();
    }

    public String getObjectiveDescription() {
        return placeTileObjective.toString();
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

    public boolean objectiveIsAchieved() {
        return placeTileObjective.isAchieved();
    }

    public void verifyObjective(Board board) {
        placeTileObjective.verify(board);
    }
}
