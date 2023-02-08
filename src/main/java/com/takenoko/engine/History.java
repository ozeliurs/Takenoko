package com.takenoko.engine;

import java.util.*;

public class History {

    private final Map<UUID, List<HistoryItem>> historyMap;

    public History(Map<UUID, List<HistoryItem>> historyMap) {
        this.historyMap = historyMap;
    }

    public History(History historyMap) {
        this.historyMap = new HashMap<>(historyMap.historyMap);
    }

    public History() {
        this.historyMap = new HashMap<>();
    }

    public History getHistoryWithoutCurrentBotManager(BotManager botManager) {
        History copy = copy();
        copy.historyMap.remove(botManager.getUniqueID());
        return copy;
    }

    public void addBotManager(BotManager botManager) {
        historyMap.put(
                botManager.getUniqueID(),
                new ArrayList<>(List.of(new HistoryItem(null, List.of()))));
    }

    public void addHistoryItem(BotManager botManager, HistoryItem historyItem) {
        historyMap
                .computeIfAbsent(botManager.getUniqueID(), k -> new ArrayList<>())
                .add(historyItem);
    }

    public List<HistoryItem> getHistory(UUID uniqueID) {
        return historyMap.computeIfAbsent(uniqueID, k -> new ArrayList<>());
    }

    public List<UUID> getBotManagerUUIDs() {
        return new ArrayList<>(historyMap.keySet());
    }

    public History copy() {
        return new History(this);
    }

    public List<HistoryItem> getLatestHistoryItem() {
        return historyMap.values().stream()
                .map(
                        historyItems -> {
                            ArrayList<HistoryItem> reversed = new ArrayList<>(historyItems);
                            Collections.reverse(reversed);
                            return reversed.get(0);
                        })
                .toList();
    }
}
