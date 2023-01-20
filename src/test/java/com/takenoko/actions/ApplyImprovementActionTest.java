package com.takenoko.actions;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.inventory.Inventory;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ApplyImprovementActionTest {

    @Nested
    @DisplayName("method execute")
    class Execute {
        @Test
        @DisplayName("should throw IllegalStateException when improvement not in inventory")
        void shouldThrowIllegalStateExceptionWhenImprovementNotInInventory() {
            BotManager botManager = mock(BotManager.class);
            Board board = mock(Board.class);
            ApplyImprovementAction applyImprovementAction =
                    new ApplyImprovementAction(
                            mock(ImprovementType.class), mock(PositionVector.class));
            when(botManager.getInventory()).thenReturn(mock(Inventory.class));
            when(botManager.getInventory().hasImprovement(any(ImprovementType.class)))
                    .thenReturn(false);

            assertThatThrownBy(() -> applyImprovementAction.execute(board, botManager))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("Improvement not in deck");
        }
    }
}
