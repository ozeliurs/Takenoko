package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.actions.Action;
import com.takenoko.actions.ActionResult;
import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.improvement.ApplyImprovementFromInventoryAction;
import com.takenoko.actions.objective.DrawObjectiveAction;
import com.takenoko.actions.weather.ChooseIfApplyWeatherAction;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.objective.Objective;
import com.takenoko.objective.PandaObjective;
import com.takenoko.vector.PositionVector;
import com.takenoko.weather.WeatherFactory;
import java.util.Optional;
import org.junit.jupiter.api.*;

class BotStateTest {
    private BotState botState;
    Board board;

    @BeforeEach
    void setUp() {
        board = mock(Board.class);
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
        @DisplayName("should remove already executed actions")
        void updateAvailableActions_shouldRemoveAlreadyExecutedActions() {
            botState.addAvailableAction(MoveGardenerAction.class);
            botState.addAvailableAction(DrawObjectiveAction.class);
            botState.updateAvailableActions(
                    new MoveGardenerAction(mock(PositionVector.class)), new ActionResult());
            assertThat(botState.getAvailableActions()).hasSize(1);
        }

        @Test
        @DisplayName("shouldn't remove already executed actions if windy")
        void updateAvailableActions_shouldNotRemoveAlreadyExecutedActionsIfWindy() {
            botState.addAvailableAction(MoveGardenerAction.class);
            botState.addAvailableAction(DrawObjectiveAction.class);
            when(board.getWeather()).thenReturn(Optional.of(WeatherFactory.WINDY.createWeather()));
            botState.update(board);
            assertThat(botState.getAvailableActions()).hasSize(2);
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
        @DisplayName("should add DrawObjectionAction is it can draw an objective")
        void update_should_addDrawObjectiveActionIfCanDrawObjective() {
            Board board = mock(Board.class);

            botState.update(board);
            assertThat(botState.getAvailableActions()).contains(DrawObjectiveAction.class);
        }
    }

    @Nested
    @DisplayName("Method getPandaObjectiveScore()")
    class TestGetPandaObjectiveScore {
        @Test
        @DisplayName("should return 0 if no panda objective")
        void getPandaObjectiveScore_shouldReturnZeroIfNoPandaObjective() {
            assertThat(botState.getPandaObjectiveScore()).isZero();
        }

        @Test
        @DisplayName("should return 0 if panda objective not achieved")
        void getPandaObjectiveScore_shouldReturnZeroIfPandaObjectiveNotAchieved() {
            PandaObjective objective = mock(PandaObjective.class);
            when(objective.getPoints()).thenReturn(10);
            botState.addObjective(objective);
            assertThat(botState.getPandaObjectiveScore()).isZero();
        }

        @Test
        @DisplayName("should return the score if panda objective is redeemed")
        void getPandaObjectiveScore_shouldReturnScoreIfPandaObjectiveIsRedeemed() {
            PandaObjective objective = mock(PandaObjective.class);
            when(objective.getPoints()).thenReturn(10);
            botState.addObjective(objective);
            botState.setObjectiveAchieved(objective);
            botState.redeemObjective(objective);
            assertThat(botState.getPandaObjectiveScore()).isEqualTo(10);
        }
    }
}
