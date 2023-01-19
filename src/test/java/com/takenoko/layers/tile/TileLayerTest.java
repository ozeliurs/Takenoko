package com.takenoko.layers.tile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.vector.PositionVector;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.*;

/** Test class for the Board class. */
class TileLayerTest {
    private TileLayer tileLayer;
    Board board;

    @BeforeEach
    void setUp() {
        tileLayer = new TileLayer();
        board = mock(Board.class);
    }

    @AfterEach
    void tearDown() {
        tileLayer = null;
        board = null;
    }

    @Nested
    @DisplayName("Method getTile")
    class TestGetTiles {
        /** Test that the board contains only pond when created. */
        @Test
        @DisplayName(
                "should return a list of size 1 containing only a pond when the board is created")
        void getTiles_shouldReturnOnlyOneItem() {
            assertThat(tileLayer.getTiles()).hasSize(1);
            assertThat(tileLayer.getTiles()).containsKey(new PositionVector(0, 0, 0));
            assertThat(tileLayer.getTiles().get(new PositionVector(0, 0, 0)).getType())
                    .isEqualTo(TileType.POND);
        }
    }

    @Nested
    @DisplayName("Method getTilesWithoutPond")
    class TestGetTilesWithoutPond {
        @Test
        @DisplayName("should return a list of size 1 when a tile is added")
        void getTilesWithoutPond_shouldReturnOnlyOneItem() {
            tileLayer.placeTile(new Tile(), new PositionVector(1, 0, -1), board);
            assertThat(tileLayer.getTilesWithoutPond()).hasSize(1);
        }
    }

    @Nested
    @DisplayName("Method getAvailableTiles")
    class TestGetAvailableTiles {
        @Test
        @DisplayName("should return a list with at least one tile when the board is created")
        void getAvailableTiles_shouldReturnListWithAtLeastOneTile() {
            assertThat(tileLayer.getAvailableTiles()).hasSizeGreaterThanOrEqualTo(1);
        }
    }

    @Nested
    @DisplayName("Method placeTile")
    class TestPlaceTile {
        /** Test that a tile can be placed on the board. */
        @Test
        @DisplayName("should place a tile on the board")
        void placeTile_WhenCalled_AddsTileToBoard() {
            Tile tile = new Tile();
            tileLayer.placeTile(tile, tileLayer.getAvailableTilePositions().get(0), board);
            assertThat(tileLayer.getTiles()).containsValue(tile);
        }

        @Test
        @DisplayName("should grow a bamboo on the tile")
        void placeTile_WhenCalled_GrowsBambooOnTile() {
            Tile tile = new Tile();
            PositionVector position = tileLayer.getAvailableTilePositions().get(0);
            tileLayer.placeTile(tile, position, board);
            verify(board).growBamboo(position);
        }

        @Test
        @DisplayName("should remove the vector from the available tiles positions when placed")
        void placeTile_WhenCalled_RemovesPositionVectorFromAvailablePositions() {
            PositionVector vector = tileLayer.getAvailableTilePositions().get(0);
            tileLayer.placeTile(new Tile(), vector, board);
            assertThat(tileLayer.getAvailableTilePositions()).doesNotContain(vector);
        }

