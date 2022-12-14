package com.takenoko.objective;

import static org.junit.jupiter.api.Assertions.*;

import com.takenoko.Board;
import com.takenoko.tile.Tile;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.*;

class TwoAdjacentTilesObjectiveTest {

    private TwoAdjacentTilesObjective twoAdjacentTilesObjective;
    private Board board;

    @BeforeEach
    void setUp() {
        twoAdjacentTilesObjective = new TwoAdjacentTilesObjective();
        board = new Board();
    }

    @AfterEach
    void tearDown() {
        twoAdjacentTilesObjective = null;
        board = null;
    }

    @Nested
    @DisplayName("Method verify")
    class TestVerify {
        @Test
        @DisplayName("When board has two tiles placed, state is ACHIEVED")
        void verify_WhenBoardHasTwoTilesNextToEachOther_ThenObjectiveStateIsACHIEVED() {
            board.placeTile(new Tile(), new PositionVector(0, -1, 1));
            board.placeTile(new Tile(), new PositionVector(1, -1, 0));
            twoAdjacentTilesObjective.verify(board);
            assertEquals(ObjectiveState.ACHIEVED, twoAdjacentTilesObjective.getState());
        }
    }

    @Nested
    @DisplayName("Method isAchieved")
    class TestIsAchieved {
        @Test
        @DisplayName("When Objective is initialized return false")
        void isAchieved_WhenObjectiveIsInitialized_ThenReturnsFalse() {
            assertFalse(twoAdjacentTilesObjective.isAchieved());
        }

        @Test
        @DisplayName("When Objective is achieved return true")
        void isAchieved_WhenObjectiveIsAchieved_ThenReturnsTrue() {
            board.placeTile(new Tile(), new PositionVector(0, -1, 1));
            board.placeTile(new Tile(), new PositionVector(1, -1, 0));
            twoAdjacentTilesObjective.verify(board);
            assertTrue(twoAdjacentTilesObjective.isAchieved());
        }
    }

    @Nested
    @DisplayName("Method toString")
    class TestToString {
        @Test
        @DisplayName("When Objective is initialized return correct string")
        void toString_WhenObjectiveIsInitialized_ThenReturnsCorrectString() {
            assertEquals(
                    "TwoAdjacentTilesObjective{state=NOT_ACHIEVED}",
                    twoAdjacentTilesObjective.toString());
        }
    }

    @Nested
    @DisplayName("Method getState")
    class TestGetState {
        @Test
        @DisplayName("When Objective is initialized return correct state")
        void getState_WhenObjectiveIsInitialized_ThenReturnsCorrectState() {
            assertEquals(ObjectiveState.NOT_ACHIEVED, twoAdjacentTilesObjective.getState());
        }
    }
}
