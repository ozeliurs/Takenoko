package com.takenoko.objective;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.LayerManager;
import com.takenoko.layers.tile.TileLayer;
import com.takenoko.tile.Pond;
import com.takenoko.tile.Tile;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import org.junit.jupiter.api.*;
import org.springframework.test.util.ReflectionTestUtils;

class PlaceTileObjectiveTest {

    private PlaceTileObjective placeTileObjective;
    private Board board;
    private BotManager botManager;

    @BeforeEach
    void setup() {
        placeTileObjective = new PlaceTileObjective(2);
        board = new Board();
        botManager = mock(BotManager.class);
    }

    @AfterEach
    void tearDown() {
        placeTileObjective = null;
        board = null;
        botManager = null;
    }

    @Nested
    @DisplayName("Method verify")
    class TestVerify {
        @Test
        @DisplayName("When board has no tiles placed, state is NOT_ACHIEVED")
        void verify_WhenBoardHasNoTiles_ThenObjectiveStateIsNOT_ACHIEVED() {
            placeTileObjective.verify(board, botManager);
            assertThat(placeTileObjective.getState()).isEqualTo(ObjectiveState.NOT_ACHIEVED);
        }

        @Test
        @DisplayName("When board has two tiles placed, state is ACHIEVED")
        void verify_WhenBoardHasTwoTiles_ThenObjectiveStateIsACHIEVED() {
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            tiles.put(new PositionVector(0, 0, 0), new Pond());
            tiles.put(new PositionVector(1, 0, -1), new Tile());
            tiles.put(new PositionVector(1, -1, 0), new Tile());

            TileLayer tileLayer = mock(TileLayer.class);
            when(tileLayer.getTiles()).thenReturn(tiles);
            for (PositionVector position : tiles.keySet()) {
                when(tileLayer.isTile(position)).thenReturn(true);
            }

            LayerManager layerManager = mock(LayerManager.class);
            when(layerManager.getTileLayer()).thenReturn(tileLayer);
            board = mock(Board.class);
            when(board.getLayerManager()).thenReturn(layerManager);
            placeTileObjective.verify(board, botManager);
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
            // use reflection to set the private field
            ReflectionTestUtils.setField(placeTileObjective, "state", ObjectiveState.ACHIEVED);
            placeTileObjective.verify(board, botManager);
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
