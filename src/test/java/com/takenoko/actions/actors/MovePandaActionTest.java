package com.takenoko.actions.actors;

import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.engine.SingleBotStatistics;
import com.takenoko.inventory.Inventory;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;
import java.util.Map;
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
        when(board.movePanda(any()))
                .thenReturn(Map.of(new PositionVector(-1, 0, 1), new LayerBambooStack(1)));
    }

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should move the panda collect bamboo and display messages")
        void shouldMoveThePandaCollectBambooAndDisplayMessages() {
            SingleBotStatistics singleBotStatistics = mock(SingleBotStatistics.class);
            when(botManager.getSingleBotStatistics()).thenReturn(singleBotStatistics);
            Inventory inventory = mock(Inventory.class);
            when(board.getPandaPosition()).thenReturn(new PositionVector(0, 0, 0));
            when(botManager.getInventory()).thenReturn(inventory);
            when(botManager.getName()).thenReturn("Joe");
            when(board.getTileAt(any())).thenReturn(new Tile());
            movePandaAction.execute(board, botManager);
            verify(board).movePanda(new PositionVector(-1, 0, 1));
            verify(inventory, times(1)).collectBamboo(any());
        }

        @Test
        @DisplayName("should update eaten bamboo counter and actions in singleBotStatistics")
        void shouldUpdateEatenBambooCounterAndActionsInSingleBotStatistics() {
            SingleBotStatistics singleBotStatistics = mock(SingleBotStatistics.class);
            when(botManager.getSingleBotStatistics()).thenReturn(singleBotStatistics);
            Inventory inventory = mock(Inventory.class);
            when(board.getPandaPosition()).thenReturn(new PositionVector(0, 0, 0));
            when(botManager.getInventory()).thenReturn(inventory);
            when(botManager.getName()).thenReturn("Joe");
            when(board.getTileAt(any())).thenReturn(new Tile());
            movePandaAction.execute(board, botManager);
            verify(singleBotStatistics).updateEatenBambooCounter(any());
            verify(singleBotStatistics).updateActions(movePandaAction.getClass().getSimpleName());
        }
    }
}
