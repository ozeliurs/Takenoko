package com.takenoko.player;

import com.takenoko.engine.Board;
import com.takenoko.layers.tile.PlaceTileAction;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

class TilePlacingBotTest {
    private TilePlacingBot tilePlacingBot;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        tilePlacingBot = new TilePlacingBot();
    }

    @AfterEach
    void tearDown() {
        tilePlacingBot = null;
        board = null;
    }

    @Nested
    @DisplayName("Method chooseAction()")
    class TestChooseAction {
        @Test
        @DisplayName("should return an action")
        void shouldReturnAnAction() {
            assertThat(tilePlacingBot.chooseAction(board)).isNotNull();
        }

        @Test
        @DisplayName("should return an action of type PlaceTileAction")
        void shouldReturnAnActionOfTypePlaceTile() {
            assertThat(tilePlacingBot.chooseAction(board)).isInstanceOf(PlaceTileAction.class);
        }
    }
}
