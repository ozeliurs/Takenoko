package com.takenoko.layers.bamboo;

import static org.mockito.Mockito.*;

import com.takenoko.bot.Bot;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GrowBambooActionTest {
    private GrowBambooAction growBambooAction;
    private BotManager botManager;
    private Board board;

    @BeforeEach
    void setUp() {
        growBambooAction = new GrowBambooAction(new PositionVector(-1, 0, 1));
        botManager = new BotManager(mock(Bot.class));
        board = mock(Board.class);
    }

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should place the tile on the board")
        void shouldMoveThePanda() {
            growBambooAction.execute(board, botManager);
            verify(board).growBamboo(new PositionVector(-1, 0, 1));
        }
    }
}
