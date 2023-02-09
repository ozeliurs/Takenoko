package com.takenoko.actions.objective;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.engine.BotState;
import com.takenoko.objective.Objective;
import com.takenoko.stats.SingleBotStatistics;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RedeemObjectiveActionTest {

    @Nested
    @DisplayName("Method execute")
    class TestExecute {
        @Test
        @DisplayName("should return a ActionResult with a RedeemObjectiveAction")
        void execute_shouldReturnActionResultWithRedeemObjectiveAction() {
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            Objective objective = mock(Objective.class);
            SingleBotStatistics singleBotStatistics = mock(SingleBotStatistics.class);
            when(botManager.getSingleBotStatistics()).thenReturn(singleBotStatistics);
            RedeemObjectiveAction redeemObjectiveAction = new RedeemObjectiveAction(objective);
            redeemObjectiveAction.execute(board, botManager);

            verify(botManager, times(1)).redeemObjective(any());
        }

        @Test
        @DisplayName("should add RedeemObjectiveAction if it can redeem an objective")
        void update_should_addRedeemObjectiveActionIfCanRedeemObjective() {
            BotState botState = spy(BotState.class);
            when(botState.getAchievedObjectives()).thenReturn(List.of(mock(Objective.class)));

            Board board = mock(Board.class);

            botState.addAvailableAction(DrawObjectiveAction.class);

            assertThat(RedeemObjectiveAction.canBePlayed(botState)).isTrue();

            botState.update(board);
            assertThat(botState.getAvailableActions()).contains(RedeemObjectiveAction.class);
        }

        @Test
        @DisplayName("should update actions in singlebotStatistics")
        void shouldUpdateActionsInSingleBotStatistics() {
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            Objective objective = mock(Objective.class);
            SingleBotStatistics singleBotStatistics = mock(SingleBotStatistics.class);
            when(botManager.getSingleBotStatistics()).thenReturn(singleBotStatistics);
            RedeemObjectiveAction redeemObjectiveAction = new RedeemObjectiveAction(objective);
            redeemObjectiveAction.execute(board, botManager);
            verify(singleBotStatistics, times(1)).updateObjectivesRedeemed(objective);
        }
    }
}
