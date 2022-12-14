package com.takenoko.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.takenoko.Board;
import com.takenoko.tile.Tile;
import com.takenoko.vector.PositionVector;
import java.util.ArrayList;
import java.util.List;
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
            Pair<PositionVector, Tile> tileToPlace =
                    bot.chooseTileToPlace(
                            board.getAvailableTiles(), board.getAvailableTilePositions());
            assertThat(tileToPlace.getRight()).isNotNull();
        }

        @Test
        @DisplayName("When there is no valid tile, throw exception")
        void chooseTileToPlace_WhenThereIsNoValidTile_ThenThrowException() {
            ArrayList<Tile> emptyList = new ArrayList<>();
            List<PositionVector> p = board.getAvailableTilePositions();
            assertThatThrownBy(() -> bot.chooseTileToPlace(emptyList, p))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("No possible tiles");
        }
    }
}
