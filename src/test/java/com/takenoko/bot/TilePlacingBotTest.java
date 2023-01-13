package com.takenoko.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.actions.MoveGardenerAction;
import com.takenoko.actions.MovePandaAction;
import com.takenoko.actions.PlaceTileAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import java.util.List;
import org.junit.jupiter.api.*;

class TilePlacingBotTest {
    private TilePlacingBot tilePlacingBot;
    private Board board;
    private BotState botState;

    @BeforeEach
    void setUp() {
        board = new Board();
        botState = mock(BotState.class);
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
            when(botState.getAvailableActions()).thenReturn(List.of(PlaceTileAction.class));
            assertThat(tilePlacingBot.chooseAction(board, botState)).isNotNull();
        }

        @Test
        @DisplayName("should return an action of type PlaceTileAction")
        void shouldReturnAnActionOfTypePlaceTile() {
            when(botState.getAvailableActions())
                    .thenReturn(
                            List.of(
                                    PlaceTileAction.class,
                                    MoveGardenerAction.class,
                                    MovePandaAction.class));
            assertThat(tilePlacingBot.chooseAction(board, mock(BotState.class)))
                    .isInstanceOf(PlaceTileAction.class);
        }
    }
}
