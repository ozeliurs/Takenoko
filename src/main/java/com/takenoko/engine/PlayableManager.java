package com.takenoko.engine;

import com.takenoko.Board;
import com.takenoko.objective.Objective;
import com.takenoko.objective.PlaceTileObjective;
import com.takenoko.ui.ConsoleUserInterface;

public abstract class PlayableManager {
    private static final int DEFAULT_NUMBER_OF_ACTIONS = 2;
    private final int numberOfActions;
    private final Objective placeTileObjective;
    private ConsoleUserInterface consoleUserInterface;

    protected PlayableManager() {
        numberOfActions = DEFAULT_NUMBER_OF_ACTIONS;
        placeTileObjective = new PlaceTileObjective(2);
        consoleUserInterface = new ConsoleUserInterface();
    }

    public void setConsoleUserInterface(ConsoleUserInterface consoleUserInterface) {
        this.consoleUserInterface = consoleUserInterface;
    }

    public String getObjectiveDescription() {
        return placeTileObjective.toString();
    }

    protected int getNumberOfActions() {
        return numberOfActions;
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
