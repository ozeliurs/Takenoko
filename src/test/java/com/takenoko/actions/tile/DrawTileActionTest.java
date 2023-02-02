package com.takenoko.actions.tile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.inventory.Inventory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DrawTileActionTest {
    @Nested
    class TestExecute {

        @Test
        @DisplayName(
                "if the tile deck is not empty, and the inventory has an improvement, return a"
                        + " PlaceTileAction and a PlaceTileWithImprovementAction")
        void testExecuteTileDeckNotEmptyAndInventoryHasImprovement() {
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            DrawTileAction drawTileAction = new DrawTileAction();
            when(botManager.getInventory()).thenReturn(mock(Inventory.class));
            when(botManager.getInventory().hasImprovement()).thenReturn(true);

            assertThat(drawTileAction.execute(board, botManager).availableActions())
                    .containsExactly(PlaceTileAction.class, PlaceTileWithImprovementAction.class);

            verify(board).drawTiles();
            verify(botManager).displayMessage(any());
            verify(botManager.getInventory()).hasImprovement();
        }

        @Test
        @DisplayName(
                "if the tile deck is not empty, and the inventory does not have an improvement,"
                        + " return a PlaceTileAction")
        void testExecuteTileDeckNotEmptyAndInventoryDoesNotHaveImprovement() {
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            DrawTileAction drawTileAction = new DrawTileAction();
            when(botManager.getInventory()).thenReturn(mock(Inventory.class));
            when(botManager.getInventory().hasImprovement()).thenReturn(false);

            assertThat(drawTileAction.execute(board, botManager).availableActions())
                    .containsExactly(PlaceTileAction.class);

            verify(board).drawTiles();
            verify(botManager).displayMessage(any());
            verify(botManager.getInventory()).hasImprovement();
        }
    }
}
