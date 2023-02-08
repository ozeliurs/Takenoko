package com.takenoko.bot.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.engine.BotManager;
import com.takenoko.engine.History;
import com.takenoko.engine.HistoryItem;
import com.takenoko.objective.PandaObjective;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HistoryAnalysisTest {

    private History history;
    private BotManager botManager;
    private BotManager botManager2;

    @BeforeEach
    void setUp() {
        history = new History();
        botManager = new BotManager();
        HistoryItem historyItem4Points = mock(HistoryItem.class);
        PandaObjective pandaObjective = mock(PandaObjective.class);
        when(pandaObjective.getPoints()).thenReturn(2);
        when(historyItem4Points.redeemedObjectives())
                .thenReturn(List.of(pandaObjective, pandaObjective));
        history.addBotManager(botManager);
        history.setCurrentBotManagerUUID(botManager.getUniqueID());
        history.addHistoryItem(botManager, historyItem4Points);

        botManager2 = new BotManager();
        HistoryItem historyItem2Points = mock(HistoryItem.class);
        when(historyItem2Points.redeemedObjectives()).thenReturn(List.of(pandaObjective));
        history.addBotManager(botManager2);
        history.setCurrentBotManagerUUID(botManager2.getUniqueID());
        history.addHistoryItem(botManager2, historyItem2Points);
    }

    @Nested
    @DisplayName("Method getCurrentBotScores()")
    class GetCurrentBotScores {
        @Test
        @DisplayName("Should return a map of the current scores of each bot")
        void shouldReturnMapOfCurrentScores() {

            assertThat(HistoryAnalysis.getCurrentBotScores(history))
                    .hasSize(2)
                    .containsEntry(botManager.getUniqueID(), 4)
                    .containsEntry(botManager2.getUniqueID(), 2);
        }
    }

    @Nested
    @DisplayName("Method getMaxCurrentBotScore()")
    class GetMaxCurrentBotScore {
        @Test
        @DisplayName("Should return the maximum score of all the bots")
        void shouldReturnMaxScore() {
            assertThat(HistoryAnalysis.getMaxCurrentBotScore(history)).isEqualTo(4);
        }
    }
}
