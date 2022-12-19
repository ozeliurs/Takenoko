package com.takenoko.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

import com.takenoko.actors.panda.MovePandaAction;
import com.takenoko.engine.Board;
import com.takenoko.layers.tile.PlaceTileAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TilePlacingAndPandaMovingBotTest {

    private TilePlacingAndPandaMovingBot bot;
    private Board board;

    @BeforeEach
    void setUp() {
        this.bot = new TilePlacingAndPandaMovingBot();
        this.board = spy(Board.class);
    }

    @Nested
    @DisplayName("Method chooseAction()")
    class TestChooseAction {
        @Test
        @DisplayName("should return an action")
        void shouldReturnAnAction() {
            assertThat(bot.chooseAction(board)).isNotNull();
        }

        @Test
        @DisplayName("should return an action of type PlaceTileAction or MovePandaAction")
        void shouldReturnAnActionOfTypePlaceTileOrMovePanda() {
            assertThat(bot.chooseAction(board))
                    .isInstanceOfAny(PlaceTileAction.class, MovePandaAction.class);
        }
    }
}