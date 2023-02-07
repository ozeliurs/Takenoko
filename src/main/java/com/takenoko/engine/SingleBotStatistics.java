package com.takenoko.engine;

import com.takenoko.layers.tile.TileColor;
import com.takenoko.objective.Objective;
import com.takenoko.objective.ObjectiveTypes;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/** This class represents an extended list of statistics for a specific BotManager */
public class SingleBotStatistics {
    private int wins;
    private int losses;
    private final HashMap<ObjectiveTypes, Integer> objectivesRedeemed;
    private final HashMap<TileColor, Integer> bamboosEaten;
    private final HashMap<TileColor, Integer> bamboosPlanted;
    private int irrigationsPlaced;
    private int finalScore;
    private final HashMap<TileColor, Integer> tilesPlaced;

    public SingleBotStatistics(
            int wins,
            int losses,
            Map<ObjectiveTypes, Integer> objectivesRedeemed,
            Map<TileColor, Integer> bamboosEaten,
            Map<TileColor, Integer> bamboosPlanted,
            int irrigationsPlaced,
            int finalScore,
            Map<TileColor, Integer> tilesPlaced) {
        this.wins = wins;
        this.losses = losses;
        this.objectivesRedeemed = (HashMap<ObjectiveTypes, Integer>) objectivesRedeemed;
        this.bamboosEaten = (HashMap<TileColor, Integer>) bamboosEaten;
        this.bamboosPlanted = (HashMap<TileColor, Integer>) bamboosPlanted;
        this.irrigationsPlaced = irrigationsPlaced;
        this.finalScore = finalScore;
        this.tilesPlaced = (HashMap<TileColor, Integer>) tilesPlaced;
    }

    public SingleBotStatistics(SingleBotStatistics singleBotStatistics) {
        this(
                singleBotStatistics.wins,
                singleBotStatistics.losses,
                singleBotStatistics.objectivesRedeemed,
                singleBotStatistics.bamboosEaten,
                singleBotStatistics.bamboosPlanted,
                singleBotStatistics.irrigationsPlaced,
                singleBotStatistics.finalScore,
                singleBotStatistics.tilesPlaced);
    }

    public SingleBotStatistics() {
        this(0, 0, new HashMap<>(), new HashMap<>(), new HashMap<>(), 0, 0, new HashMap<>());
    }

    public void incrementWins() {
        wins++;
    }

    public void incrementLosses() {
        losses++;
    }

    public void incrementIrrigationsPlaced() {
        irrigationsPlaced++;
    }

    public void updateScore(int toAdd) {
        finalScore += toAdd;
    }

    public void updateObjectivesRedeemed(Objective objective) {
        if (objective == null) {
            throw new IllegalArgumentException();
        }
        ObjectiveTypes objectiveType = objective.getType();
        if (objectivesRedeemed.containsKey(objectiveType)) {
            objectivesRedeemed.replace(objectiveType, objectivesRedeemed.get(objectiveType) + 1);
        } else {
            objectivesRedeemed.put(objectiveType, 1);
        }
    }

    public void updateEatenBambooCounter(TileColor tileColor) {
        if (tileColor == null) {
            throw new IllegalArgumentException();
        }
        if (bamboosEaten.containsKey(tileColor)) {
            bamboosEaten.put(tileColor, bamboosEaten.get(tileColor) + 1);
        } else {
            bamboosEaten.put(tileColor, 1);
        }
    }

    public void updatePlantedBambooCounter(TileColor tileColor) {
        if (tileColor == null) {
            throw new IllegalArgumentException();
        }
        if (bamboosPlanted.containsKey(tileColor)) {
            bamboosPlanted.put(tileColor, bamboosPlanted.get(tileColor) + 1);
        } else {
            bamboosPlanted.put(tileColor, 1);
        }
    }

    public void updateTilesPlacedCounter(TileColor tileColor) {
        if (tileColor == null) {
            throw new IllegalArgumentException();
        }
        if (tilesPlaced.containsKey(tileColor)) {
            tilesPlaced.put(tileColor, tilesPlaced.get(tileColor) + 1);
        } else {
            tilesPlaced.put(tileColor, 1);
        }
    }

    public SingleBotStatistics copy() {
        return new SingleBotStatistics(this);
    }

    public void reset() {
        this.wins = 0;
        this.losses = 0;
        this.objectivesRedeemed.clear();
        this.bamboosEaten.clear();
        this.bamboosPlanted.clear();
        this.irrigationsPlaced = 0;
        this.finalScore = 0;
    }

    public String toString() {
        StringBuilder singleBotStat = new StringBuilder();
        String lineJump = "\n \t \t \t";
        String indentation = "\t\t* ";
        TreeMap<ObjectiveTypes, Integer> sortedObjectives = new TreeMap<>(objectivesRedeemed);
        TreeMap<TileColor, Integer> sortedBamboosEaten = new TreeMap<>(bamboosEaten);
        TreeMap<TileColor, Integer> sortedBamboosGrown = new TreeMap<>(bamboosPlanted);
        TreeMap<TileColor, Integer> sortedTilesPlaced = new TreeMap<>(tilesPlaced);
        singleBotStat
                .append("\t -Number of wins : ")
                .append(wins)
                .append(lineJump)
                .append("\t -Number of Losses : ")
                .append(losses)
                .append(lineJump)
                .append("\t -Total final score : ")
                .append(finalScore)
                .append(lineJump)
                .append("\t -Number of irrigations placed : ")
                .append(irrigationsPlaced)
                .append(lineJump)
                .append("\t -Redeemed objectives : ");
        for (ObjectiveTypes objectiveType : sortedObjectives.keySet()) {
            singleBotStat
                    .append(lineJump)
                    .append(indentation)
                    .append(objectiveType)
                    .append(" : ")
                    .append(objectivesRedeemed.get(objectiveType));
        }
        singleBotStat.append(lineJump).append("\t -Bamboos eaten :");
        for (TileColor tileColor : sortedBamboosEaten.keySet()) {
            singleBotStat
                    .append(lineJump)
                    .append(indentation)
                    .append(tileColor)
                    .append(" : ")
                    .append(bamboosEaten.get(tileColor));
        }
        singleBotStat.append(lineJump).append("\t -Bamboos grown :");
        for (TileColor tileColor : sortedBamboosGrown.keySet()) {
            singleBotStat
                    .append(lineJump)
                    .append(indentation)
                    .append(tileColor)
                    .append(" : ")
                    .append(bamboosPlanted.get(tileColor));
        }
        singleBotStat.append(lineJump).append("\t -Tiles placed :");
        for (TileColor tileColor : sortedTilesPlaced.keySet()) {
            singleBotStat
                    .append(lineJump)
                    .append(indentation)
                    .append(tileColor)
                    .append(" : ")
                    .append(tilesPlaced.get(tileColor));
        }
        return singleBotStat.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleBotStatistics that = (SingleBotStatistics) o;
        return wins == that.wins
                && losses == that.losses
                && irrigationsPlaced == that.irrigationsPlaced
                && finalScore == that.finalScore
                && objectivesRedeemed.equals(that.objectivesRedeemed)
                && bamboosEaten.equals(that.bamboosEaten)
                && bamboosPlanted.equals(that.bamboosPlanted)
                && tilesPlaced.equals(that.tilesPlaced);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                wins,
                losses,
                objectivesRedeemed,
                bamboosEaten,
                bamboosPlanted,
                irrigationsPlaced,
                finalScore,
                tilesPlaced);
    }
}
