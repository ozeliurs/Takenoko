package com.takenoko.objective;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.inventory.Inventory;
import com.takenoko.layers.tile.TileColor;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PandaObjectiveTest {

    Board board;
    PandaObjective pandaObjective;
    BotManager botManager;

    @BeforeEach
    void setUp() {
        board = mock(Board.class);
        botManager = mock(BotManager.class);
        when(botManager.getInventory()).thenReturn(mock(Inventory.class));
        pandaObjective =
                new PandaObjective(Map.entry(TileColor.GREEN, 1), Map.entry(TileColor.PINK, 1));
    }

    @Nested
    @DisplayName("Method verify")
    class TestVerify {
        @Test
        @DisplayName("When the objective is not achieved, returns NOT_ACHIEVED")
        void verify_WhenObjectiveIsNotAchieved_ThenReturnsNOT_ACHIEVED() {
            when(botManager.getInventory().getBambooCount(any())).thenReturn(0);
            pandaObjective.verify(board, botManager);
            assertThat(pandaObjective.isAchieved()).isFalse();
        }

        @Test
        @DisplayName("When the objective is achieved, returns ACHIEVED")
        void verify_WhenObjectiveIsAchieved_ThenReturnsACHIEVED() {
            when(botManager.getInventory().getBambooCount(TileColor.GREEN)).thenReturn(1);
            when(botManager.getInventory().getBambooCount(TileColor.PINK)).thenReturn(1);
            pandaObjective.verify(board, botManager);
            assertThat(pandaObjective.isAchieved()).isTrue();
        }
    }

    @Nested
    @DisplayName("Method getCompletion")
    class TestGetCompletion {
        @Test
        @DisplayName("When the objective is not achieved, returns 0")
        void getCompletion_WhenObjectiveIsNotAchieved_ThenReturns0() {
            when(botManager.getInventory().getBambooCount(any())).thenReturn(0);
            assertThat(pandaObjective.getCompletion(board, botManager)).isZero();
        }

        @Test
        @DisplayName("When the objective is achieved, returns 1")
        void getCompletion_WhenObjectiveIsAchieved_ThenReturns1() {
            when(botManager.getInventory().getBambooCount(TileColor.GREEN)).thenReturn(1);
            when(botManager.getInventory().getBambooCount(TileColor.PINK)).thenReturn(1);
            assertThat(pandaObjective.getCompletion(board, botManager)).isEqualTo(1);
        }

        @Test
        @DisplayName("When the objective is partially achieved, returns the correct value")
        void getCompletion_WhenObjectiveIsPartiallyAchieved_ThenReturnsCorrectValue() {
            when(botManager.getInventory().getBambooCount(TileColor.GREEN)).thenReturn(1);
            when(botManager.getInventory().getBambooCount(TileColor.PINK)).thenReturn(0);
            assertThat(pandaObjective.getCompletion(board, botManager)).isEqualTo(0.5f);
        }
    }
}
