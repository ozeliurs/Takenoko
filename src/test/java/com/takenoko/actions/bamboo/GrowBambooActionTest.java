package com.takenoko.actions.bamboo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GrowBambooActionTest {

    @Nested
    @DisplayName("execute")
    class Execute {
        @Test
        @DisplayName("should throw IllegalStateException when bamboo is not growable")
        void shouldThrowIllegalStateExceptionWhenBambooIsNotGrowable() {
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            when(board.isBambooGrowableAt(any())).thenReturn(false);
            GrowBambooAction growBambooAction = new GrowBambooAction(mock(PositionVector.class));

            assertThatThrownBy(() -> growBambooAction.execute(board, botManager))
                    .isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("should grow bamboo when bamboo is growable")
        void shouldGrowBambooWhenBambooIsGrowable() {
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            PositionVector position = mock(PositionVector.class);
            when(board.isBambooGrowableAt(any())).thenReturn(true);
            GrowBambooAction growBambooAction = new GrowBambooAction(position);

            growBambooAction.execute(board, botManager);

            verify(board, times(1)).growBamboo(position);
        }
    }
}
