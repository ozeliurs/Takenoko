package com.takenoko.layers.bamboo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
            BambooStack bambooStack = new BambooStack(5);
            bambooStack.growBamboo();
            assertEquals(6, bambooStack.getBambooCount());
        }
    }

    @Nested
    @DisplayName("Method removeBamboo()")
    class TestRemoveBamboo {
        @Test
        @DisplayName("should remove a bamboo")
        void shouldRemoveABamboo() {
            BambooStack bambooStack = new BambooStack(5);
            bambooStack.eatBamboo();
            assertEquals(4, bambooStack.getBambooCount());
        }

        @Test
        @DisplayName("should throw an exception if there is no bamboo")
        void shouldThrowAnExceptionIfThereIsNoBamboo() {
            BambooStack bambooStack = new BambooStack(0);
            assertThrows(IllegalArgumentException.class, bambooStack::eatBamboo);
        }
    }

    @Nested
    @DisplayName("Method equals()")
    class TestEquals {
        @Test
        @DisplayName("should return true if the two objects are equal")
        void shouldReturnTrueIfTheTwoObjectsAreEqual() {
            BambooStack bambooStack = new BambooStack(5);
            BambooStack bambooStack2 = new BambooStack(5);
            assertThat(bambooStack).isEqualTo(bambooStack2);
        }

        @Test
        @DisplayName("should return true if the two objects are the same")
        void shouldReturnTrueIfTheTwoObjectsAreTheSame() {
            BambooStack bambooStack = new BambooStack(5);
            BambooStack bambooStack2 = bambooStack;
            assertThat(bambooStack).isEqualTo(bambooStack2);
        }

        @Test
        @DisplayName("should return false if the two objects are of different classes")
        void shouldReturnFalseIfTheTwoObjectsAreOfDifferentClasses() {
            BambooStack bambooStack = new BambooStack(5);
            Object object = new Object();
            assertThat(bambooStack).isNotEqualTo(object);
        }

        @Test
        @DisplayName("should return false if the two objects are not equal")
        void shouldReturnFalseIfTheTwoObjectsAreNotEqual() {
            BambooStack bambooStack = new BambooStack(5);
            BambooStack bambooStack2 = new BambooStack(6);
            assertThat(bambooStack).isNotEqualTo(bambooStack2);
        }
    }

    @Nested
    @DisplayName("Method hashCode()")
    class TestHashCode {
        @Test
        @DisplayName("should return the same hashcode if the two objects are equal")
        void shouldReturnTheSameHashcodeIfTheTwoObjectsAreEqual() {
            BambooStack bambooStack = new BambooStack(5);
            BambooStack bambooStack2 = new BambooStack(5);
            assertThat(bambooStack).hasSameHashCodeAs(bambooStack2);
        }

        @Test
        @DisplayName("should return a different hashcode if the two objects are not equal")
        void shouldReturnADifferentHashcodeIfTheTwoObjectsAreNotEqual() {
            BambooStack bambooStack = new BambooStack(5);
            BambooStack bambooStack2 = new BambooStack(6);
            assertThat(bambooStack).doesNotHaveSameHashCodeAs(bambooStack2);
        }
    }

    @Nested
    @DisplayName("Method copy()")
    class TestCopy {
        @Test
        @DisplayName("should return a copy of the BambooStack")
        void shouldReturnACopyOfTheBambooStack() {
            BambooStack bambooStack = new BambooStack(5);
            BambooStack bambooStack2 = bambooStack.copy();
            assertThat(bambooStack).isEqualTo(bambooStack2).isNotSameAs(bambooStack2);
        }
    }

    @Nested
    @DisplayName("Method isEmpty()")
    class TestIsEmpty {
        @Test
        @DisplayName("should return true when it is empty")
        void shouldReturnTrueWhenItIsEmpty() {
            BambooStack bambooStack = new BambooStack(0);
            assertThat(bambooStack.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("should return false when it is not empty")
        void shouldReturnFalseWhenItIsNotEmpty() {
            BambooStack bambooStack = new BambooStack(1);
            assertThat(bambooStack.isEmpty()).isFalse();
        }
    }
}
