package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HistoryTest {
    @Nested
    @DisplayName("method addBotManager")
    class AddBotManager {
        @Test
        @DisplayName("Should add a BotManager to the history")
        void shouldAddBotManager() {
            BotManager botManager = mock(BotManager.class);
            History history = new History();
            history.addBotManager(botManager);
            assertThat(history).containsEntry(botManager.getUniqueID(), List.of());
        }
    }

    @Nested
    @DisplayName("method setCurrentBotManagerUUID")
    class SetCurrentBotManagerUUID {
        @Test
        @DisplayName("Should set the current BotManager UUID")
        void shouldSetCurrentBotManagerUUID() {
            BotManager botManager = mock(BotManager.class);
            History history = new History();
            history.addBotManager(botManager);
            history.setCurrentBotManagerUUID(botManager.getUniqueID());
            assertThat(history.getCurrentBotManagerUUID()).isEqualTo(botManager.getUniqueID());
        }
    }

    @Nested
    @DisplayName("method getLatestHistoryItems")
    class GetLatestHistoryItems {
        @Test
        @DisplayName("Should return the latest history items")
        void shouldReturnLatestHistoryItems() {
            BotManager botManager = mock(BotManager.class);
            when(botManager.getUniqueID()).thenReturn(UUID.randomUUID());
            History history = new History();
            HistoryItem latestHistoryItem = mock(HistoryItem.class);
            history.addBotManager(botManager);
            history.setCurrentBotManagerUUID(botManager.getUniqueID());
            history.addTurnHistory(
                    botManager, new TurnHistory(mock(HistoryItem.class), mock(HistoryItem.class)));
            history.addTurnHistory(
                    botManager, new TurnHistory(mock(HistoryItem.class), latestHistoryItem));

            BotManager botManager2 = mock(BotManager.class);
            when(botManager2.getUniqueID()).thenReturn(UUID.randomUUID());
            HistoryItem latestHistoryItem2 = mock(HistoryItem.class);
            history.addBotManager(botManager2);
            history.setCurrentBotManagerUUID(botManager2.getUniqueID());
            history.addTurnHistory(
                    botManager2, new TurnHistory(mock(HistoryItem.class), mock(HistoryItem.class)));
            history.addTurnHistory(
                    botManager2, new TurnHistory(mock(HistoryItem.class), latestHistoryItem2));

            assertThat(history.getLatestHistoryItems())
                    .hasSize(2)
                    .containsEntry(botManager.getUniqueID(), latestHistoryItem)
                    .containsEntry(botManager2.getUniqueID(), latestHistoryItem2);
        }
    }

    @Nested
    @DisplayName("method equals")
    class Equals {
        @Test
        @DisplayName("Should return true when comparing the same object")
        void equals_shouldReturnTrueWhenComparingSameObject() {
            History history = new History();
            assertThat(history).isEqualTo(history);
        }

        @Test
        @DisplayName("Should return true when comparing two empty histories")
        void shouldReturnTrueWhenComparingTwoEmptyHistories() {
            History history1 = new History();
            History history2 = new History();
            assertThat(history1).isEqualTo(history2);
        }

        @Test
        @DisplayName("Should return true when comparing two histories with the same data")
        void shouldReturnTrueWhenComparingTwoHistoriesWithSameData() {
            UUID botManagerUUID = UUID.randomUUID();
            HistoryItem historyItem = mock(HistoryItem.class);

            BotManager botManager = mock(BotManager.class);
            when(botManager.getUniqueID()).thenReturn(botManagerUUID);
            History history1 = new History();
            HistoryItem latestHistoryItem = mock(HistoryItem.class);
            history1.addBotManager(botManager);
            history1.setCurrentBotManagerUUID(botManager.getUniqueID());
            history1.addTurnHistory(botManager, new TurnHistory(historyItem, historyItem));
            history1.addTurnHistory(botManager, new TurnHistory(historyItem, latestHistoryItem));

            BotManager botManager2 = mock(BotManager.class);
            when(botManager2.getUniqueID()).thenReturn(botManagerUUID);
            History history2 = new History();
            history2.addBotManager(botManager2);
            history2.setCurrentBotManagerUUID(botManager2.getUniqueID());
            history2.addTurnHistory(botManager2, new TurnHistory(historyItem, historyItem));
            history2.addTurnHistory(botManager2, new TurnHistory(historyItem, latestHistoryItem));

            assertThat(history1).isEqualTo(history2);
        }

        @Test
        @DisplayName("Should return false when comparing two histories with different data")
        void shouldReturnFalseWhenComparingTwoHistoriesWithDifferentData() {
            BotManager botManager = mock(BotManager.class);
            when(botManager.getUniqueID()).thenReturn(UUID.randomUUID());
            History history1 = new History();
            HistoryItem latestHistoryItem = mock(HistoryItem.class);
            history1.addBotManager(botManager);
            history1.setCurrentBotManagerUUID(botManager.getUniqueID());
            history1.addTurnHistory(
                    botManager, new TurnHistory(mock(HistoryItem.class), mock(HistoryItem.class)));
            history1.addTurnHistory(
                    botManager, new TurnHistory(mock(HistoryItem.class), latestHistoryItem));

            BotManager botManager2 = mock(BotManager.class);
            when(botManager2.getUniqueID()).thenReturn(UUID.randomUUID());
            HistoryItem latestHistoryItem2 = mock(HistoryItem.class);
            History history2 = new History();
            history2.addBotManager(botManager2);
            history2.setCurrentBotManagerUUID(botManager2.getUniqueID());
            history2.addTurnHistory(
                    botManager2, new TurnHistory(mock(HistoryItem.class), mock(HistoryItem.class)));
            history2.addTurnHistory(
                    botManager2, new TurnHistory(mock(HistoryItem.class), latestHistoryItem2));

            assertThat(history1).isNotEqualTo(history2);
        }

        @Test
        @DisplayName("Should return false when comparing to null")
        void shouldReturnFalseWhenComparingToNull() {
            History history = new History();
            assertThat(history).isNotNull();
        }

        @Test
        @DisplayName("Should return false when comparing to an object of a different class")
        void shouldReturnFalseWhenComparingToDifferentClass() {
            History history = new History();
            assertThat(history).isNotEqualTo(new Object());
        }
    }

    @Nested
    @DisplayName("method hashCode")
    class HashCode {
        @Test
        @DisplayName("Should return the same hash code when comparing the same object")
        void hashcode_shouldReturnSameHashCodeWhenComparingSameObject() {
            History history = new History();
            assertThat(history).hasSameHashCodeAs(history);
        }

        @Test
        @DisplayName("Should return the same hash code when comparing two empty histories")
        void hashcode_shouldReturnSameHashCodeWhenComparingTwoEmptyHistories() {
            History history1 = new History();
            History history2 = new History();
            assertThat(history1).hasSameHashCodeAs(history2);
        }

        @Test
        @DisplayName(
                "Should return the same hash code when comparing two histories with the same data")
        void shouldReturnSameHashCodeWhenComparingTwoHistoriesWithSameData() {
            UUID botManagerUUID = UUID.randomUUID();
            HistoryItem historyItem = mock(HistoryItem.class);

            BotManager botManager = mock(BotManager.class);
            when(botManager.getUniqueID()).thenReturn(botManagerUUID);
            History history1 = new History();
            HistoryItem latestHistoryItem = mock(HistoryItem.class);
            history1.addBotManager(botManager);
            history1.setCurrentBotManagerUUID(botManager.getUniqueID());
            history1.addTurnHistory(botManager, new TurnHistory(historyItem, historyItem));
            history1.addTurnHistory(botManager, new TurnHistory(historyItem, latestHistoryItem));

            BotManager botManager2 = mock(BotManager.class);
            when(botManager2.getUniqueID()).thenReturn(botManagerUUID);
            History history2 = new History();
            history2.addBotManager(botManager2);
            history2.setCurrentBotManagerUUID(botManager2.getUniqueID());
            history2.addTurnHistory(botManager2, new TurnHistory(historyItem, historyItem));
            history2.addTurnHistory(botManager2, new TurnHistory(historyItem, latestHistoryItem));

            assertThat(history1).hasSameHashCodeAs(history2);
        }

        @Test
        @DisplayName(
                "Should return a different hash code when comparing two histories with different"
                        + " data")
        void shouldReturnDifferentHashCodeWhenComparingTwoHistoriesWithDifferentData() {
            BotManager botManager = mock(BotManager.class);
            when(botManager.getUniqueID()).thenReturn(UUID.randomUUID());
            History history1 = new History();
            HistoryItem latestHistoryItem = mock(HistoryItem.class);
            history1.addBotManager(botManager);
            history1.setCurrentBotManagerUUID(botManager.getUniqueID());
            history1.addTurnHistory(
                    botManager, new TurnHistory(mock(HistoryItem.class), mock(HistoryItem.class)));
            history1.addTurnHistory(
                    botManager, new TurnHistory(mock(HistoryItem.class), latestHistoryItem));

            BotManager botManager2 = mock(BotManager.class);
            when(botManager2.getUniqueID()).thenReturn(UUID.randomUUID());

            HistoryItem latestHistoryItem2 = mock(HistoryItem.class);
            History history2 = new History();
            history2.addBotManager(botManager2);
            history2.setCurrentBotManagerUUID(botManager2.getUniqueID());
            history2.addTurnHistory(
                    botManager2, new TurnHistory(mock(HistoryItem.class), mock(HistoryItem.class)));

            history2.addTurnHistory(
                    botManager2, new TurnHistory(mock(HistoryItem.class), latestHistoryItem2));
            assertThat(history1).doesNotHaveSameHashCodeAs(history2);
        }
    }

    @Nested
    @DisplayName("method copy")
    class Copy {
        @Test
        @DisplayName("Should return a copy of the history")
        void shouldReturnACopyOfTheHistory() {
            History history = new History();
            history.setCurrentBotManagerUUID(UUID.randomUUID());
            History copy = history.copy();
            assertThat(copy).isEqualTo(history).isNotSameAs(history);
        }
    }
}
