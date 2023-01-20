package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.ChooseIfApplyWeatherAction;
import com.takenoko.actions.MoveGardenerAction;
import com.takenoko.actions.annotations.ApplyImprovementFromInventoryAction;
import com.takenoko.vector.PositionVector;
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
            botState.addAvailableAction(ChooseIfApplyWeatherAction.class);
            botState.addAvailableAction(ChooseIfApplyWeatherAction.class);
            botState.addAvailableAction(Action.class);
            assertThat(botState.getAvailableActions()).hasSize(2);
        }
    }

    @Nested
    @DisplayName("Method updateAvailableActions()")
    class TestUpdateAvailableActions {
        @Test
        @DisplayName("should remove the forced actions")
        void updateAvailableActions_shouldRemoveForcedActions() {
            botState.addAvailableAction(ChooseIfApplyWeatherAction.class);
            botState.addAvailableAction(ChooseIfApplyWeatherAction.class);
            botState.updateAvailableActions(
                    new ChooseIfApplyWeatherAction(false), new ActionResult());
            assertThat(botState.getAvailableActions()).isEmpty();
        }

        @Test
        @DisplayName("should remove the current action")
        void updateAvailableActions_shouldRemoveCurrentAction() {
            botState.addAvailableAction(MoveGardenerAction.class);
            botState.updateAvailableActions(
                    new MoveGardenerAction(mock(PositionVector.class)), new ActionResult());
            assertThat(botState.getAvailableActions()).isEmpty();
        }

        @Test
        @DisplayName("should keep persistant actions")
        void updateAvailableActions_shouldKeepPersistantActions() {
            botState.addAvailableAction(MoveGardenerAction.class);
            botState.addAvailableAction(ApplyImprovementFromInventoryAction.class);
            botState.updateAvailableActions(
                    new MoveGardenerAction(mock(PositionVector.class)), new ActionResult());
            assertThat(botState.getAvailableActions()).hasSize(1);
        }
    }
}
