package com.takenoko;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
            Tile pond = new Tile();
            pond.setPond(true);
            assertThat(board.getTiles()).hasSize(1);
            assertThat(board.getTiles().get(0).getPond()).isTrue();
        }
    }

    @Nested
    @DisplayName("Method getAvailableTiles")
    class TestGetAvailableTiles {
        @Test
        @DisplayName("should return a list with one tile when the board is created")
        void getAvailableTiles_shouldReturnListWithOneTile() {
            assertThat(board.getAvailableTiles()).hasSize(1);
        }
    }

    @Nested
    @DisplayName("Method placeTile")
    class TestPlaceTile {
        /** Test that a tile can be placed on the board. */
        @Test
        void placeTile_WhenCalled_AddsTileToBoard() {
            Tile tile = board.getAvailableTiles().get(0);
            board.placeTile(tile);
            assertThat(board.getTiles()).contains(tile);
        }

        @Test
        @DisplayName("should remove the tile from the available tiles when placed")
        void placeTile_WhenCalled_RemovesTileFromAvailableTiles() {
            Tile tile = board.getAvailableTiles().get(0);
            board.placeTile(tile);
            assertThat(board.getAvailableTiles()).doesNotContain(tile);
        }

        @Test
        @DisplayName("should throw an exception when there is no more available tile")
        void placeTile_WhenNoAvailableTile_ThrowsException() {
            Tile tile = board.getAvailableTiles().get(0);
            board.placeTile(tile);
            assertThatThrownBy(() -> board.placeTile(tile))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The tile is not available.");
        }
    }
}
