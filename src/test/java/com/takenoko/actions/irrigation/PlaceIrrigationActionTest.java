package com.takenoko.actions.irrigation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.actions.ActionResult;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.stats.SingleBotStatistics;
import com.takenoko.layers.irrigation.EdgePosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PlaceIrrigationActionTest {
    PlaceIrrigationAction action;
    Board board;
    BotManager botManager;

    @BeforeEach
    void setUp() {
        action = new PlaceIrrigationAction(mock(EdgePosition.class));
        board = mock(Board.class);
        botManager = mock(BotManager.class);
    }

    @Nested
    @DisplayName("method execute")
    class Execute {

        @Test
        @DisplayName("should call board.placeIrrigation()")
        void shouldCallBoardPlaceIrrigation() {
            when(botManager.getSingleBotStatistics()).thenReturn(mock(SingleBotStatistics.class));
            action.execute(board, botManager);
            verify(board).placeIrrigation(action.edgePosition);
        }

        @Test
        @DisplayName("should return an ActionResult with 1 cost")
        void shouldReturnAnActionResultWith1Cost() {
            when(botManager.getSingleBotStatistics()).thenReturn(mock(SingleBotStatistics.class));
            ActionResult result = action.execute(board, botManager);
            assertThat(result.cost()).isEqualTo(1);
        }
    }
}
