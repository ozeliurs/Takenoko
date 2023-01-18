package com.takenoko.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.actions.MoveGardenerAction;
import com.takenoko.actions.MovePandaAction;
import com.takenoko.actions.PlaceTileAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FullRandomBotTest {
    private Bot bot;
    private Board board;
    private BotState botState;

    @BeforeEach
    void setUp() {
        this.bot = new FullRandomBot();
        this.board = spy(Board.class);
        this.botState = mock(BotState.class);
    }

    @Nested
    @DisplayName("Method chooseAction()")
    class TestChooseAction {
        @Test
        @DisplayName("should return an action")
        void shouldReturnAnAction() {
            when(botState.getAvailableActions()).thenReturn(List.of(PlaceTileAction.class));
            assertThat(bot.chooseAction(board, botState)).isNotNull();
        }

        @Test
        @DisplayName("should return an action of type PlaceTileAction or MovePandaAction")
        void shouldReturnAnActionOfTypePlaceTileOrMovePanda() {
            when(botState.getAvailableActions())
                    .thenReturn(
                            List.of(
                                    PlaceTileAction.class,
                                    MovePandaAction.class,
                                    MoveGardenerAction.class));
            assertThat(bot.chooseAction(board, botState))
                    .isInstanceOfAny(PlaceTileAction.class, MovePandaAction.class);
        }
    }
}
