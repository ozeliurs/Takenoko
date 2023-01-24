package com.takenoko.actions;

import static org.mockito.Mockito.*;

import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class MoveGardenerActionTest {

    private MoveGardenerAction moveGardenerAction;
    private BotManager botManager;
    private Board board;

    @BeforeEach
    void setUp() {
        moveGardenerAction = new MoveGardenerAction(new PositionVector(-1, 0, 1));
        botManager = mock(BotManager.class);
        board = mock(Board.class);
        when(board.moveGardener(any())).thenReturn(new LayerBambooStack(1));
    }

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should move the gardener plant a bamboo and display messages")
        void shouldMoveTheGardenerPlantBambooAndDisplayMessages() {
            when(board.getGardenerPosition()).thenReturn(new PositionVector(0, 0, 0));
            when(botManager.getName()).thenReturn("Joe");
            moveGardenerAction.execute(board, botManager);
            verify(board).moveGardener(new PositionVector(-1, 0, 1));
            verify(botManager, times(1))
                    .displayMessage(
                            "Joe moved the gardener with Vector[q=-1.0, r=0.0, s=1.0] to position"
                                    + " Vector[q=0.0, r=0.0, s=0.0]");
            verify(botManager, times(1)).displayMessage("Joe planted one bamboo");
        }
    }
}
