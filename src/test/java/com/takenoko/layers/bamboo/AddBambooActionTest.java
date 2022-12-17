package com.takenoko.layers.bamboo;

import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.LayerManager;
import com.takenoko.bot.Bot;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AddBambooActionTest {
    private AddBambooAction addBambooAction;
    private BotManager botManager;
    private Board board;

    @BeforeEach
    void setUp() {
        addBambooAction = new AddBambooAction(new PositionVector(-1, 0, 1));
        botManager = new BotManager(mock(Bot.class));
        board = mock(Board.class);
        when(board.getLayerManager()).thenReturn(mock(LayerManager.class));
        when(board.getLayerManager().getBambooLayer()).thenReturn(mock(BambooLayer.class));
    }

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should place the tile on the board")
        void shouldMoveThePanda() {
            addBambooAction.execute(board, botManager);
            verify(board.getLayerManager().getBambooLayer())
                    .addBamboo(new PositionVector(-1, 0, 1));
        }
    }
}
