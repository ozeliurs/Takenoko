package com.takenoko.actions.tile;

import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.inventory.Inventory;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.stats.SingleBotStatistics;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PlaceTileWithImprovementActionTest {
    private BotManager botManager;
    private Board board;
    private ImprovementType improvementType;
    private Tile tile;
    private PositionVector positionVector;

    @BeforeEach
    void setUp() {
        tile = spy(new Tile(TileColor.GREEN));
        positionVector = mock(PositionVector.class);
        improvementType = mock(ImprovementType.class);
        botManager = spy(BotManager.class);
        board = mock(Board.class);
        when(board.placeTile(any(), any())).thenReturn(new LayerBambooStack(1));
    }

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("Should add improvement to tile")
        void shouldAddImprovementToTile() {
            when(botManager.getInventory()).thenReturn(mock(Inventory.class));
            when(botManager.getSingleBotStatistics()).thenReturn(mock(SingleBotStatistics.class));
            PlaceTileWithImprovementAction placeTileWithImprovementAction =
                    new PlaceTileWithImprovementAction(tile, positionVector, improvementType);
            when(board.getTileAt(any())).thenReturn(mock(Tile.class));
            placeTileWithImprovementAction.execute(board, botManager);
            verify(board, times(1)).placeTile(any(), any());
            verify(tile, times(1)).setImprovement(improvementType);
            verify(botManager.getInventory(), times(1)).useImprovement(improvementType);
        }
    }
}
