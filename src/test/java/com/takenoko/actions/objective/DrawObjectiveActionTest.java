package com.takenoko.actions.objective;

import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
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

            DrawObjectiveAction drawObjectiveAction = new DrawObjectiveAction();

            drawObjectiveAction.execute(board, botManager);

            verify(board, times(1)).drawObjective();
            verify(botManager, times(1)).addObjective(any());
        }
    }
}
