package com.takenoko.objective;

import static org.assertj.core.api.Assertions.assertThat;

import com.takenoko.Board;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class MovedPandaObjectiveTest {

    private Board board;
    private MovedPandaObjective movedPandaObjective;

    @BeforeEach
    void setUp() {
        movedPandaObjective = new MovedPandaObjective();
        board = new Board();
        board.placeTile(board.getAvailableTiles().get(0), new PositionVector(0, 1, -1));
    }

    @Nested
    @DisplayName("Method isAchieved and verify")
    class TestIsAchieved {
        @Test
        @DisplayName("When the panda has moved, returns true")
        void isAchieved_WhenPandaHasMoved_ThenReturnsTrue() {
            board.getPanda().move(new PositionVector(0, 1, -1), board);
            movedPandaObjective.verify(board);
            assertThat(movedPandaObjective.isAchieved()).isTrue();
        }

        @Test
        @DisplayName("When the panda has not moved, returns false")
        void isAchieved_WhenPandaHasNotMoved_ThenReturnsFalse() {
            movedPandaObjective.verify(board);
            assertThat(movedPandaObjective.isAchieved()).isFalse();
        }
    }

    @Nested
    @DisplayName("Method toString")
    class TestToString {
        @Test
        @DisplayName("When the panda has not moved, returns false")
        void toString_WhenPandaHasNotMoved_ThenReturnsFalse() {
            movedPandaObjective.verify(board);
            assertThat(movedPandaObjective).hasToString("MovedPandaObjective{state=NOT_ACHIEVED}");
        }
    }

    @Nested
    @DisplayName("Method getState")
    class TestGetState {
        @Test
        @DisplayName("When the panda has not moved, returns NOT_ACHIEVED")
        void getState_WhenPandaHasNotMoved_ThenReturnsNOT_ACHIEVED() {
            movedPandaObjective.verify(board);
            assertThat(movedPandaObjective.getState()).isEqualTo(ObjectiveState.NOT_ACHIEVED);
        }
    }

    @Nested
    @DisplayName("Method getType")
    class TestGetType {
        @Test
        @DisplayName("When the panda has not moved, returns MOVED_PANDA")
        void getType_WhenPandaHasNotMoved_ThenReturnsMOVED_PANDA() {
            movedPandaObjective.verify(board);
            assertThat(movedPandaObjective.getType()).isEqualTo(ObjectiveTypes.MOVED_PANDA);
        }
    }
}
