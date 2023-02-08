package com.takenoko.bot.utils;

import com.takenoko.engine.History;
import com.takenoko.objective.Objective;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class HistoryAnalysis {

    private HistoryAnalysis() {
    }

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
}
