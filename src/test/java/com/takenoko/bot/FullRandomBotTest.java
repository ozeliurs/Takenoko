package com.takenoko.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.actions.ChooseIfApplyWeatherAction;
import com.takenoko.actions.MoveGardenerAction;
import com.takenoko.actions.MovePandaAction;
import com.takenoko.actions.PlaceTileAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.vector.PositionVector;
import java.util.List;
import org.junit.jupiter.api.*;

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
        @DisplayName("Should return applyWeatherAction if that action is available")
        void shouldReturnApplyWeatherActionIfThatActionIsAvailable() {
            when(botState.getAvailableActions())
                    .thenReturn(
                            List.of(
                                    ChooseIfApplyWeatherAction.class,
                                    PlaceTileAction.class,
                                    MovePandaAction.class,
                                    MoveGardenerAction.class));
            assertThat(bot.chooseAction(board, botState))
                    .isInstanceOf(ChooseIfApplyWeatherAction.class);
        }

        @Test
        @DisplayName("should return an action of type PlaceTileAction")
        void shouldReturnAnActionOfTypePlaceTile() {
            when(botState.getAvailableActions()).thenReturn(List.of(PlaceTileAction.class));
            assertThat(bot.chooseAction(board, botState)).isInstanceOfAny(PlaceTileAction.class);
        }

        @Test
        @DisplayName("should return an action of type MovePandaAction")
        void shouldReturnAnActionOfTypeMovePanda() {
            when(botState.getAvailableActions()).thenReturn(List.of(MovePandaAction.class));
            when(board.getPandaPossibleMoves()).thenReturn(List.of(mock(PositionVector.class)));
            assertThat(bot.chooseAction(board, botState)).isInstanceOfAny(MovePandaAction.class);
        }

        @Test
        @DisplayName("should return an action of type MoveGardenerAction")
        void shouldReturnAnActionOfTypeMoveGardener() {
            when(botState.getAvailableActions()).thenReturn(List.of(MoveGardenerAction.class));
            when(board.getGardenerPossibleMoves()).thenReturn(List.of(mock(PositionVector.class)));
            assertThat(bot.chooseAction(board, botState)).isInstanceOfAny(MoveGardenerAction.class);
        }
    }
}
