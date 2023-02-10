package com.takenoko.engine;

import com.takenoko.bot.utils.GameProgress;

import java.util.HashMap;

public class HistoryStatisticsItem extends HashMap<GameProgress,GameProgressStatistics>{

    public HistoryStatisticsItem(){
        super();
        this.put(GameProgress.EARLY_GAME,new GameProgressStatistics(GameProgress.EARLY_GAME));
        this.put(GameProgress.MID_GAME,new GameProgressStatistics(GameProgress.MID_GAME));
        this.put(GameProgress.LATE_GAME,new GameProgressStatistics(GameProgress.LATE_GAME));
    }

    @Override
    public String toString(){
        StringBuilder fullGameProgress=new StringBuilder();
        for (GameProgressStatistics gameProgressStatistics:values()){
            fullGameProgress.append(gameProgressStatistics.toString())
                    .append("\n");
        }
        return fullGameProgress.toString();
    }
}
