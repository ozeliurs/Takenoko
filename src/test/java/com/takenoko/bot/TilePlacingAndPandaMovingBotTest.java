package com.takenoko.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.tile.PlaceTileAction;
import com.takenoko.actions.weather.ChooseIfApplyWeatherAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;
import java.util.List;
import org.apache.commons.lang3.NotImplementedException;
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
        @DisplayName("should return an action of type PlaceTileAction or MovePandaAction")
        void shouldReturnAnActionOfTypePlaceTileOrMovePanda() {
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
            assertThat(bot.chooseAction(board, botState))
                    .isInstanceOfAny(PlaceTileAction.class, MovePandaAction.class);
        }

        @Test
        @DisplayName("Should return applyWeatherAction if that action is available")
        void shouldReturnApplyWeatherActionIfThatActionIsAvailable() {
            BotState botState = mock(BotState.class);
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
        @DisplayName("Should throw NotImplementedException when no actions are available")
        void shouldThrowNotImplementedException() {
            BotState botState = mock(BotState.class);
            when(botState.getAvailableActions()).thenReturn(List.of());
            assertThatThrownBy(() -> bot.chooseAction(board, botState))
                    .isInstanceOf(NotImplementedException.class);
        }
    }
}
