package com.takenoko.layers.tile;

import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.LayerManager;
import com.takenoko.player.Playable;
import com.takenoko.tile.Tile;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PlaceTileActionTest {
    private PlaceTileAction placeTileAction;
    private BotManager botManager;
    private Board board;
    private TileLayer tileLayer;

    @BeforeEach
    void setUp() {
        placeTileAction = new PlaceTileAction(new Tile(), new PositionVector(-1, 0, 1));
        botManager = new BotManager(mock(Playable.class));
        LayerManager layerManager = mock(LayerManager.class);
        tileLayer = mock(TileLayer.class);
        when(layerManager.getTileLayer()).thenReturn(tileLayer);
        board = mock(Board.class);
        when(board.getLayerManager()).thenReturn(layerManager);
    }

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should place the tile on the board")
        void shouldMoveThePanda() {
            placeTileAction.execute(board, botManager);
            verify(tileLayer).placeTile(new Tile(), new PositionVector(-1, 0, 1));
        }
    }
}
