package com.takenoko.layers.bamboo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BambooStackTest {

    @Nested
    @DisplayName("Method getBamboo()")
    class TestGetBamboo {
        @Test
        @DisplayName("should return the number of bamboo")
        void shouldReturnTheNumberOfBamboo() {
            BambooStack bambooStack = new BambooStack(5);
            assertEquals(5, bambooStack.getBamboo());
        }
    }

    @Nested
    @DisplayName("Method addBamboo()")
    class TestAddBamboo {
        @Test
        @DisplayName("should add a bamboo")
        void shouldAddABamboo() {
            BambooStack bambooStack = new BambooStack(5);
            bambooStack.addBamboo();
            assertEquals(6, bambooStack.getBamboo());
        }
    }

    @Nested
    @DisplayName("Method removeBamboo()")
    class TestRemoveBamboo {
        @Test
        @DisplayName("should remove a bamboo")
        void shouldRemoveABamboo() {
            BambooStack bambooStack = new BambooStack(5);
            bambooStack.subBamboo();
            assertEquals(4, bambooStack.getBamboo());
        }

        @Test
        @DisplayName("should throw an exception if there is no bamboo")
        void shouldThrowAnExceptionIfThereIsNoBamboo() {
            BambooStack bambooStack = new BambooStack(0);
            assertThrows(IllegalArgumentException.class, bambooStack::subBamboo);
        }
    }

    @Nested
    @DisplayName("Method equals()")
    class TestEquals {
        @Test
        @DisplayName("should return true if the bamboo stacks are equal")
        void shouldReturnTrueIfTheBambooStacksAreEqual() {
            BambooStack bambooStack1 = new BambooStack(5);
            BambooStack bambooStack2 = new BambooStack(5);
            assertEquals(bambooStack1, bambooStack2);
        }

        @Test
        @DisplayName("should return false if the bamboo stacks are not equal")
        void shouldReturnFalseIfTheBambooStacksAreNotEqual() {
            BambooStack bambooStack1 = new BambooStack(5);
            BambooStack bambooStack2 = new BambooStack(6);
            assertNotEquals(bambooStack1, bambooStack2);
        }
    }

    @Nested
    @DisplayName("Method hashCode()")
    class TestHashCode {
        @Test
        @DisplayName("should return the same hash code if the bamboo stacks are equal")
        void shouldReturnTheSameHashCodeIfTheBambooStacksAreEqual() {
            BambooStack bambooStack1 = new BambooStack(5);
            BambooStack bambooStack2 = new BambooStack(5);
            assertEquals(bambooStack1.hashCode(), bambooStack2.hashCode());
        }

        @Test
        @DisplayName("should return a different hash code if the bamboo stacks are not equal")
        void shouldReturnADifferentHashCodeIfTheBambooStacksAreNotEqual() {
            BambooStack bambooStack1 = new BambooStack(5);
            BambooStack bambooStack2 = new BambooStack(6);
            assertNotEquals(bambooStack1.hashCode(), bambooStack2.hashCode());
        }
    }
}
