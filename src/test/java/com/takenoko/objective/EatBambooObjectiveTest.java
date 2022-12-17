package com.takenoko.objective;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import org.junit.jupiter.api.*;

public class EatBambooObjectiveTest {

    private Board board;
    private EatBambooObjective eatBambooObjective;

    @BeforeEach
    public void setUp() {
        board = new Board();
        eatBambooObjective = new EatBambooObjective(1);
    }

    @AfterEach
    public void tearDown() {
        board = null;
        eatBambooObjective = null;
    }

    @Nested
    @DisplayName("Method toString")
    class TestToString {
        @Test
        @DisplayName("Returns the correct string")
        void toString_ThenReturnsCorrectString() {
            assertThat(eatBambooObjective)
                    .hasToString("EatBambooObjective{numberOfBamboosToEat=1, state=NOT_ACHIEVED}");
        }
    }

    @Nested
    @DisplayName("Method getType")
    class TestGetType {
        @Test
        @DisplayName("Returns the correct type")
        void getType_ThenReturnsCorrectType() {
            assertThat(eatBambooObjective.getType())
                    .isEqualTo(ObjectiveTypes.NUMBER_OF_BAMBOOS_EATEN);
        }
    }

    @Nested
    @DisplayName("Method getState")
    class TestGetState {
        @Test
        @DisplayName("When the objective is not achieved, returns NOT_ACHIEVED")
        void getState_WhenObjectiveIsNotAchieved_ThenReturnsNOT_ACHIEVED() {
            assertThat(eatBambooObjective.getState()).isEqualTo(ObjectiveState.NOT_ACHIEVED);
        }

        @Test
        @DisplayName("When the objective is achieved, returns ACHIEVED")
        void getState_WhenObjectiveIsAchieved_ThenReturnsACHIEVED() {
            BotManager botManager = mock(BotManager.class);
            when(botManager.getEatenBambooCounter()).thenReturn(1);
            eatBambooObjective.verify(board, botManager);
            assertThat(eatBambooObjective.getState()).isEqualTo(ObjectiveState.ACHIEVED);
        }
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {
        @Test
        @DisplayName("When the objective is compared to itself, returns true")
        @SuppressWarnings("EqualsWithItself")
        void equals_WhenObjectiveIsComparedToItself_ThenReturnsTrue() {
            assertThat(eatBambooObjective.equals(eatBambooObjective)).isTrue();
        }

        @Test
        @DisplayName("When the objective is compared to null, returns false")
        @SuppressWarnings("ConstantConditions")
        void equals_WhenObjectiveIsComparedToNull_ThenReturnsFalse() {
            assertThat(eatBambooObjective.equals(null)).isFalse();
        }

        @Test
        @DisplayName(
                "When the objective is compared to an object of a different class, returns false")
        void equals_WhenObjectiveIsComparedToDifferentClass_ThenReturnsFalse() {
            assertThat(eatBambooObjective.equals(new Object())).isFalse();
        }

        @Test
        @DisplayName(
                "When the objective is compared to an objective of a different type, returns false")
        @SuppressWarnings("EqualsBetweenInconvertibleTypes")
        void equals_WhenObjectiveIsComparedToDifferentType_ThenReturnsFalse() {
            assertThat(eatBambooObjective.equals(new PlaceTileObjective(1))).isFalse();
        }

        @Test
        @DisplayName(
                "When the objective is compared to an objective of the same type but different"
                        + " number of bamboos, returns false")
        void
                equals_WhenObjectiveIsComparedToSameTypeWithDifferentNumberOfBamboos_ThenReturnsFalse() {
            assertThat(eatBambooObjective.equals(new EatBambooObjective(2))).isFalse();
        }

        @Test
        @DisplayName(
                "When the objective is compared to an objective of the same state with same number"
                        + " of bamboos, returns true")
        void equals_WhenObjectiveIsComparedToSameTypeWithSameNumberOfBamboos_ThenReturnsTrue() {
            assertThat(eatBambooObjective.equals(new EatBambooObjective(1))).isTrue();
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("When the objective is compared to itself, returns the same hash code")
        void hashCode_WhenObjectiveIsComparedToItself_ThenReturnsSameHashCode() {
            assertThat(eatBambooObjective).hasSameHashCodeAs(eatBambooObjective);
        }

        @Test
        @DisplayName(
                "When the objective is compared to an objective of the same type with same number"
                        + " of bamboos, returns same hash code")
        void
                hashCode_WhenObjectiveIsComparedToSameTypeAndSameNumberOfBamboos_ThenReturnsSameHashCode() {
            assertThat(eatBambooObjective).hasSameHashCodeAs(new EatBambooObjective(1));
        }

        @Test
        @DisplayName(
                "When the objective is compared to an objective of the same type but different"
                        + " number of bamboos, returns different hash code")
        void
                hashCode_WhenObjectiveIsComparedToSameTypeWithDifferentNumberOfBamboos_ThenReturnsDifferentHashCode() {
            assertThat(eatBambooObjective).doesNotHaveSameHashCodeAs(new EatBambooObjective(2));
        }

        @Test
        @DisplayName(
                "When the objective is compared to an objective of a different type, returns a"
                        + " different hash code")
        void hashCode_WhenObjectiveIsComparedToDifferentType_ThenReturnsDifferentHashCode() {
            assertThat(eatBambooObjective).doesNotHaveSameHashCodeAs(new PlaceTileObjective(1));
        }
    }
}
