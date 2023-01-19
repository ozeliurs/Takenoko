package com.takenoko.actions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.ImprovementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GetAndStoreImprovementActionTest {

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
        @DisplayName("When getting an improvement, the number of actions consumed is 1")
        void whenGettingAnImprovementTheNumberOfActionsConsumedIs1() {
            GetAndStoreImprovementAction action =
                    new GetAndStoreImprovementAction(ImprovementType.FERTILIZER);
            ActionResult result = action.execute(mock(Board.class), mock(BotManager.class));
            assertEquals(1, result.cost());
        }
    }
}