        @Test
        @DisplayName("should throw an exception when there is already a tile in the position")
        void placeTile_WhenTileAlreadyAtPosition_ThrowsException() {
            Tile t = new Tile(TileType.OTHER);
            PositionVector p = new PositionVector(0, 0, 0);
            assertThatThrownBy(() -> tileLayer.placeTile(t, p, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Tile already present at this position");
        }

        @Test
        @DisplayName("should throw an exception when the position is not available (not adjacent)")
        void placeTile_WhenPositionNotAvailable_ThrowsException() {
            Tile t = new Tile(TileType.OTHER);
            PositionVector p = new PositionVector(100, 0, -100);
            assertThatThrownBy(() -> tileLayer.placeTile(t, p, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Tile position not available");
        }
    }

    @Nested
    @DisplayName("Method updateAvailableTilePositions")
    class TestUpdateAvailableTilePositions {
        PositionVector pm101;
        PositionVector pm110;
        PositionVector pm211;
        PositionVector pm220;
        PositionVector pm202;

        @BeforeEach
        void setUp() {
            tileLayer = new TileLayer();
            this.pm101 = new PositionVector(-1, 0, 1);
            this.pm110 = new PositionVector(-1, 1, 0);
            this.pm211 = new PositionVector(-2, 1, 1);
            this.pm202 = new PositionVector(-2, 0, 2);
            this.pm220 = new PositionVector(-2, 2, 0);
        }

        @Test
        @DisplayName("tiles next to the pond should be available")
        void TilesNextToPondShouldBeAvailable() {
            // board.getTiles().get(new PositionVector(0, 0, 0)).
            assertThat(tileLayer.getAvailableTilePositions())
                    .containsAll(
                            Arrays.asList(
                                    new PositionVector(1, -1, 0),
                                    new PositionVector(1, 0, -1),
                                    new PositionVector(0, 1, -1),
                                    new PositionVector(-1, 1, 0),
                                    new PositionVector(-1, 0, 1),
                                    new PositionVector(0, -1, 1)));
        }

        @Test
        @DisplayName("two adjacent tiles should create one available position next to them")
        void updateAvailableTilePositions_WhenCalled_AddsPositionToAvailableTilePositions() {
            Tile t = new Tile();
            PositionVector p = new PositionVector(1, -1, 0);
            tileLayer.placeTile(t, p, board);
            assertThat(tileLayer.getAvailableTilePositions()).doesNotContain(p);

            t = new Tile();
            p = new PositionVector(1, 0, -1);
            tileLayer.placeTile(t, p, board);
            assertThat(tileLayer.getAvailableTilePositions()).doesNotContain(p);

            p = new PositionVector(2, -1, -1);
            assertThat(tileLayer.getAvailableTilePositions()).contains(p);
        }

        @Test
        @DisplayName("two adjacent tiles should create two available position next to them")
        void updateAvailableTilePositions_WhenCalled_AddsTwoPositionToAvailableTilePositions() {
            for (PositionVector v : Arrays.asList(pm101, pm110, pm211)) {
                tileLayer.placeTile(new Tile(), v, board);
                assertThat(tileLayer.getAvailableTilePositions()).doesNotContain(v);
            }

            assertThat(tileLayer.getAvailableTilePositions()).contains(pm202);
            assertThat(tileLayer.getAvailableTilePositions()).contains(pm220);

            assertThat(tileLayer.getAvailableTilePositions()).hasSize(6);
            assertThat(tileLayer.getAvailableTilePositions())
                    .containsAll(
                            Arrays.asList(
                                    new PositionVector(1, -1, 0),
                                    new PositionVector(1, 0, -1),
                                    new PositionVector(0, 1, -1),
                                    new PositionVector(-2, 2, 0),
                                    new PositionVector(-2, 0, 2),
                                    new PositionVector(0, -1, 1)));
        }
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {
        @Test
        @DisplayName("should return true when the two objects are the same")
        void equals_shouldReturnTrueWhenSameObject() {
            assertThat(tileLayer).isEqualTo(tileLayer);
        }

        @Test
        @DisplayName("should return true when the two objects are equal")
        void equals_shouldReturnTrueWhenEqual() {
            TileLayer other = new TileLayer();
            assertThat(tileLayer).isEqualTo(other);
        }

        @Test
        @DisplayName("should return false when the two objects are not equal")
        void equals_shouldReturnFalseWhenNotEqual() {
            TileLayer other = new TileLayer();
            other.placeTile(new Tile(), new PositionVector(1, -1, 0), board);
            assertThat(tileLayer).isNotEqualTo(other);
        }

        @Test
        @DisplayName("should return false when the other object is null")
        void equals_shouldReturnFalseWhenOtherIsNull() {
            assertThat(tileLayer).isNotEqualTo(null);
        }

        @Test
        @DisplayName("should return false when the other object is not a TileLayer")
        void equals_shouldReturnFalseWhenOtherIsNotTileLayer() {
            assertThat(tileLayer).isNotEqualTo(new Object());
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("should return the same hash code when the two objects are equal")
        void hashCode_shouldReturnSameHashCodeWhenEqual() {
            TileLayer other = new TileLayer();
            assertThat(tileLayer).hasSameHashCodeAs(other);
        }

        @Test
        @DisplayName("should return a different hash code when the two objects are not equal")
        void hashCode_shouldReturnDifferentHashCodeWhenNotEqual() {
            TileLayer other = new TileLayer();
            other.placeTile(new Tile(), new PositionVector(1, -1, 0), board);
            assertThat(tileLayer).doesNotHaveSameHashCodeAs(other);
        }
    }

    @Nested
    @DisplayName("Method copy()")
    class TestCopy {
        @Test
        @DisplayName("should return a copy of the object")
        void copy_shouldReturnCopyOfObject() {
            TileLayer copy = tileLayer.copy();
            assertThat(copy).isEqualTo(tileLayer).isNotSameAs(tileLayer);
        }
    }

    @Nested
    @DisplayName("Method getAvailableImprovementPositions()")
    class TestGetAvailableImprovementPositions {
        @Test
        @DisplayName("should return not return any positions when there are no tiles")
        void getAvailableImprovementPositions_shouldReturnEmptyListWhenNoTiles() {
            Board board = mock(Board.class);
            when(board.getTiles()).thenReturn(new HashMap<>());
            assertThat(tileLayer.getAvailableImprovementPositions(board)).isEmpty();
        }

        @Test
        @DisplayName(
                "should return not return any positions when there are tiles with improvements")
        void getAvailableImprovementPositions_shouldReturnEmptyListWhenTilesWithImprovements() {
            Board board = mock(Board.class);
            when(board.getTiles()).thenReturn(new HashMap<>());
            Tile tile = new Tile();
            tile.setImprovement(mock(ImprovementType.class));
            when(board.getTiles()).thenReturn(Map.of(mock(PositionVector.class), tile));
            when(board.getTileAt(any())).thenReturn(tile);
            assertThat(tileLayer.getAvailableImprovementPositions(board)).isEmpty();
        }

        @Test
        @DisplayName(
                "should return positions when there are tiles with no improvements and no bamboo")
        void
                getAvailableImprovementPositions_shouldReturnPositionsWhenTilesWithNoImprovementsAndBamboo() {
            Board board = mock(Board.class);
            Tile tile = new Tile();
            when(board.getTiles()).thenReturn(Map.of(mock(PositionVector.class), tile));
            when(board.getTileAt(any())).thenReturn(tile);
            when(board.getBambooAt(any())).thenReturn(new LayerBambooStack(0));
            assertThat(tileLayer.getAvailableImprovementPositions(board)).isNotEmpty();
        }

        @Test
        @DisplayName(
                "should not return positions when there are tiles with no improvements but have"
                        + " bamboo")
        void
                getAvailableImprovementPositions_shouldNotReturnPositionsWhenTilesWithNoImprovementsButBamboo() {
            Board board = mock(Board.class);
            Tile tile = new Tile();
            when(board.getTiles()).thenReturn(Map.of(mock(PositionVector.class), tile));
            when(board.getTileAt(any())).thenReturn(tile);
            when(board.getBambooAt(any())).thenReturn(new LayerBambooStack(1));
            assertThat(tileLayer.getAvailableImprovementPositions(board)).isEmpty();
        }

        @Test
        @DisplayName("should not return the pond position")
        void getAvailableImprovementPositions_shouldNotReturnPondPosition() {
            Board board = mock(Board.class);
            Pond pond = new Pond();
            when(board.getTiles()).thenReturn(Map.of(mock(PositionVector.class), pond));
            when(board.getTileAt(any())).thenReturn(pond);
            when(board.getBambooAt(any())).thenReturn(new LayerBambooStack(0));
            assertThat(tileLayer.getAvailableImprovementPositions(board)).isEmpty();
        }
    }

    @Nested
    @DisplayName("Method applyImprovement()")
    class TestApplyImprovement {
        @Test
        @DisplayName("should apply the improvement to the tile")
        void applyImprovement_shouldApplyImprovementToTile() {
            Board board = mock(Board.class);
            Tile tile = new Tile();
            PositionVector position = mock(PositionVector.class);
            when(board.getTiles()).thenReturn(Map.of(position, tile));
            when(board.getTileAt(any())).thenReturn(tile);
            when(board.getBambooAt(any())).thenReturn(new LayerBambooStack(0));
            when(board.getAvailableImprovementPositions()).thenReturn(List.of(position));
            tileLayer.applyImprovement(mock(ImprovementType.class), position, board);
            assertThat(tile.getImprovement()).isNotNull();
        }
    }
}
