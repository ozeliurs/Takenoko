package com.takenoko.layers.bamboo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BambooStackTest {
    @Nested
    @DisplayName("Method addBamboo()")
    class TestAddBamboo {
        @Test
        @DisplayName("should add a bamboo")
        void shouldAddABamboo() {
            BambooStack layerBambooStack = new BambooStack(5);
            layerBambooStack.growBamboo();
            assertEquals(6, layerBambooStack.getBambooCount());
        }
    }

    @Nested
    @DisplayName("Method removeBamboo()")
    class TestRemoveBamboo {
        @Test
        @DisplayName("should remove a bamboo")
        void shouldRemoveABamboo() {
            BambooStack layerBambooStack = new BambooStack(5);
            layerBambooStack.eatBamboo();
            assertEquals(4, layerBambooStack.getBambooCount());
        }

        @Test
        @DisplayName("should throw an exception if there is no bamboo")
        void shouldThrowAnExceptionIfThereIsNoBamboo() {
            BambooStack layerBambooStack = new BambooStack(0);
            assertThrows(IllegalArgumentException.class, layerBambooStack::eatBamboo);
        }
    }
}
