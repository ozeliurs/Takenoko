package com.takenoko.player;

import com.takenoko.engine.Board;
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
        //        @Test
        //        @Disabled
        //        @DisplayName("When a valid tile exists, returns the tile")
        //        void chooseTileToPlace_WhenValidTileExists_ThenReturnsTheTile() {
        //            Pair<PositionVector, Tile> tileToPlace =
        //                    bot.chooseTileToPlace(
        //                            board.getTileLayer().getAvailableTiles(),
        //                            board.getTileLayer().getAvailableTilePositions());
        //            assertThat(tileToPlace.getRight()).isNotNull();
        //        }
        //
        //        @Test
        //        @Disabled
        //        @DisplayName("When there is no valid tile, throw exception")
        //        void chooseTileToPlace_WhenThereIsNoValidTile_ThenThrowException() {
        //            ArrayList<Tile> emptyList = new ArrayList<>();
        //            List<PositionVector> p = board.getTileLayer().getAvailableTilePositions();
        //            assertThatThrownBy(() -> bot.chooseTileToPlace(emptyList, p))
        //                    .isInstanceOf(IllegalArgumentException.class)
        //                    .hasMessage("No possible tiles");
        //        }
    }
}
