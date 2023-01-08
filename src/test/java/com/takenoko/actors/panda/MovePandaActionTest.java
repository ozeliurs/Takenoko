package com.takenoko.actors.panda;

import static org.mockito.Mockito.*;

import com.takenoko.actions.MovePandaAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.inventory.Inventory;
import com.takenoko.inventory.InventoryBambooStack;
import com.takenoko.layers.bamboo.LayerBambooStack;
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
        botManager = mock(BotManager.class);
        board = mock(Board.class);
        when(board.movePanda(any())).thenReturn(new LayerBambooStack(1));
    }

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should move the panda collect bamboo and display messages")
        void shouldMoveThePandaCollectBambooAndDisplayMessages() {
            InventoryBambooStack bambooStack = mock(InventoryBambooStack.class);
            Inventory inventory = mock(Inventory.class);
            when(board.getPandaPosition()).thenReturn(new PositionVector(0, 0, 0));
            when(botManager.getInventory()).thenReturn(inventory);
            when(inventory.getBambooStack()).thenReturn(bambooStack);
            when(botManager.getName()).thenReturn("Joe");
            movePandaAction.execute(board, botManager);
            verify(board).movePanda(new PositionVector(-1, 0, 1));
            verify(botManager, times(1))
                    .displayMessage(
                            "Joe moved the panda with Vector[q=-1.0, r=0.0, s=1.0] to position"
                                    + " Vector[q=0.0, r=0.0, s=0.0]");
            verify(botManager, times(1)).displayMessage("Joe collected one bamboo");
            verify(bambooStack, times(1)).collectBamboo();
        }
    }
}
