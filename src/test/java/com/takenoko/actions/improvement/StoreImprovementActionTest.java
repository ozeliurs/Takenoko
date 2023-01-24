package com.takenoko.actions.improvement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.actions.ActionResult;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.inventory.Inventory;
import com.takenoko.layers.tile.ImprovementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StoreImprovementActionTest {

    Board board;

    @BeforeEach
    void setUp() {
        board = mock(Board.class);
        when(board.hasImprovementInDeck(any(ImprovementType.class))).thenReturn(true);
        when(board.drawImprovement(any(ImprovementType.class)))
                .thenReturn(mock(ImprovementType.class));
    }

    @Nested
    @DisplayName("execute")
    class Execute {
        @Test
        @DisplayName(
                "When getting an improvement and the improvement is available, the number of"
                        + " actions consumed is 1")
        void whenGettingAnImprovementTheNumberOfActionsConsumedIs1() {
            StoreImprovementAction action = new StoreImprovementAction(ImprovementType.FERTILIZER);
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            when(board.hasImprovementInDeck(any(ImprovementType.class))).thenReturn(true);
            when(board.drawImprovement(any(ImprovementType.class)))
                    .thenReturn(mock(ImprovementType.class));
            when(botManager.getInventory()).thenReturn(mock(Inventory.class));
            ActionResult result = action.execute(board, botManager);
            assertEquals(1, result.cost());
        }
    }
}
