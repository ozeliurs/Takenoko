package com.takenoko.layers.bamboo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LayerBambooStackTest {

    @Nested
    @DisplayName("Method getBamboo()")
    class TestGetBamboo {
        @Test
        @DisplayName("should return the number of bamboo")
        void shouldReturnTheNumberOfBamboo() {
            LayerBambooStack layerBambooStack = new LayerBambooStack(5);
            assertEquals(5, layerBambooStack.getBambooCount());
        }
    }

    @Nested
    @DisplayName("Method addBamboo()")
    class TestAddBamboo {
        @Test
        @DisplayName("should add a bamboo")
        void shouldAddABamboo() {
            LayerBambooStack layerBambooStack = new LayerBambooStack(5);
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
            LayerBambooStack layerBambooStack = new LayerBambooStack(5);
            layerBambooStack.eatBamboo();
            assertEquals(4, layerBambooStack.getBambooCount());
        }

        @Test
        @DisplayName("should throw an exception if there is no bamboo")
        void shouldThrowAnExceptionIfThereIsNoBamboo() {
            LayerBambooStack layerBambooStack = new LayerBambooStack(0);
            assertThrows(IllegalArgumentException.class, layerBambooStack::eatBamboo);
        }
    }

    @Nested
    @DisplayName("Method equals()")
    class TestEquals {
        @Test
        @DisplayName("should return true if the bamboo stacks are equal")
        void shouldReturnTrueIfTheBambooStacksAreEqual() {
            LayerBambooStack layerBambooStack1 = new LayerBambooStack(5);
            LayerBambooStack layerBambooStack2 = new LayerBambooStack(5);
            assertEquals(layerBambooStack1, layerBambooStack2);
        }

        @Test
        @DisplayName("should return false if the bamboo stacks are not equal")
        void shouldReturnFalseIfTheBambooStacksAreNotEqual() {
            LayerBambooStack layerBambooStack1 = new LayerBambooStack(5);
            LayerBambooStack layerBambooStack2 = new LayerBambooStack(6);
            assertNotEquals(layerBambooStack1, layerBambooStack2);
        }
    }

    @Nested
    @DisplayName("Method hashCode()")
    class TestHashCode {
        @Test
        @DisplayName("should return the same hash code if the bamboo stacks are equal")
        void shouldReturnTheSameHashCodeIfTheBambooStacksAreEqual() {
            LayerBambooStack layerBambooStack1 = new LayerBambooStack(5);
            LayerBambooStack layerBambooStack2 = new LayerBambooStack(5);
            assertEquals(layerBambooStack1.hashCode(), layerBambooStack2.hashCode());
        }

        @Test
        @DisplayName("should return a different hash code if the bamboo stacks are not equal")
        void shouldReturnADifferentHashCodeIfTheBambooStacksAreNotEqual() {
            LayerBambooStack layerBambooStack1 = new LayerBambooStack(5);
            LayerBambooStack layerBambooStack2 = new LayerBambooStack(6);
            assertNotEquals(layerBambooStack1.hashCode(), layerBambooStack2.hashCode());
        }
    }

    @Nested
    @DisplayName("Method copy()")
    class TestCopy {
        @Test
        @DisplayName("should return a copy of the bamboo stack")
        void shouldReturnACopyOfTheBambooStack() {
            LayerBambooStack layerBambooStack1 = new LayerBambooStack(5);
            LayerBambooStack layerBambooStack2 = layerBambooStack1.copy();
            assertThat(layerBambooStack1)
                    .isEqualTo(layerBambooStack2)
                    .isNotSameAs(layerBambooStack2);
        }
    }
}
