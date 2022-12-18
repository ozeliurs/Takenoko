package com.takenoko.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class InventoryBambooStackTest {
    @Nested
    @DisplayName("Method addBamboo()")
    class TestAddBamboo {
        @Test
        @DisplayName("should add a bamboo")
        void shouldAddABamboo() {
            InventoryBambooStack layerBambooStack = new InventoryBambooStack(5);
            layerBambooStack.collectBamboo();
            assertEquals(6, layerBambooStack.getBambooCount());
        }
    }
}
