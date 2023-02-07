package com.takenoko.bot.utils;

import com.takenoko.engine.History;
import com.takenoko.objective.Objective;
import java.util.Collections;
import java.util.List;

public class HistoryAnalysis {
    private final History history;

    public HistoryAnalysis(History history) {
        this.history = history;
    }

    public List<Integer> getBotScores() {
        return history.getLatestHistoryItem().stream()
                .map(
                        historyItem ->
                                historyItem.redeemedObjectives().stream()
                                        .mapToInt(Objective::getPoints)
                                        .sum())
                .toList();
    }

    public int getMaxBotScore() {
        return Collections.max(getBotScores());
    }
}
