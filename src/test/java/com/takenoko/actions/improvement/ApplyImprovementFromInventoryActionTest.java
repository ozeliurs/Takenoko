package com.takenoko.actions.improvement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.inventory.Inventory;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ApplyImprovementFromInventoryActionTest {

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should return an ActionResult")
        void shouldReturnAnActionResult() {
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            when(botManager.getInventory()).thenReturn(mock(Inventory.class));
            when(botManager.getInventory().hasImprovement(any(ImprovementType.class)))
                    .thenReturn(true);
            ApplyImprovementFromInventoryAction applyImprovementFromInventoryAction =
                    new ApplyImprovementFromInventoryAction(
                            mock(ImprovementType.class), mock(PositionVector.class));
            assertNotNull(applyImprovementFromInventoryAction.execute(board, botManager));
            // calls board.applyImprovement() and botManager.getInventory().useImprovement()
            verify(board).applyImprovement(any(ImprovementType.class), any(PositionVector.class));
            verify(botManager.getInventory()).useImprovement(any(ImprovementType.class));
        }
    }
}
