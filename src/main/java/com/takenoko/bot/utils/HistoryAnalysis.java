package com.takenoko.bot.utils;

import com.takenoko.engine.History;
import com.takenoko.objective.Objective;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class HistoryAnalysis {
    private final History history;

    public HistoryAnalysis(History history) {
        this.history = history;
    }

    public Map<UUID, Integer> getBotScores() {
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

    public int getMaxBotScore() {
        return Collections.max(getBotScores().values());
    }
}
