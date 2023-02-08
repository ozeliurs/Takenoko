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
            history.addHistoryItem(botManager, mock(HistoryItem.class));
            history.addHistoryItem(botManager, mock(HistoryItem.class));
            history.addHistoryItem(botManager, latestHistoryItem);

            BotManager botManager2 = mock(BotManager.class);
            when(botManager2.getUniqueID()).thenReturn(UUID.randomUUID());
            HistoryItem latestHistoryItem2 = mock(HistoryItem.class);
            history.addBotManager(botManager2);
            history.setCurrentBotManagerUUID(botManager2.getUniqueID());
            history.addHistoryItem(botManager2, mock(HistoryItem.class));
            history.addHistoryItem(botManager2, mock(HistoryItem.class));
            history.addHistoryItem(botManager2, latestHistoryItem2);
            assertThat(history.getLatestHistoryItems())
                    .hasSize(2)
                    .containsEntry(botManager.getUniqueID(), latestHistoryItem)
                    .containsEntry(botManager2.getUniqueID(), latestHistoryItem2);
        }
    }
}
