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

    @Nested
    @DisplayName("Method getGameProgress()")
    class GetGameProgress {

        private PandaObjective pandaObjective;
        private HistoryItem historyItem1;
        private HistoryItem historyItem2;

        @BeforeEach
        void setUp() {
            history = new History();
            botManager = new BotManager();
            historyItem1 = mock(HistoryItem.class);
            pandaObjective = mock(PandaObjective.class);
            when(pandaObjective.getPoints()).thenReturn(2);

            history.addBotManager(botManager);
            history.setCurrentBotManagerUUID(botManager.getUniqueID());
            history.addHistoryItem(botManager, historyItem1);

            botManager2 = new BotManager();
            historyItem2 = mock(HistoryItem.class);
            history.addBotManager(botManager2);
            history.setCurrentBotManagerUUID(botManager2.getUniqueID());
            history.addHistoryItem(botManager2, historyItem2);
        }

        @Test
        @DisplayName("Should return Early Game when it is early game")
        void shouldReturnEarlyGame() {
            when(historyItem1.redeemedObjectives()).thenReturn(List.of(pandaObjective));
            when(historyItem2.redeemedObjectives()).thenReturn(List.of(pandaObjective));
            assertThat(HistoryAnalysis.getGameProgress(history)).isEqualTo(GameProgress.EARLY_GAME);
        }

        @Test
        @DisplayName("Should return Mid Game when it is mid game")
        void shouldReturnMidGame() {
            when(historyItem1.redeemedObjectives())
                    .thenReturn(
                            List.of(
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective));
            when(historyItem2.redeemedObjectives())
                    .thenReturn(
                            List.of(
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective));
            assertThat(HistoryAnalysis.getGameProgress(history)).isEqualTo(GameProgress.MID_GAME);
        }

        @Test
        @DisplayName("Should return Late Game when it is late game")
        void shouldReturnLateGame() {
            when(historyItem1.redeemedObjectives())
                    .thenReturn(
                            List.of(
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective));
            when(historyItem2.redeemedObjectives())
                    .thenReturn(
                            List.of(
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective,
                                    pandaObjective));
            assertThat(HistoryAnalysis.getGameProgress(history)).isEqualTo(GameProgress.LATE_GAME);
        }
    }
}
