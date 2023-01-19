package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;

import com.takenoko.actions.Action;
import com.takenoko.actions.ChooseIfApplyWeatherAction;
import com.takenoko.actions.annotations.DefaultAction;
import com.takenoko.actions.annotations.ForcedAction;
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

    @Nested
    @DisplayName("Method getAvailableActions()")
    class TestGetAvailableActions {
        @Test
        @DisplayName("should return the list of available actions")
        void getAvailableActions_shouldReturnListOfAvailableActions() {
            assertThat(botState.getAvailableActions()).isEmpty();
        }

        @Test
        @DisplayName("should return only the forced actions if there are forced actions")
        void getAvailableActions_shouldReturnOnlyForcedActions() {
            botState.addAvailableAction(ForcedAction.class);
            botState.addAvailableAction(ForcedAction.class);
            botState.addAvailableAction(DefaultAction.class);
            assertThat(botState.getAvailableActions()).hasSize(2);
        }
    }

    @Nested
    @DisplayName("Method clearForcedActions()")
    class TestClearForcedActions {
        @Test
        @DisplayName("should remove all the forced actions")
        void clearForcedActions_shouldRemoveAllForcedActions() {
            botState.addAvailableAction(ChooseIfApplyWeatherAction.class);
            botState.addAvailableAction(ChooseIfApplyWeatherAction.class);
            botState.addAvailableAction(Action.class);
            botState.clearForcedActions();
            assertThat(botState.getAvailableActions()).hasSize(1);
        }
    }
}
