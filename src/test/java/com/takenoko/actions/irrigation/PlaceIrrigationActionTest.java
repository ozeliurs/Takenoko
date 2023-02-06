package com.takenoko.actions.irrigation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.takenoko.actions.ActionResult;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.irrigation.EdgePosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PlaceIrrigationActionTest {
    PlaceIrrigationAction action;
    Board board;

    @BeforeEach
    void setUp() {
        action = new PlaceIrrigationAction(mock(EdgePosition.class));
        board = mock(Board.class);
    }

    @Nested
    @DisplayName("method execute")
    class Execute {

        @Test
        @DisplayName("should call board.placeIrrigation()")
        void shouldCallBoardPlaceIrrigation() {
            action.execute(board, mock(BotManager.class));
            verify(board).placeIrrigation(action.edgePosition);
        }

        @Test
        @DisplayName("should return an ActionResult with 1 cost")
        void shouldReturnAnActionResultWith1Cost() {
            ActionResult result = action.execute(board, mock(BotManager.class));
            assertThat(result.cost()).isEqualTo(1);
        }
    }
}
