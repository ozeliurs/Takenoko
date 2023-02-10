package com.takenoko.stats;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.irrigation.PlaceIrrigationAction;
import com.takenoko.actions.irrigation.StoreIrrigationInInventoryAction;
import com.takenoko.actions.objective.DrawObjectiveAction;
import com.takenoko.actions.tile.PlaceTileAction;
import com.takenoko.bot.utils.GameProgress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GameProgressStatisticsTest {
    private GameProgressStatistics gameProgressStatistics;
    private GameProgress gameProgress;

    @BeforeEach
    void setUp() {
        gameProgress = mock(GameProgress.class);
        gameProgressStatistics = new GameProgressStatistics(gameProgress);
    }

    @Test
    @DisplayName("Constructor test")
    void whenCreated_shouldHaveSpecificActionMetrics() {
        assertThat(gameProgressStatistics.getActions())
                .containsOnlyKeys(
                        MovePandaAction.class.getSimpleName(),
                        MoveGardenerAction.class.getSimpleName(),
                        PlaceIrrigationAction.class.getSimpleName(),
                        PlaceTileAction.class.getSimpleName(),
                        StoreIrrigationInInventoryAction.class.getSimpleName(),
                        DrawObjectiveAction.class.getSimpleName());
        assertThat(gameProgressStatistics.getActions())
                .containsEntry(MovePandaAction.class.getSimpleName(), 0F);
        assertThat(gameProgressStatistics.getActions())
                .containsEntry(MoveGardenerAction.class.getSimpleName(), 0F);
        assertThat(gameProgressStatistics.getActions())
                .containsEntry(PlaceIrrigationAction.class.getSimpleName(), 0F);
        assertThat(gameProgressStatistics.getActions())
                .containsEntry(PlaceTileAction.class.getSimpleName(), 0F);
        assertThat(gameProgressStatistics.getActions())
                .containsEntry(StoreIrrigationInInventoryAction.class.getSimpleName(), 0F);
        assertThat(gameProgressStatistics.getActions())
                .containsEntry(DrawObjectiveAction.class.getSimpleName(), 0F);
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {
        @Test
        @DisplayName("When called on itself should return true")
        @SuppressWarnings("EqualsWithItself")
        void EqualsWithItselfIsTrue() {
            assertThat(gameProgressStatistics.equals(gameProgressStatistics)).isTrue();
        }

        @Test
        @DisplayName("When gameProgressStatistics are equal, returns true")
        void equals_WhenGameProgressStatisticsAreEqual_ThenReturnsTrue() {
            GameProgressStatistics gameProgressStatistics1 =
                    new GameProgressStatistics(gameProgress);
            assertThat(gameProgressStatistics).isEqualTo(gameProgressStatistics1);
        }

        @Test
        @DisplayName("When gameProgressStatistics are not equal, returns false")
        void equals_WhenBoardsAreNotEqual_ThenReturnsFalse() {
            GameProgressStatistics gameProgressStatistics1 =
                    new GameProgressStatistics(GameProgress.EARLY_GAME);
            assertThat(gameProgressStatistics).isNotEqualTo(gameProgressStatistics1);
        }

        @Test
        @DisplayName("When boards is null, returns false")
        void equals_WhenBoardIsNull_ThenReturnsFalse() {
            assertThat(gameProgressStatistics).isNotEqualTo(null);
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("When boards are equal, returns same hash code")
        void hashCode_WhenBoardsAreEqual_ThenReturnsSameHashCode() {
            GameProgressStatistics gameProgressStatistics1 =
                    new GameProgressStatistics(gameProgress);
            assertThat(gameProgressStatistics).hasSameHashCodeAs(gameProgressStatistics1);
        }

        @Test
        @DisplayName("When boards are not equal, returns different hash code")
        void hashCode_WhenBoardsAreNotEqual_ThenReturnsDifferentHashCode() {
            GameProgressStatistics gameProgressStatistics1 =
                    new GameProgressStatistics(GameProgress.EARLY_GAME);
            assertThat(gameProgressStatistics).doesNotHaveSameHashCodeAs(gameProgressStatistics1);
        }
    }
}
