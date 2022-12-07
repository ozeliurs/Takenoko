package com.takenoko;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
    class TestGetTiles {
        @Test
        void getTiles_shouldReturnEmptyList() {
            assertThat(board.getTiles()).isEmpty();
        }
    }

    @Nested
    class TestPlaceTile {
        @Test
        void placeTile_WhenGivenTile_AddsTileToBoard() {
            Tile tile = new Tile();
            board.placeTile(tile);
            assertThat(board.getTiles()).containsExactly(tile);
        }
    }
}
