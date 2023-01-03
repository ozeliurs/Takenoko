package com.takenoko.layers.tile;

import static org.mockito.Mockito.*;

import com.takenoko.bot.Bot;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PlaceTileActionTest {
    private PlaceTileAction placeTileAction;
    private BotManager botManager;
    private Board board;

    @BeforeEach
    void setUp() {
        placeTileAction = new PlaceTileAction(new Tile(), new PositionVector(-1, 0, 1));
        botManager = new BotManager(mock(Bot.class));
        board = mock(Board.class);
        when(board.placeTile(any(), any())).thenReturn(new LayerBambooStack(1));
    }

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should place the tile on the board and grow bamboo")
        void shouldPlaceTileAndGrowBamboo() {
            placeTileAction.execute(board, botManager);
            verify(board).placeTile(new Tile(), new PositionVector(-1, 0, 1));
        }
    }
}
