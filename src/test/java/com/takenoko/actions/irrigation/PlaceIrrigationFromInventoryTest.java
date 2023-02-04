package com.takenoko.actions.irrigation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.inventory.Inventory;
import com.takenoko.layers.irrigation.EdgePosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PlaceIrrigationFromInventoryTest {
    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should return an ActionResult")
        void shouldReturnAnActionResult() {
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);

            EdgePosition positionVector = mock(EdgePosition.class);

            when(botManager.getInventory()).thenReturn(mock(Inventory.class));

            PlaceIrrigationFromInventory applyIrrigationFromInventory =
                    new PlaceIrrigationFromInventory(positionVector);

            assertNotNull(applyIrrigationFromInventory.execute(board, botManager));

            verify(board).placeIrrigation(positionVector);
            verify(botManager.getInventory()).useIrrigationChannel();
        }
    }
}
