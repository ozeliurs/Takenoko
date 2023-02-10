package com.takenoko.stats;

import com.takenoko.bot.utils.GameProgress;
import com.takenoko.bot.utils.HistoryAnalysis;
import com.takenoko.engine.History;

import java.util.HashMap;
import java.util.UUID;

public class HistoryStatisticsItem extends HashMap<GameProgress,GameProgressStatistics>{

    public HistoryStatisticsItem(){
        super();
        this.put(GameProgress.EARLY_GAME,new GameProgressStatistics(GameProgress.EARLY_GAME));
        this.put(GameProgress.MID_GAME,new GameProgressStatistics(GameProgress.MID_GAME));
        this.put(GameProgress.LATE_GAME,new GameProgressStatistics(GameProgress.LATE_GAME));
    }

    @Override
    public String toString() {
        StringBuilder fullGameProgress = new StringBuilder();
        fullGameProgress
                .append(get(GameProgress.EARLY_GAME))
                .append("\n")
                .append(get(GameProgress.MID_GAME))
                .append("\n")
                .append(get(GameProgress.LATE_GAME))
                .append("\n");
        return fullGameProgress.toString();
    }

    public void updateEvolution(History history, UUID uniqueID) {
        get(HistoryAnalysis.getGameProgress(history))
                .update(history.get(uniqueID).get(history.get(uniqueID).size() - 1));
    }
}
