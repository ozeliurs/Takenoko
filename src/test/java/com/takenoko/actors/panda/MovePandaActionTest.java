package com.takenoko.actors.panda;

import static org.mockito.Mockito.*;

import com.takenoko.bot.Bot;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MovePandaActionTest {

    private MovePandaAction movePandaAction;
    private BotManager botManager;
    private Board board;

    @BeforeEach
    void setUp() {
        movePandaAction = new MovePandaAction(new PositionVector(-1, 0, 1));
        botManager = new BotManager(mock(Bot.class));
        board = mock(Board.class);
    }

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should move the panda")
        void shouldMoveThePanda() {
            when(board.getPandaPosition()).thenReturn(new PositionVector(0, 0, 0));
            movePandaAction.execute(board, botManager);
            verify(board).movePanda(new PositionVector(-1, 0, 1));
        }
    }
}
