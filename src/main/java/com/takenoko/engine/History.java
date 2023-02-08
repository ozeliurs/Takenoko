package com.takenoko.engine;

import java.util.*;
import java.util.stream.Collectors;

/** Class that stores the history of a game. */
public class History extends HashMap<UUID, List<HistoryItem>> {

    private UUID currentBotManagerUUID;

    public History(History historyMap) {
        super(historyMap);
        currentBotManagerUUID = historyMap.currentBotManagerUUID;
    }

    public History() {
        super();
    }

    public UUID getCurrentBotManagerUUID() {
        return currentBotManagerUUID;
    }

    public void setCurrentBotManagerUUID(UUID currentBotManagerUUID) {
        this.currentBotManagerUUID = currentBotManagerUUID;
    }

    public void addBotManager(BotManager botManager) {
        put(botManager.getUniqueID(), new ArrayList<>(List.of()));
    }

    public void addHistoryItem(BotManager botManager, HistoryItem historyItem) {
        computeIfAbsent(botManager.getUniqueID(), k -> new ArrayList<>()).add(historyItem);
    }

    public History copy() {
        return new History(this);
    }

    /**
     * Returns a map of the latest history item for each bot manager in the history.
     *
     * @return a map of the latest history item for each bot manager.
     */
    public Map<UUID, HistoryItem> getLatestHistoryItems() {
        return entrySet().stream()
                .map(
                        uuidListEntry -> {
                            ArrayList<HistoryItem> reversed =
                                    new ArrayList<>(uuidListEntry.getValue());
                            Collections.reverse(reversed);
                            return new AbstractMap.SimpleEntry<>(
                                    uuidListEntry.getKey(), reversed.get(0));
                        })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        History history = (History) o;
        return Objects.equals(getCurrentBotManagerUUID(), history.getCurrentBotManagerUUID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCurrentBotManagerUUID());
    }
}
