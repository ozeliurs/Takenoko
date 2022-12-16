package com.takenoko.objective;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.actors.ActorsManager;
import com.takenoko.actors.panda.Panda;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.*;

public class MovePandaObjectiveTest {

    private Board board;
    private MovePandaObjective movePandaObjective;
    private BotManager botManager;

    @BeforeEach
    public void setUp() {
        board = new Board();
        movePandaObjective = new MovePandaObjective();
        botManager = mock(BotManager.class);
    }

    @AfterEach
    public void tearDown() {
        board = null;
        movePandaObjective = null;
        botManager = null;
    }

    @Nested
    @DisplayName("Method toString")
    class TestToString {
        @Test
        @DisplayName("Returns the correct string")
        void toString_ThenReturnsCorrectString() {
            assertThat(movePandaObjective).hasToString("MovePandaObjective{state=NOT_ACHIEVED}");
        }
    }

    @Nested
    @DisplayName("Method getType")
    class TestGetType {
        @Test
        @DisplayName("Returns the correct type")
        void getType_ThenReturnsCorrectType() {
            assertThat(movePandaObjective.getType()).isEqualTo(ObjectiveTypes.MOVED_PANDA);
        }
    }

    @Nested
    @DisplayName("Method getState")
    class TestGetState {
        @Test
        @DisplayName("When the objective is not achieved, returns NOT_ACHIEVED")
        void getState_WhenObjectiveIsNotAchieved_ThenReturnsNOT_ACHIEVED() {
            assertThat(movePandaObjective.getState()).isEqualTo(ObjectiveState.NOT_ACHIEVED);
        }

        @Test
        @DisplayName("When the objective is achieved, returns ACHIEVED")
        void getState_WhenObjectiveIsAchieved_ThenReturnsACHIEVED() {
            board = mock(Board.class);
            when(board.getActorsManager()).thenReturn(mock(ActorsManager.class));
            when(board.getActorsManager().getPanda()).thenReturn(mock(Panda.class));
            when(board.getActorsManager().getPanda().getPosition())
                    .thenReturn(new PositionVector(-1, 0, 1));

            BotManager botManager = mock(BotManager.class);

            movePandaObjective.verify(board, botManager);
            assertThat(movePandaObjective.getState()).isEqualTo(ObjectiveState.ACHIEVED);
        }
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {
        @Test
        @DisplayName("When the objective is compared to itself, returns true")
        @SuppressWarnings("EqualsWithItself")
        void equals_WhenObjectiveIsComparedToItself_ThenReturnsTrue() {
            assertThat(movePandaObjective.equals(movePandaObjective)).isTrue();
        }

        @Test
        @DisplayName("When the objective is compared to null, returns false")
        @SuppressWarnings("ConstantConditions")
        void equals_WhenObjectiveIsComparedToNull_ThenReturnsFalse() {
            assertThat(movePandaObjective.equals(null)).isFalse();
        }

        @Test
        @DisplayName(
                "When the objective is compared to an object of a different class, returns false")
        void equals_WhenObjectiveIsComparedToDifferentClass_ThenReturnsFalse() {
            assertThat(movePandaObjective.equals(new Object())).isFalse();
        }

        @Test
        @DisplayName(
                "When the objective is compared to an objective of a different type, returns false")
        void equals_WhenObjectiveIsComparedToDifferentType_ThenReturnsFalse() {
            assertThat(movePandaObjective.equals(new PlaceTileObjective(1))).isFalse();
        }

        @Test
        @DisplayName(
                "When the objective is compared to an objective of the same type, returns true")
        void equals_WhenObjectiveIsComparedToSameType_ThenReturnsTrue() {
            assertThat(movePandaObjective.equals(new MovePandaObjective())).isTrue();
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("When the objective is compared to itself, returns the same hash code")
        void hashCode_WhenObjectiveIsComparedToItself_ThenReturnsSameHashCode() {
            assertThat(movePandaObjective).hasSameHashCodeAs(movePandaObjective);
        }

        @Test
        @DisplayName(
                "When the objective is compared to an objective of the same type, returns the same"
                        + " hash code")
        void hashCode_WhenObjectiveIsComparedToSameType_ThenReturnsSameHashCode() {
            assertThat(movePandaObjective).hasSameHashCodeAs(new MovePandaObjective());
        }

        @Test
        @DisplayName(
                "When the objective is compared to an objective of a different type, returns a"
                        + " different hash code")
        void hashCode_WhenObjectiveIsComparedToDifferentType_ThenReturnsDifferentHashCode() {
            assertThat(movePandaObjective.hashCode())
                    .isNotEqualTo(new PlaceTileObjective(1).hashCode());
        }
    }
}
