package com.takenoko.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.actions.*;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.layers.tile.Tile;
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
            board = mock(Board.class);
            BotState botState = mock(BotState.class);
            when(botState.getAvailableActions())
                    .thenReturn(
                            List.of(
                                    PlaceTileAction.class,
                                    MoveGardenerAction.class,
                                    MovePandaAction.class));
            when(board.peekTileDeck()).thenReturn(List.of(mock(Tile.class)));
            when(board.getAvailableTilePositions()).thenReturn(List.of(mock(PositionVector.class)));
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
            board = mock(Board.class);
            when(board.peekTileDeck()).thenReturn(List.of(mock(Tile.class)));
            when(board.getAvailableTilePositions()).thenReturn(List.of(mock(PositionVector.class)));
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

        @Test
        @DisplayName("should return an action of type DrawTileAction")
        void shouldReturnAnActionOfTypeDrawTileAction() {
            when(botState.getAvailableActions()).thenReturn(List.of(DrawTileAction.class));
            assertThat(bot.chooseAction(board, botState)).isInstanceOfAny(DrawTileAction.class);
        }

        @Test
        @DisplayName("should return an action of type GetAndStoreImprovementAction")
        void shouldReturnAnActionOfTypeGetAndStoreImprovementAction() {
            when(botState.getAvailableActions())
                    .thenReturn(List.of(GetAndStoreImprovementAction.class));
            assertThat(bot.chooseAction(board, botState))
                    .isInstanceOfAny(GetAndStoreImprovementAction.class);
        }
    }
}
