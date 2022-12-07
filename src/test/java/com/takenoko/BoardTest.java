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
        /** Test that the board is empty when created. */
        @Test
        @DisplayName("should return an empty list when the board is created")
        void getTiles_shouldReturnEmptyList() {
            assertThat(board.getTiles()).isEmpty();
        }

        @Test
        @DisplayName("should only have one available tile when the board is created")
        void getTiles_shouldHaveOneAvailableTile() {
            assertThat(board.getAvailableTileNumber()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("Method placeTile")
    class TestPlaceTile {
        /** Test that a tile can be placed on the board. */
        @Test
        void placeTile_WhenCalled_AddsTileToBoard() {
            Tile tile = new Tile();
            board.placeTile(tile);
            assertThat(board.getTiles()).containsExactly(tile);
        }

        @Test
        @DisplayName("should decrease the number of available tiles by one")
        void placeTile_WhenCalled_DecreasesAvailableTileNumber() {
            int availableTileNumber = board.getAvailableTileNumber();
            board.placeTile(new Tile());
            assertThat(board.getAvailableTileNumber()).isEqualTo(availableTileNumber - 1);
        }

        @Test
        @DisplayName("should throw an exception when there is no more available tile")
        void placeTile_WhenNoAvailableTile_ThrowsException() {
            assert board.getAvailableTileNumber() == 1;
            board.placeTile(new Tile());

            assertThatThrownBy(() -> board.placeTile(new Tile()))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("There is no more available tile.");
        }
    }
}
