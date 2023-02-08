package com.takenoko.bot.utils;

import com.takenoko.actions.actors.ForcedMovePandaAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.engine.GameEngine;
import com.takenoko.engine.History;
import com.takenoko.objective.Objective;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/** Class that analyses the history of a game. */
public class HistoryAnalysis {

    private static final double MID_GAME_THRESHOLD = 0.25;
    private static final double LATE_GAME_THRESHOLD = 0.75;

    private HistoryAnalysis() {}

    /**
     * Retrieves the current score for each bot manager in the history.
     *
     * @param history the history to analyse
     * @return a map of the current score for each bot manager in the history.
     */
    public static Map<UUID, Integer> getCurrentBotScores(History history) {
        return history.getLatestHistoryItems().entrySet().stream()
                .map(
                        uuidHistoryItemEntry ->
                                Map.entry(
                                        uuidHistoryItemEntry.getKey(),
                                        uuidHistoryItemEntry
                                                .getValue()
                                                .redeemedObjectives()
                                                .stream()
                                                .mapToInt(Objective::getPoints)
                                                .sum()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Calculates the maximum current score of all bot managers in the history.
     *
     * @param history the history to analyse
     * @return the maximum current score of all bot managers in the history.
     */
    public static int getMaxCurrentBotScore(History history) {
        return Collections.max(getCurrentBotScores(history).values());
    }

    /**
     * Calculate the game progress as an Link{@code GameProgress} Enum.
     *
     * @param history the history to analyse
     * @return the game progress as an Link{@code GameProgress} Enum.
     */
    public static GameProgress getGameProgress(History history) {
        // if no-one has completed less that MID_GAME_THRESHOLD objectives, the game is in the
        // early game
        // if someone has completed more than LATE_GAME_THRESHOLD objectives, the game is in the
        // late game
        // otherwise, the game is in the mid-game
        if (history.getLatestHistoryItems().values().stream()
                .allMatch(
                        historyItem ->
                                historyItem.redeemedObjectives().size()
                                                / (double)
                                                        GameEngine
                                                                .DEFAULT_NUMBER_OF_OBJECTIVES_TO_WIN
                                                                .get(history.keySet().size())
                                        < MID_GAME_THRESHOLD)) {
            return GameProgress.EARLY_GAME;
        }
        if (history.getLatestHistoryItems().values().stream()
                .anyMatch(
                        historyItem ->
                                historyItem.redeemedObjectives().size()
                                                / (double)
                                                        GameEngine
                                                                .DEFAULT_NUMBER_OF_OBJECTIVES_TO_WIN
                                                                .get(history.keySet().size())
                                        > LATE_GAME_THRESHOLD)) {
            return GameProgress.LATE_GAME;
        }
        return GameProgress.MID_GAME;
    }

    /**
     * Analyse the history and determine the bots which are using the Rush Panda strategy.
     *
     * @param history the history to analyse
     * @return a map of the bot managers which are using the Rush Panda strategy.
     */
    static Map<UUID, Boolean> analyzeRushPanda(History history, double threshold) {
        // if the bot has moved a panda more than threshold times, it is using the Rush Panda
        // strategy

        return history.entrySet().stream()
                .map(
                        uuidHistoryEntry ->
                                Map.entry(
                                        uuidHistoryEntry.getKey(),
                                        uuidHistoryEntry.getValue().stream()
                                                                .filter(
                                                                        historyItem ->
                                                                                historyItem
                                                                                                .action()
                                                                                                .getClass()
                                                                                                .equals(
                                                                                                        MovePandaAction
                                                                                                                .class)
                                                                                        || historyItem
                                                                                                .action()
                                                                                                .getClass()
                                                                                                .equals(
                                                                                                        ForcedMovePandaAction
                                                                                                                .class))
                                                                .count()
                                                        / (double)
                                                                history.get(
                                                                                uuidHistoryEntry
                                                                                        .getKey())
                                                                        .size()
                                                > threshold))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    static Map<UUID, Boolean> analyzeRushPanda(History history) {
        return analyzeRushPanda(history, 0.3);
    }
}
