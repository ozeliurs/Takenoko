package com.takenoko.engine;

import com.takenoko.bot.utils.HistoryAnalysis;
import com.takenoko.stats.HistoryStatistics;
import com.takenoko.stats.HistoryStatisticsItem;

import java.util.*;
import java.util.stream.Collectors;

/** Class that stores the history of a game. */
public class History extends HashMap<UUID, List<TurnHistory>> {

    private UUID currentBotManagerUUID;
    private HistoryStatistics historyStatistics = new HistoryStatistics();

    public History(History historyMap) {
        super(historyMap);
        currentBotManagerUUID = historyMap.currentBotManagerUUID;
        historyStatistics = historyMap.historyStatistics;
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
        historyStatistics.put(botManager, new HistoryStatisticsItem());
    }

    public void addTurnHistory(BotManager botManager, TurnHistory turnHistory) {
        computeIfAbsent(botManager.getUniqueID(), k -> new ArrayList<>()).add(turnHistory);
    }

    public void updateHistoryStatistics(BotManager botManager) {
        if (containsKey(botManager.getUniqueID()) && get(botManager.getUniqueID()).size() > 1) {
            historyStatistics.incrementNumberOfRounds(
                    botManager, HistoryAnalysis.getGameProgress(this));
            historyStatistics.updateEvolution(botManager, this);
        }
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
                .filter(uuidListEntry -> !uuidListEntry.getValue().isEmpty())
                .map(
                        uuidListEntry -> {
                            TurnHistory lastRound =
                                    uuidListEntry
                                            .getValue()
                                            .get(uuidListEntry.getValue().size() - 1);
                            HistoryItem lastHistoryItem = lastRound.get(lastRound.size() - 1);

                            return new AbstractMap.SimpleEntry<>(
                                    uuidListEntry.getKey(), lastHistoryItem);
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

    public HistoryStatistics getHistoryStatistics() {
        return historyStatistics;
    }
}
