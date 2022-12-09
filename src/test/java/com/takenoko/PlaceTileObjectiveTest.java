package com.takenoko;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;

class PlaceTileObjectiveTest {

    private PlaceTileObjective placeTileObjective;
    private Board board;

    @BeforeEach
    void setup() {
        placeTileObjective = new PlaceTileObjective(2);
        board = new Board();
    }

    @AfterEach
    void tearDown() {
        placeTileObjective = null;
        board = null;
    }

    @Nested
    @DisplayName("Method verify")
    class TestVerify {
        @Test
        @DisplayName("When board has no tiles placed, state is NOT_ACHIEVED")
        void verify_WhenBoardHasNoTiles_ThenObjectiveStateIsNOT_ACHIEVED() {
            placeTileObjective.verify(board);
            assertThat(placeTileObjective.getState()).isEqualTo(ObjectiveState.NOT_ACHIEVED);
        }

        @Test
        @DisplayName("When board has one tile placed, state is ACHIEVED")
        void verify_WhenBoardHasOneTile_ThenObjectiveStateIsACHIEVED() {
            Tile tile = board.getAvailableTiles().get(0);
            board.placeTile(tile, board.getAvailableTilePositions().get(0));
            placeTileObjective.verify(board);
            assertThat(placeTileObjective.getState()).isEqualTo(ObjectiveState.ACHIEVED);
        }
    }

    @Nested
    @DisplayName("Method isAchieved")
    class TestIsAchieved {
        @Test
        @DisplayName("When Objective is initialized return false")
        void isAchieved_WhenObjectiveIsInitialized_ThenReturnsFalse() {
            assertThat(placeTileObjective.isAchieved()).isFalse();
        }

        @Test
        @DisplayName("When Objective is achieved return true")
        void isAchieved_WhenObjectiveIsAchieved_ThenReturnsTrue() {
            Tile tile = board.getAvailableTiles().get(0);
            board.placeTile(tile, board.getAvailableTilePositions().get(0));
            placeTileObjective.verify(board);
            assertThat(placeTileObjective.isAchieved()).isTrue();
        }
    }
}
