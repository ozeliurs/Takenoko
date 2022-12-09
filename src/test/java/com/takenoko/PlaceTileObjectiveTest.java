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

    @Nested
    @DisplayName("Method getType")
    class TestGetType {
        @Test
        @DisplayName("When Objective is initialized return PLACE_TILE")
        void getType_WhenObjectiveIsInitialized_ThenReturnsPLACE_TILE() {
            assertThat(placeTileObjective.getType())
                    .isEqualTo(ObjectiveTypes.NUMBER_OF_TILES_PLACED);
        }
    }

    @Nested
    @DisplayName("Method getNumberOfTilesToPlace")
    class TestGetNumberOfTilesToPlace {
        @Test
        @DisplayName("When Objective is initialized return 2")
        void getNumberOfTilesToPlace_WhenObjectiveIsInitialized_ThenReturns2() {
            assertThat(placeTileObjective.getNumberOfTileToPlace()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {
        @Test
        @DisplayName("When Objective is compared to null return false")
        @SuppressWarnings("ConstantConditions")
        void equals_WhenObjectiveIsComparedToNull_ThenReturnsFalse() {
            assertThat(placeTileObjective.equals(null)).isFalse();
        }

        @Test
        @DisplayName("When Objective is compared to itself return true")
        @SuppressWarnings("EqualsWithItself")
        void equals_WhenObjectiveIsComparedToItself_ThenReturnsTrue() {
            assertThat(placeTileObjective.equals(placeTileObjective)).isTrue();
        }

        @Test
        @DisplayName(
                "When Objective is compared to another Objective with same number of tiles return"
                        + " true")
        void
                equals_WhenObjectiveIsComparedToAnotherObjectiveWithSameNumberOfTiles_ThenReturnsTrue() {
            PlaceTileObjective otherPlaceTileObjective = new PlaceTileObjective(2);
            assertThat(placeTileObjective.equals(otherPlaceTileObjective)).isTrue();
        }

        @Test
        @DisplayName(
                "When Objective is compared to another Objective with different number of tiles"
                        + " return false")
        void
                equals_WhenObjectiveIsComparedToAnotherObjectiveWithDifferentNumberOfTiles_ThenReturnsFalse() {
            PlaceTileObjective otherPlaceTileObjective = new PlaceTileObjective(3);
            assertThat(placeTileObjective.equals(otherPlaceTileObjective)).isFalse();
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("When Objective is compared to itself return true")
        void hashCode_WhenObjectiveIsComparedToItself_ThenReturnsTrue() {
            assertThat(placeTileObjective.hashCode())
                    .hasSameHashCodeAs(placeTileObjective.hashCode());
        }

        @Test
        @DisplayName(
                "When Objective is compared to another Objective with same number of tiles return"
                        + " true")
        void
                hashCode_WhenObjectiveIsComparedToAnotherObjectiveWithSameNumberOfTiles_ThenReturnsTrue() {
            PlaceTileObjective otherPlaceTileObjective = new PlaceTileObjective(2);
            assertThat(placeTileObjective.hashCode())
                    .hasSameHashCodeAs(otherPlaceTileObjective.hashCode());
        }

        @Test
        @DisplayName(
                "When Objective is compared to another Objective with different number of tiles"
                        + " return false")
        void
                hashCode_WhenObjectiveIsComparedToAnotherObjectiveWithDifferentNumberOfTiles_ThenReturnsFalse() {
            PlaceTileObjective otherPlaceTileObjective = new PlaceTileObjective(3);
            assertThat(placeTileObjective.hashCode())
                    .doesNotHaveSameHashCodeAs(otherPlaceTileObjective.hashCode());
        }
    }

    @Nested
    @DisplayName("Method toString")
    class TestToString {
        @Test
        @DisplayName("When Objective is initialized return correct string")
        void toString_WhenObjectiveIsInitialized_ThenReturnsCorrectString() {
            assertThat(placeTileObjective)
                    .hasToString("PlaceTileObjective{numberOfTileToPlace=2, state=NOT_ACHIEVED}");
        }
    }
}
