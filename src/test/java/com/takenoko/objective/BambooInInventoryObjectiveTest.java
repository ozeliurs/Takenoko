package com.takenoko.objective;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import org.junit.jupiter.api.*;

public class BambooInInventoryObjectiveTest {

    private Board board;
    private BambooInInventoryObjective bambooInInventoryObjective;

    @BeforeEach
    public void setUp() {
        board = new Board();
        bambooInInventoryObjective = new BambooInInventoryObjective(1);
    }

    @AfterEach
    public void tearDown() {
        board = null;
        bambooInInventoryObjective = null;
    }

    @Nested
    @DisplayName("Method toString")
    class TestToString {
        @Test
        @DisplayName("Returns the correct string")
        void toString_ThenReturnsCorrectString() {
            assertThat(bambooInInventoryObjective)
                    .hasToString(
                            "BambooInInventoryObjective{targetBambooInInventory=1,"
                                    + " state=NOT_ACHIEVED}");
        }
    }

    @Nested
    @DisplayName("Method getType")
    class TestGetType {
        @Test
        @DisplayName("Returns the correct type")
        void getType_ThenReturnsCorrectType() {
            assertThat(bambooInInventoryObjective.getType())
                    .isEqualTo(ObjectiveTypes.NUMBER_OF_BAMBOOS_EATEN);
        }
    }

    @Nested
    @DisplayName("Method getState")
    class TestGetState {
        @Test
        @DisplayName("When the objective is not achieved, returns NOT_ACHIEVED")
        void getState_WhenObjectiveIsNotAchieved_ThenReturnsNOT_ACHIEVED() {
            assertThat(bambooInInventoryObjective.getState())
                    .isEqualTo(ObjectiveState.NOT_ACHIEVED);
        }

        @Test
        @DisplayName("When the objective is achieved, returns ACHIEVED")
        void getState_WhenObjectiveIsAchieved_ThenReturnsACHIEVED() {
            BotManager botManager = mock(BotManager.class);
            when(botManager.getEatenBambooCounter()).thenReturn(1);
            bambooInInventoryObjective.verify(board, botManager);
            assertThat(bambooInInventoryObjective.getState()).isEqualTo(ObjectiveState.ACHIEVED);
        }
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {
        @Test
        @DisplayName("When the objective is compared to itself, returns true")
        @SuppressWarnings("EqualsWithItself")
        void equals_WhenObjectiveIsComparedToItself_ThenReturnsTrue() {
            assertThat(bambooInInventoryObjective.equals(bambooInInventoryObjective)).isTrue();
        }

        @Test
        @DisplayName("When the objective is compared to null, returns false")
        @SuppressWarnings("ConstantConditions")
        void equals_WhenObjectiveIsComparedToNull_ThenReturnsFalse() {
            assertThat(bambooInInventoryObjective.equals(null)).isFalse();
        }

        @Test
        @DisplayName(
                "When the objective is compared to an object of a different class, returns false")
        void equals_WhenObjectiveIsComparedToDifferentClass_ThenReturnsFalse() {
            assertThat(bambooInInventoryObjective.equals(new Object())).isFalse();
        }

        @Test
        @DisplayName(
                "When the objective is compared to an objective of a different type, returns false")
        @SuppressWarnings("EqualsBetweenInconvertibleTypes")
        void equals_WhenObjectiveIsComparedToDifferentType_ThenReturnsFalse() {
            assertThat(bambooInInventoryObjective.equals(new Object())).isFalse();
        }

        @Test
        @DisplayName(
                "When the objective is compared to an objective of the same type but different"
                        + " number of bamboos, returns false")
        void
                equals_WhenObjectiveIsComparedToSameTypeWithDifferentNumberOfBamboos_ThenReturnsFalse() {
            assertThat(bambooInInventoryObjective.equals(new BambooInInventoryObjective(2)))
                    .isFalse();
        }

        @Test
        @DisplayName(
                "When the objective is compared to an objective of the same state with same number"
                        + " of bamboos, returns true")
        void equals_WhenObjectiveIsComparedToSameTypeWithSameNumberOfBamboos_ThenReturnsTrue() {
            assertThat(bambooInInventoryObjective.equals(new BambooInInventoryObjective(1)))
                    .isTrue();
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("When the objective is compared to itself, returns the same hash code")
        void hashCode_WhenObjectiveIsComparedToItself_ThenReturnsSameHashCode() {
            assertThat(bambooInInventoryObjective).hasSameHashCodeAs(bambooInInventoryObjective);
        }

        @Test
        @DisplayName(
                "When the objective is compared to an objective of the same type with same number"
                        + " of bamboos, returns same hash code")
        void
                hashCode_WhenObjectiveIsComparedToSameTypeAndSameNumberOfBamboos_ThenReturnsSameHashCode() {
            assertThat(bambooInInventoryObjective)
                    .hasSameHashCodeAs(new BambooInInventoryObjective(1));
        }

        @Test
        @DisplayName(
                "When the objective is compared to an objective of the same type but different"
                        + " number of bamboos, returns different hash code")
        void
                hashCode_WhenObjectiveIsComparedToSameTypeWithDifferentNumberOfBamboos_ThenReturnsDifferentHashCode() {
            assertThat(bambooInInventoryObjective)
                    .doesNotHaveSameHashCodeAs(new BambooInInventoryObjective(2));
        }

        @Test
        @DisplayName(
                "When the objective is compared to an objective of a different type, returns a"
                        + " different hash code")
        void hashCode_WhenObjectiveIsComparedToDifferentType_ThenReturnsDifferentHashCode() {
            assertThat(bambooInInventoryObjective).doesNotHaveSameHashCodeAs(new Object());
        }
    }

    @Nested
    @DisplayName("Method getCompletion")
    class TestGetCompletion {
        @Test
        @DisplayName("When the objective is not achieved, returns 0")
        void getCompletion_WhenObjectiveIsNotAchieved_ThenReturns0() {
            assertThat(bambooInInventoryObjective.getCompletion(board, mock(BotManager.class)))
                    .isZero();
        }

        @Test
        @DisplayName("When the objective is started, returns 0.5")
        void getCompletion_WhenObjectiveIsStarted_ThenReturns05() {
            bambooInInventoryObjective = new BambooInInventoryObjective(2);
            BotManager botManager = mock(BotManager.class);
            when(botManager.getEatenBambooCounter()).thenReturn(1);
            bambooInInventoryObjective.verify(board, botManager);
            assertThat(bambooInInventoryObjective.getCompletion(board, botManager)).isEqualTo(0.5f);
        }

        @Test
        @DisplayName("When the objective is achieved, returns 1")
        void getCompletion_WhenObjectiveIsAchieved_ThenReturns1() {
            bambooInInventoryObjective = new BambooInInventoryObjective(2);
            BotManager botManager = mock(BotManager.class);
            when(botManager.getEatenBambooCounter()).thenReturn(2);
            bambooInInventoryObjective.verify(board, botManager);
            assertThat(bambooInInventoryObjective.getCompletion(board, botManager)).isEqualTo(1);
        }
    }
}
