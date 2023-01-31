package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.improvement.ApplyImprovementFromInventoryAction;
import com.takenoko.actions.objective.DrawObjectiveAction;
import com.takenoko.actions.objective.RedeemObjectiveAction;
import com.takenoko.actions.weather.ChooseIfApplyWeatherAction;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.objective.Objective;
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
            other.getInventory().getBambooStack(TileColor.ANY).collectBamboo();
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
            other.getInventory().getBambooStack(TileColor.ANY).collectBamboo();
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

    @Nested
    @DisplayName("Method canDrawObjective()")
    class TestCanDrawObjective {
        @Test
        @DisplayName("should return true if the player can draw an objective")
        void canDrawObjective_shouldReturnTrueIfCanDrawObjective() {
            assertThat(botState.canDrawObjective()).isTrue();
        }

        @Test
        @DisplayName("should return false if the player can't draw an objective")
        void canDrawObjective_shouldReturnFalseIfCantDrawObjective() {
            for (int i = 0; i < BotState.MAX_OBJECTIVES; i++) {
                botState.addObjective(mock(Objective.class));
            }
            assertThat(botState.canDrawObjective()).isFalse();
        }
    }

    @Test
    @DisplayName("test getScore")
    void test_getScore() {
        assertThat(botState.getObjectiveScore()).isZero();

        Objective objective = mock(Objective.class);
        when(objective.getPoints()).thenReturn(10);

        botState.setObjectiveAchieved(objective);
        botState.redeemObjective(objective);

        botState.addObjective(objective);

        assertThat(botState.getObjectiveScore()).isEqualTo(10);
    }

    @Nested
    @DisplayName("Method update()")
    class TestGetObjectiveScore {
        @Test
        @DisplayName("should call verifyObjectives")
        void update_shouldCallVerifyObjectives() {
            botState = spy(botState);

            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);

            botState.update(board, botManager);
            verify(botState, times(1)).verifyObjectives(board, botManager);
        }

        @Test
        @DisplayName("should call setObjectiveAchieved if some are achieved")
        void update_should_callSetObjectiveAchievedIfSomeAreAchieved() {
            Objective objective = mock(Objective.class);
            when(objective.isAchieved()).thenReturn(true);
            botState.addObjective(objective);
            botState = spy(botState);

            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);

            botState.update(board, botManager);
            verify(botState, times(1)).setObjectiveAchieved(objective);
        }

        @Test
        @DisplayName("should call setObjectiveNotAchieved if some are not achieved")
        void update_should_callSetObjectiveNotAchievedIfSomeAreNotAchieved() {
            Objective objective = mock(Objective.class);
            when(objective.isAchieved()).thenReturn(false);
            botState.addObjective(objective);
            botState.setObjectiveAchieved(objective);
            botState = spy(botState);

            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);

            botState.update(board, botManager);
            verify(botState, times(1)).setObjectiveNotAchieved(objective);
        }

        @Test
        @DisplayName("should add DrawObjectionAction is it can draw an objective")
        void update_should_addDrawObjectiveActionIfCanDrawObjective() {
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);

            botState.update(board, botManager);
            assertThat(botState.getAvailableActions()).containsExactly(DrawObjectiveAction.class);
        }

        @Test
        @DisplayName("should add RedeemObjectiveAction if it can redeem an objective")
        void update_should_addRedeemObjectiveActionIfCanRedeemObjective() {
            botState = spy(botState);

            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);

            botState.addAvailableAction(DrawObjectiveAction.class);

            when(botState.canRedeemObjective()).thenReturn(true);

            botState.update(board, botManager);
            assertThat(botState.getAvailableActions()).contains(RedeemObjectiveAction.class);
        }
    }
}
