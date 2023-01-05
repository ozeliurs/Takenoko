package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BotStateTest {
    private BotState botState;

    @BeforeEach
    void setUp() {
        botState = new BotState();
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {
        @Test
        @DisplayName("should return true when the two objects are the same")
        void equals_shouldReturnTrueWhenSameObject() {
            assertThat(botState).isEqualTo(botState);
        }

        @Test
        @DisplayName("should return true when the two objects are equal")
        void equals_shouldReturnTrueWhenEqual() {
            BotState other = new BotState();
            assertThat(botState).isEqualTo(other);
        }

        @Test
        @DisplayName("should return false when the two objects are not equal")
        void equals_shouldReturnFalseWhenNotEqual() {
            BotState other = new BotState();
            other.getInventory().getBambooStack().collectBamboo();
            assertThat(botState).isNotEqualTo(other);
        }

        @Test
        @DisplayName("should return false when the other object is null")
        void equals_shouldReturnFalseWhenOtherIsNull() {
            assertThat(botState).isNotEqualTo(null);
        }

        @Test
        @DisplayName("should return false when the other object is not a BotState")
        void equals_shouldReturnFalseWhenOtherIsNotBotState() {
            assertThat(botState).isNotEqualTo(new Object());
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("should return the same hash code when the two objects are equal")
        void hashCode_shouldReturnSameHashCodeWhenEqual() {
            BotState other = new BotState();
            assertThat(botState).hasSameHashCodeAs(other);
        }

        @Test
        @DisplayName("should return a different hash code when the two objects are not equal")
        void hashCode_shouldReturnDifferentHashCodeWhenNotEqual() {
            BotState other = new BotState();
            other.getInventory().getBambooStack().collectBamboo();
            assertThat(botState).doesNotHaveSameHashCodeAs(other);
        }
    }

    @Nested
    @DisplayName("Method copy()")
    class TestCopy {
        @Test
        @DisplayName("should return a copy of the object")
        void copy_shouldReturnCopyOfObject() {
            BotState copy = botState.copy();
            assertThat(copy).isEqualTo(botState).isNotSameAs(botState);
        }
    }
}
