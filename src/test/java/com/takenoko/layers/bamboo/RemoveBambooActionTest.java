package com.takenoko.layers.bamboo;

import static org.mockito.Mockito.*;

import com.takenoko.bot.Bot;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.inventory.Inventory;
import com.takenoko.inventory.InventoryBambooStack;
import com.takenoko.layers.LayerManager;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RemoveBambooActionTest {

    private RemoveBambooAction removeBambooAction;
    private BotManager botManager;
    private Board board;

    @BeforeEach
    void setUp() {
        removeBambooAction = new RemoveBambooAction(new PositionVector(-1, 0, 1));
        botManager = spy(new BotManager(mock(Bot.class)));
        board = mock(Board.class);
        when(board.getLayerManager()).thenReturn(mock(LayerManager.class));
        when(board.getLayerManager().getBambooLayer()).thenReturn(mock(BambooLayer.class));
    }

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should remove bamboo")
        void shouldMoveThePanda() {
            removeBambooAction.execute(board, botManager);
            verify(board.getLayerManager().getBambooLayer())
                    .removeBamboo(new PositionVector(-1, 0, 1));
        }

        @Test
        @DisplayName("should collect bamboo")
        void shouldCollectBamboo() {
            when(botManager.getInventory()).thenReturn(mock(Inventory.class));
            when(botManager.getInventory().getBambooStack())
                    .thenReturn(mock(InventoryBambooStack.class));
            removeBambooAction.execute(board, botManager);
            verify(botManager.getInventory()).getBambooStack();
        }
    }
}
