package com.takenoko.stats;

import com.takenoko.engine.BotManager;
import java.util.HashMap;
import java.util.List;

/** This class stores extended statistics for each BotManager */
public class BotStatistics extends HashMap<BotManager, SingleBotStatistics> {

    public void addBotManager(BotManager botManager) {
        this.put(botManager, botManager.getSingleBotStatistics());
    }

    public void addBotManagers(List<BotManager> botManagers) {
        for (BotManager botManager : botManagers) {
            addBotManager(botManager);
        }
    }

    public void incrementWins(BotManager botManager) {
        get(botManager).incrementWins();
    }

    public void incrementLosses(BotManager botManager) {
        get(botManager).incrementLosses();
    }

    public void updateScore(BotManager botManager, int toAdd) {
        get(botManager).updateScore(toAdd);
    }

    @Override
    public String toString() {
        StringBuilder fullStats = new StringBuilder();
        String lineJump = "\n \t \t \t";
        for (BotManager botManager : this.keySet()) {
            fullStats
                    .append("===========")
                    .append(botManager)
                    .append("===========")
                    .append(lineJump)
                    .append(get(botManager))
                    .append(lineJump);
        }
        return fullStats.toString();
    }
}
