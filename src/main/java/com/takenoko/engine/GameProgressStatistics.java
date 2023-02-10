package com.takenoko.engine;

import com.takenoko.actions.Action;
import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.irrigation.PlaceIrrigationAction;
import com.takenoko.actions.irrigation.StoreIrrigationInInventoryAction;
import com.takenoko.actions.objective.DrawObjectiveAction;
import com.takenoko.actions.tile.PlaceTileAction;
import com.takenoko.bot.utils.GameProgress;
import com.takenoko.objective.Objective;
import com.takenoko.objective.ObjectiveTypes;
import java.util.HashMap;
import java.util.List;

public class GameProgressStatistics {
    private final GameProgress gameProgress;
    private int totalNbOfRounds = 0;
    private int totalNbOfAction = 0;
    private final HashMap<String, Float> actions = new HashMap<>();
    private int totalNbOfRedeemedObjectives = 0;
    private final HashMap<ObjectiveTypes, Float> objectives = new HashMap<>();

    public GameProgressStatistics(GameProgress gameProgress) {
        this.gameProgress = gameProgress;
        actions.put(MovePandaAction.class.getSimpleName(), 0F);
        actions.put(MoveGardenerAction.class.getSimpleName(), 0F);
        actions.put(PlaceIrrigationAction.class.getSimpleName(), 0F);
        actions.put(PlaceTileAction.class.getSimpleName(), 0F);
        actions.put(StoreIrrigationInInventoryAction.class.getSimpleName(), 0F);
        actions.put(DrawObjectiveAction.class.getSimpleName(), 0F);
    }

    public void incrementNbOfRounds() {
        totalNbOfRounds++;
    }

    public void updateActions(Action action) {
        if (action == null) {
            throw new IllegalArgumentException();
        }
        if (actions.containsKey(action.getClass().getSimpleName())) {
            totalNbOfAction++;
            actions.replace(
                    action.getClass().getSimpleName(),
                    actions.get(action.getClass().getSimpleName()) + 1);
        }
    }

    public Float actionPercentage(String action) {
        Float totalOfAction = actions.get(action);
        return (totalOfAction / totalNbOfAction) * 100;
    }

    public Float actionAverage(String action) {
        Float totalOfAction = actions.get(action);
        return (totalOfAction / totalNbOfRounds);
    }

    public void updateRedeemedOneObjective(Objective objective) {
        if (objective == null) {
            throw new IllegalArgumentException();
        }
        totalNbOfRedeemedObjectives++;
        if (objectives.containsKey(objective.getType())) {
            objectives.replace(objective.getType(), objectives.get(objective.getType()) + 1);
        } else {
            objectives.put(objective.getType(), 1F);
        }
    }

    public Float objectiveAverage(ObjectiveTypes objectiveTypes) {
        Float totalOfRedeemedObjective = objectives.get(objectiveTypes);
        return (totalOfRedeemedObjective / totalNbOfRounds);
    }

    public void updateRedeemedObjectives(List<Objective> objectiveList) {
        for (Objective objective : objectiveList) {
            updateRedeemedOneObjective(objective);
        }
    }

    @Override
    public String toString() {
        StringBuilder statistics = new StringBuilder();
        String indent = "\t\t";
        String indentedLineJump = "\n" + indent;
        String doubleIndentedLineJump = indentedLineJump + "\t\t";
        statistics
                .append("****")
                .append(gameProgress)
                .append("****")
                .append(indentedLineJump)
                .append("Total number of rounds : ")
                .append(totalNbOfRounds)
                .append(indent)
                .append("Actions performed : ")
                .append(totalNbOfAction);
        for (String action : actions.keySet()) {
            statistics
                    .append(doubleIndentedLineJump)
                    .append(action)
                    .append("\t Choice Percentage : ")
                    .append(actionPercentage(action))
                    .append("%  |  ")
                    .append("Average use per round : ")
                    .append(actionAverage(action));
        }
        statistics
                .append(indentedLineJump)
                .append("Objectives redeemed : ")
                .append(totalNbOfRedeemedObjectives);
        for (ObjectiveTypes objectiveType : objectives.keySet()) {
            statistics
                    .append(doubleIndentedLineJump)
                    .append(objectiveType)
                    .append("  Average per round : ")
                    .append(objectiveAverage(objectiveType));
        }
        return statistics.toString();
    }

    public void update(TurnHistory turnHistory) {
        for (HistoryItem latestHistoryItems : turnHistory) {
            updateRedeemedObjectives(latestHistoryItems.redeemedObjectives());
            updateActions(latestHistoryItems.action());
        }
    }
}
