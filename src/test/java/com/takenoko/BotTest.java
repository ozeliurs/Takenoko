package com.takenoko;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.takenoko.vector.Vector;
import java.util.ArrayList;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.*;

class BotTest {
    private Bot bot;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        bot = new Bot();
    }

    @AfterEach
    void tearDown() {
        bot = null;
        board = null;
    }

    @Nested
    @DisplayName("Method placeTile")
    class TestPlaceTile {
        @Test
        @DisplayName("When a valid tile exists, returns the tile")
        void chooseTileToPlace_WhenValidTileExists_ThenReturnsTheTile() {
            Pair<Vector, Tile> tileToPlace =
                    bot.chooseTileToPlace(
                            board.getAvailableTiles(), board.getAvailableTilePositions());
            assertThat(tileToPlace.getRight()).isNotNull();
        }

        @Test
        @DisplayName("When there is no valid tile, throw exception")
        void chooseTileToPlace_WhenThereIsNoValidTile_ThenThrowException() {
            ArrayList<Tile> emptyList = new ArrayList<>();
            assertThatThrownBy(
                            () ->
                                    bot.chooseTileToPlace(
                                            emptyList, board.getAvailableTilePositions()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("No possible tiles");
        }
    }
}
