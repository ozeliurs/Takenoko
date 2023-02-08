package com.takenoko.bot.utils.pathfinding.gardener;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.objective.Objective;
import com.takenoko.vector.PositionVector;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GardenerPathfindingTest {
    @Nested
    @DisplayName("method getPosition")
    class GetPosition {
        @Test
        @DisplayName("should return null")
        void shouldReturnNull() {
            Board board = mock(Board.class);
            BotState botState = mock(BotState.class);
            History history = mock(History.class);
            Map<Objective, List<PositionVector>> goodMoves =
                    GardenerPathfinding.getGardenerMoves(board, botState);
            // TODO assertNull(position);

        }
    }
}
