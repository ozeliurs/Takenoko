package com.takenoko.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.layers.tile.PlaceTileAction;
import org.junit.jupiter.api.*;

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
            assertThat(tilePlacingBot.chooseAction(board, mock(BotState.class))).isNotNull();
        }

        @Test
        @DisplayName("should return an action of type PlaceTileAction")
        void shouldReturnAnActionOfTypePlaceTile() {
            assertThat(tilePlacingBot.chooseAction(board, mock(BotState.class)))
                    .isInstanceOf(PlaceTileAction.class);
        }
    }
}
