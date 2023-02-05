package com.takenoko.actions.objective;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.engine.BotState;
import com.takenoko.objective.Objective;
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

            RedeemObjectiveAction redeemObjectiveAction = new RedeemObjectiveAction(objective);
            redeemObjectiveAction.execute(board, botManager);

            verify(botManager, times(1)).redeemObjective(any());
        }

        @Test
        @DisplayName("should add RedeemObjectiveAction if it can redeem an objective")
        void update_should_addRedeemObjectiveActionIfCanRedeemObjective() {
            BotState botState = spy(BotState.class);
            when(botState.getAchievedObjectives(mock(Board.class), mock(BotState.class)))
                    .thenReturn(List.of(mock(Objective.class)));

            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);

            botState.addAvailableAction(DrawObjectiveAction.class);

            assertThat(RedeemObjectiveAction.canBePlayed(botState, board)).isTrue();

            botState.update(board);
            assertThat(botState.getAvailableActions()).contains(RedeemObjectiveAction.class);
        }
    }
}
