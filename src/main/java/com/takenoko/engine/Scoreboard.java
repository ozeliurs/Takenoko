package com.takenoko.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scoreboard {
    private final HashMap<BotManager, Integer> numberOfVictoryHashMap;

    public Scoreboard() {
        numberOfVictoryHashMap = new HashMap<>();
    }

    public void addBotManager(BotManager botManager) {
        numberOfVictoryHashMap.put(botManager, 0);
    }

    public void addBotManager(List<BotManager> botManagerList) {
        for (BotManager botManager : botManagerList) {
            addBotManager(botManager);
        }
    }

    public void incrementNumberOfVictory(BotManager botManager) {
        numberOfVictoryHashMap.put(botManager, numberOfVictoryHashMap.get(botManager) + 1);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("============== Scoreboard ==============")
                .append(System.lineSeparator());
        for (Map.Entry<BotManager, Integer> entry : numberOfVictoryHashMap.entrySet()) {
            stringBuilder
                    .append("< ")
                    .append(entry.getKey().getName())
                    .append(" : ")
                    .append(entry.getValue())
                    .append(" > | ");
        }
        return stringBuilder.toString();
    }

    public int getNumberOfVictory(BotManager botManager) {
        return numberOfVictoryHashMap.get(botManager);
    }

    public List<BotManager> getBotManagers() {
        return List.copyOf(numberOfVictoryHashMap.keySet());
    }
}
