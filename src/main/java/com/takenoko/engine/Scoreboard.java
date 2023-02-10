package com.takenoko.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class is used to store the number of win for each bot. */
public class Scoreboard {
    private final HashMap<BotManager, Integer> numberOfVictoryHashMap;
    private final HashMap<BotManager, Integer> totalScore;

    public Scoreboard() {
        totalScore = new HashMap<>();
        numberOfVictoryHashMap = new HashMap<>();
    }

    public Map<BotManager, Integer> getTotalScore() {
        return totalScore;
    }

    public void addBotManager(BotManager botManager) {
        totalScore.put(botManager, 0);
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

    public void updateScore(BotManager botManager, int scoreToAdd) {
        totalScore.put(botManager, totalScore.get(botManager) + scoreToAdd);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String lineJump = "\n \t \t \t";
        stringBuilder.append("============== Scoreboard ==============").append(lineJump);
        for (Map.Entry<BotManager, Integer> entry : numberOfVictoryHashMap.entrySet()) {
            stringBuilder
                    .append("< ")
                    .append(entry.getKey().getName())
                    .append(" : Wins-")
                    .append(entry.getValue())
                    .append(" Total Score-")
                    .append(totalScore.get(entry.getKey()))
                    .append(" > | ")
                    .append(lineJump);
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
