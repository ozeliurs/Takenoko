package com.takenoko.stats;

import static org.assertj.core.api.Assertions.assertThat;

import com.takenoko.bot.utils.GameProgress;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HistoryStatisticsItemTest {
    private HistoryStatisticsItem historyStatisticsItem;

    @BeforeEach
    void setUp() {
        historyStatisticsItem = new HistoryStatisticsItem();
    }

    @AfterEach
    void tearDown() {
        historyStatisticsItem = null;
    }

    @Test
    @DisplayName("Constructor test")
    void shouldHaveSpecificEntriesWhenCreated() {
        assertThat(historyStatisticsItem)
                .containsOnlyKeys(
                        GameProgress.EARLY_GAME, GameProgress.MID_GAME, GameProgress.LATE_GAME);
        assertThat(historyStatisticsItem)
                .containsEntry(
                        GameProgress.EARLY_GAME,
                        new GameProgressStatistics(GameProgress.EARLY_GAME));
        assertThat(historyStatisticsItem)
                .containsEntry(
                        GameProgress.MID_GAME, new GameProgressStatistics(GameProgress.MID_GAME));
        assertThat(historyStatisticsItem)
                .containsEntry(
                        GameProgress.LATE_GAME, new GameProgressStatistics(GameProgress.LATE_GAME));
    }
}
