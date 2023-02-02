package com.takenoko.actions.objective;

import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.objective.Objective;
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
    }
}
