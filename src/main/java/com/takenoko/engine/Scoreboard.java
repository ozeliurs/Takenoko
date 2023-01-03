package com.takenoko.engine;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Scoreboard {
    private final HashMap<UUID, BotManager> botManagerHashMap;
    private final HashMap<UUID, Integer> scoreHashMap;
    private int numberOfGamesPlayed;

    public Scoreboard() {
        numberOfGamesPlayed = 0;
        botManagerHashMap = new HashMap<>();
        scoreHashMap = new HashMap<>();
    }

    public void addBotManager(BotManager botManager) {
        botManagerHashMap.put(botManager.getBotId(), botManager);
        scoreHashMap.put(botManager.getBotId(), 0);
    }

    public void addBotManager(List<BotManager> botManagerList) {
        for (BotManager botManager : botManagerList) {
            addBotManager(botManager);
        }
    }

    public void addScore(UUID botId, int score) {
        scoreHashMap.put(botId, scoreHashMap.get(botId) + score);
    }

    public void incrementNumberOfGamesPlayed() {
        numberOfGamesPlayed++;
    }

    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Scoreboard:").append(System.lineSeparator());
        for (UUID botId : botManagerHashMap.keySet()) {
            stringBuilder.append(botManagerHashMap.get(botId).getName());
            stringBuilder.append(" : ");
            stringBuilder.append(scoreHashMap.get(botId));
            stringBuilder.append(" - ");
        }
        return stringBuilder.toString();
    }

    public int getScore(UUID botId) {
        return scoreHashMap.get(botId);
    }
}
