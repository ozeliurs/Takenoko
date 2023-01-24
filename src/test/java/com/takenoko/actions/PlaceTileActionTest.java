package com.takenoko.actions;

import static org.mockito.Mockito.*;

import com.takenoko.actions.tile.PlaceTileAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.layers.tile.Tile;
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
        botManager = spy(BotManager.class);
        board = mock(Board.class);
        when(board.placeTile(any(), any())).thenReturn(new LayerBambooStack(1));
    }

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {

        @Test
        @DisplayName("Should call board.placeTile()")
        void shouldCallBoardPlaceTile() {
            placeTileAction.execute(board, botManager);
            verify(board, times(1)).placeTile(any(), any());
        }

        @Test
        @DisplayName("Should display messages")
        void shouldDisplayMessage() {
            placeTileAction.execute(board, botManager);
            verify(botManager, times(1))
                    .displayMessage("Bamboo grew at Vector[q=-1.0, r=0.0, s=1.0]");
            verify(botManager, times(1))
                    .displayMessage("Joe placed a tile at Vector[q=-1.0, r=0.0, s=1.0]");
        }
    }
}
