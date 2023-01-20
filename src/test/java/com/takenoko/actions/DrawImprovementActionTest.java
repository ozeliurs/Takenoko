package com.takenoko.actions;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.ImprovementType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DrawImprovementActionTest {
    @Nested
    @DisplayName("Method apply()")
    class TestApply {
        @Test
        @DisplayName("should return an ActionResult")
        void shouldReturnAnActionResult() {
            ImprovementType improvementType = ImprovementType.FERTILIZER;
            DrawImprovementAction drawImprovementAction =
                    new DrawImprovementAction(improvementType);
            Board board = mock(Board.class);
            assertNotNull(drawImprovementAction.execute(board, mock(BotManager.class)));
        }

        @Test
        @DisplayName(
                "should return an ActionResult containing ApplyImprovementAction and"
                        + " StoreImprovementAction if an improvement is drawn")
        void
                shouldReturnAnActionResultContainingApplyImprovementActionAndStoreImprovementActionIfAnImprovementIsDrawn() {
            ImprovementType improvementType = ImprovementType.FERTILIZER;
            DrawImprovementAction drawImprovementAction =
                    new DrawImprovementAction(improvementType);
            Board board = mock(Board.class);
            assertNotNull(drawImprovementAction.execute(board, mock(BotManager.class)));
        }
    }
}
