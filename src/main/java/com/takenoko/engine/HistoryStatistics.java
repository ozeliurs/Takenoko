package com.takenoko.engine;

import java.util.HashMap;
import java.util.List;

public class HistoryStatistics extends HashMap<BotManager,HistoryStatisticsItem> {

    public HistoryStatistics(List<BotManager> botManagers){
        for(BotManager botManager:botManagers){
            put(botManager,botManager.getHistoryStatisticsItem());
        }
    }

    @Override
    public String toString(){
        StringBuilder historyStatistics=new StringBuilder();
        for(Entry<BotManager, HistoryStatisticsItem> entry:entrySet()){
            historyStatistics.append("======")
                    .append(entry.getKey())
                    .append("======\n")
                    .append(entry.getValue())
                    .append("\n");
        }
        return historyStatistics.toString();
    }
}
