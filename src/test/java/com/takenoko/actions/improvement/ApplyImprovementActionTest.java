package com.takenoko.actions.improvement;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
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
        @DisplayName("should call board.applyImprovement()")
        void shouldCallBoardApplyImprovement() {
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            ApplyImprovementAction applyImprovementAction =
                    new ApplyImprovementAction(
                            mock(ImprovementType.class), mock(PositionVector.class));
            assertThat(applyImprovementAction.execute(board, botManager)).isNotNull();
            verify(board).applyImprovement(any(ImprovementType.class), any(PositionVector.class));
        }
    }
}
