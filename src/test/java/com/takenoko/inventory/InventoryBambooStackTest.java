package com.takenoko.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
            layerBambooStack.addBamboo();
            assertEquals(6, layerBambooStack.getBambooCount());
        }
    }

    @Nested
    @DisplayName("Method removeBamboo()")
    class TestRemoveBamboo {
        @Test
        @DisplayName("should remove a bamboo")
        void shouldRemoveABamboo() {
            InventoryBambooStack layerBambooStack = new InventoryBambooStack(5);
            layerBambooStack.subBamboo();
            assertEquals(4, layerBambooStack.getBambooCount());
        }

        @Test
        @DisplayName("should throw an exception if there is no bamboo")
        void shouldThrowAnExceptionIfThereIsNoBamboo() {
            InventoryBambooStack layerBambooStack = new InventoryBambooStack(0);
            assertThrows(IllegalArgumentException.class, layerBambooStack::subBamboo);
        }
    }
}
