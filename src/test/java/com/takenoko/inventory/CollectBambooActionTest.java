package com.takenoko.inventory;

import static org.mockito.Mockito.*;

import com.takenoko.engine.BotManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CollectBambooActionTest {

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should add bamboo to the inventory")
        void shouldAddBambooToTheInventory() {
            BotManager botManager = mock(BotManager.class);
            when(botManager.getInventory()).thenReturn(mock(Inventory.class));
            when(botManager.getInventory().getBambooStack())
                    .thenReturn(mock(InventoryBambooStack.class));
            (new CollectBambooAction()).execute(null, botManager);
            verify(botManager.getInventory().getBambooStack()).growBamboo();
        }
    }
}
