package com.takenoko;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.takenoko.vector.Vector;
import java.util.Arrays;
import org.junit.jupiter.api.*;

/** Test class for the Board class. */
class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @AfterEach
    void tearDown() {
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
            assertThat(board.getTiles()).hasSize(1);
            assertThat(board.getTiles()).containsKey(new Vector(0, 0, 0));
            assertThat(board.getTiles().get(new Vector(0, 0, 0)).getType())
                    .isEqualTo(TileType.POND);
        }
    }

    @Nested
    @DisplayName("Method getAvailableTiles")
    class TestGetAvailableTiles {
        @Test
        @DisplayName("should return a list with at least one tile when the board is created")
        void getAvailableTiles_shouldReturnListWithAtLeastOneTile() {
            assertThat(board.getAvailableTiles()).hasSizeGreaterThanOrEqualTo(1);
        }
    }

    @Nested
    @DisplayName("Method placeTile")
    class TestPlaceTile {
        /** Test that a tile can be placed on the board. */
        @Test
        void placeTile_WhenCalled_AddsTileToBoard() {
            Tile tile = board.getAvailableTiles().get(0);
            board.placeTile(tile, board.getAvailableTilePositions().get(0));
            assertThat(board.getTiles()).containsValue(tile);
        }

        @Test
        @DisplayName("should remove the tile from the available tiles when placed")
        void placeTile_WhenCalled_RemovesTileFromAvailableTiles() {
            Tile tile = board.getAvailableTiles().get(0);
            board.placeTile(tile, board.getAvailableTilePositions().get(0));
            assertThat(board.getAvailableTiles()).doesNotContain(tile);
        }

        @Test
        @DisplayName("should remove the vector from the available tiles positions when placed")
        void placeTile_WhenCalled_RemovesVectorFromAvailablePositions() {
            Vector vector = board.getAvailableTilePositions().get(0);
            board.placeTile(board.getAvailableTiles().get(0), vector);
            assertThat(board.getAvailableTilePositions()).doesNotContain(vector);
        }

        @Test
        @DisplayName("should throw an exception when there is no more available tile")
        void placeTile_WhenNoAvailableTile_ThrowsException() {
            Tile t = new Tile(TileType.OTHER);
            Vector p = board.getAvailableTilePositions().get(0);
            assertThatThrownBy(() -> board.placeTile(t, p))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The tile is not available");
        }

        @Test
        @DisplayName("should throw an exception when there is already a tile in the position")
        void placeTile_WhenTileAlreadyAtPosition_ThrowsException() {
            Tile t = new Tile(TileType.OTHER);
            Vector p = new Vector(0, 0, 0);
            assertThatThrownBy(() -> board.placeTile(t, p))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Tile already present at this position");
        }

        @Test
        @DisplayName("should throw an exception when the position is not available (not adjacent)")
        void placeTile_WhenPositionNotAvailable_ThrowsException() {
            Tile t = new Tile(TileType.OTHER);
            Vector p = new Vector(100, 0, -100);
            assertThatThrownBy(() -> board.placeTile(t, p))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Tile position not available");
        }
    }

    @Nested
    @DisplayName("Method updateAvailableTilePositions")
    class TestUpdateAvailableTilePositions {
        Vector pm101;
        Vector pm110;
        Vector pm211;
        Vector pm220;
        Vector pm202;

        @BeforeEach
        void setUp() {
            board = new Board();
            this.pm101 = new Vector(-1, 0, 1);
            this.pm110 = new Vector(-1, 1, 0);
            this.pm211 = new Vector(-2, 1, 1);
            this.pm202 = new Vector(-2, 0, 2);
            this.pm220 = new Vector(-2, 2, 0);
        }

        @Test
        @DisplayName("tiles next to the pond should be available")
        void TilesNextToPondShouldBeAvailable() {
            // board.getTiles().get(new Vector(0, 0, 0)).
            assertThat(board.getAvailableTilePositions())
                    .containsAll(
                            Arrays.asList(
                                    new Vector(1, -1, 0),
                                    new Vector(1, 0, -1),
                                    new Vector(0, 1, -1),
                                    new Vector(-1, 1, 0),
                                    new Vector(-1, 0, 1),
                                    new Vector(0, -1, 1)));
        }

        @Test
        @DisplayName("two adjacent tiles should create one available position next to them")
        void updateAvailableTilePositions_WhenCalled_AddsPositionToAvailableTilePositions() {
            Tile t = board.getAvailableTiles().get(0);
            Vector p = new Vector(1, -1, 0);
            board.placeTile(t, p);
            assertThat(board.getAvailableTilePositions()).doesNotContain(p);

            t = board.getAvailableTiles().get(0);
            p = new Vector(1, 0, -1);
            board.placeTile(t, p);
            assertThat(board.getAvailableTilePositions()).doesNotContain(p);

            p = new Vector(2, -1, -1);
            assertThat(board.getAvailableTilePositions()).contains(p);
        }

        @Test
        @DisplayName("two adjacent tiles should create two available position next to them")
        void updateAvailableTilePositions_WhenCalled_AddsTwoPositionToAvailableTilePositions() {
            for (Vector v : Arrays.asList(pm101, pm110, pm211)) {
                board.placeTile(board.getAvailableTiles().get(0), v);
                assertThat(board.getAvailableTilePositions()).doesNotContain(v);
            }

            assertThat(board.getAvailableTilePositions()).contains(pm202);
            assertThat(board.getAvailableTilePositions()).contains(pm220);

            assertThat(board.getAvailableTilePositions()).hasSize(6);
            assertThat(board.getAvailableTilePositions())
                    .containsAll(
                            Arrays.asList(
                                    new Vector(1, -1, 0),
                                    new Vector(1, 0, -1),
                                    new Vector(0, 1, -1),
                                    new Vector(-2, 2, 0),
                                    new Vector(-2, 0, 2),
                                    new Vector(0, -1, 1)));
        }
    }
}
