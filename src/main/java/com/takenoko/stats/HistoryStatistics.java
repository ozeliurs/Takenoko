package com.takenoko.stats;

import com.takenoko.bot.utils.GameProgress;
import com.takenoko.engine.BotManager;
import com.takenoko.engine.History;
import java.util.HashMap;
import java.util.List;

public class HistoryStatistics extends HashMap<BotManager, HistoryStatisticsItem> {

    public HistoryStatistics() {
        super();
    }

    public HistoryStatistics(List<BotManager> botManagers) {
        for (BotManager botManager : botManagers) {
            put(botManager, new HistoryStatisticsItem());
        }
    }

    @Override
    public String toString() {
        StringBuilder historyStatistics = new StringBuilder();
        for (Entry<BotManager, HistoryStatisticsItem> entry : entrySet()) {
            historyStatistics
                    .append("======")
                    .append(entry.getKey())
                    .append("======\n")
                    .append(entry.getValue())
                    .append("\n");
        }
        return historyStatistics.toString();
    }

    public void updateEvolution(BotManager botManager, History history) {
        get(botManager).updateEvolution(history, botManager.getUniqueID());
    }

    public void incrementNumberOfRounds(BotManager botManager, GameProgress gameProgress) {
        get(botManager).get(gameProgress).incrementNbOfRounds();
    }
}
