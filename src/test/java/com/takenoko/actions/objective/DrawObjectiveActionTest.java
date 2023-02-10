package com.takenoko.actions.objective;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.engine.BotState;
import com.takenoko.objective.Objective;
import com.takenoko.objective.ObjectiveType;
import com.takenoko.stats.SingleBotStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DrawObjectiveActionTest {

    @Nested
    @DisplayName("Method execute")
    class TestExecute {
        @Test
        @DisplayName("should return a ActionResult with a DrawObjectiveAction")
        void execute_shouldReturnActionResultWithDrawObjectiveAction() {
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            SingleBotStatistics singleBotStatistics = mock(SingleBotStatistics.class);
            when(botManager.getSingleBotStatistics()).thenReturn(singleBotStatistics);
            DrawObjectiveAction drawObjectiveAction = new DrawObjectiveAction(ObjectiveType.PANDA);

            drawObjectiveAction.execute(board, botManager);

            verify(board, times(1)).drawObjective(ObjectiveType.PANDA);
            verify(botManager, times(1)).addObjective(any());
        }

        @Test
        @DisplayName("should update actions in singlebotStatistics")
        void shouldUpdateActionsInSingleBotStatistics() {
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            SingleBotStatistics singleBotStatistics = mock(SingleBotStatistics.class);
            when(botManager.getSingleBotStatistics()).thenReturn(singleBotStatistics);
            DrawObjectiveAction drawObjectiveAction = new DrawObjectiveAction(ObjectiveType.PANDA);
            drawObjectiveAction.execute(board, botManager);
            verify(singleBotStatistics, times(1))
                    .updateActions(drawObjectiveAction.getClass().getSimpleName());
        }
    }

    @Nested
    @DisplayName("Method canBePlayed()")
    class TestCanBePlayed {

        BotState botState;
        Board board;

        @BeforeEach
        void setUp() {
            board = mock(Board.class);
            botState = mock(BotState.class);
        }

        @Test
        @DisplayName("should return true if the player can draw an objective")
        void canDrawObjective_shouldReturnTrueIfCanDrawObjective() {
            when(board.isObjectiveDeckEmpty()).thenReturn(false);
            assertThat(DrawObjectiveAction.canBePlayed(board, botState)).isTrue();
        }

        @Test
        @DisplayName("should return false if the player can't draw an objective")
        void canDrawObjective_shouldReturnFalseIfCantDrawObjective() {
            when(board.isObjectiveDeckEmpty()).thenReturn(false);
            botState = new BotState();
            for (int i = 0; i < BotState.MAX_OBJECTIVES; i++) {
                botState.addObjective(mock(Objective.class));
            }
            assertThat(DrawObjectiveAction.canBePlayed(board, botState)).isFalse();
        }

        @Test
        @DisplayName("should return false if the objective deck is empty")
        void canDrawObjective_shouldReturnFalseIfObjectiveDeckIsEmpty() {
            when(board.isObjectiveDeckEmpty()).thenReturn(true);
            assertThat(DrawObjectiveAction.canBePlayed(board, botState)).isFalse();
        }
    }
}
