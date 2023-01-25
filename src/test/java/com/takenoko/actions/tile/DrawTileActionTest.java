package com.takenoko.actions.tile;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DrawTileActionTest {
    @Nested
    class TestExecute {
        @Test
        void shouldCallDrawTile() {
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            DrawTileAction drawTileAction = new DrawTileAction();
            drawTileAction.execute(board, botManager);
            verify(board).drawTiles();
        }
    }
}
