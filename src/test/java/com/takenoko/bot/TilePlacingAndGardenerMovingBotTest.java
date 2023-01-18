package com.takenoko.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.actions.ChooseIfApplyWeatherAction;
import com.takenoko.actions.MoveGardenerAction;
import com.takenoko.actions.MovePandaAction;
import com.takenoko.actions.PlaceTileAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TilePlacingAndGardenerMovingBotTest {
    private Board board;
    private TilePlacingAndGardenerMovingBot bot;

    @BeforeEach
    void setUp() {
        board = mock(Board.class);
        bot = new TilePlacingAndGardenerMovingBot();
    }

    @Nested
    @DisplayName("Method chooseAction()")
    class TestChooseAction {
        @Test
        @DisplayName("should return an action")
        void shouldReturnAnAction() {
            when(board.getGardenerPossibleMoves())
                    .thenReturn(List.of(new PositionVector(1, 0, -1)));
            BotState botState = mock(BotState.class);
            when(botState.getAvailableActions())
                    .thenReturn(List.of(MoveGardenerAction.class, PlaceTileAction.class));
            assertThat(bot.chooseAction(board, botState)).isNotNull();
        }

        @Test
        @DisplayName(
                "should return an action of type MoveGardenerAction when the gardener is can move")
        void shouldReturnAnActionOfTypePlaceTileWhenTheGardenerIsCanMove() {
            when(board.getGardenerPossibleMoves())
                    .thenReturn(List.of(new PositionVector(1, 0, -1)));
            BotState botState = mock(BotState.class);
            when(botState.getAvailableActions()).thenReturn(List.of(MoveGardenerAction.class));
            assertThat(bot.chooseAction(board, botState)).isInstanceOfAny(MoveGardenerAction.class);
        }

        @Test
        @DisplayName("should return an action of type PlaceTileAction when the gardener can't move")
        void shouldReturnAnActionOfTypeMoveGardenerWhenTheGardenerCantMove() {
            when(board.getGardenerPossibleMoves()).thenReturn(List.of());
            when(board.getAvailableTiles()).thenReturn(List.of(mock(Tile.class)));
            when(board.getAvailableTilePositions()).thenReturn(List.of(mock(PositionVector.class)));
            BotState botState = mock(BotState.class);
            when(botState.getAvailableActions())
                    .thenReturn(List.of(PlaceTileAction.class, MoveGardenerAction.class));
            assertThat(bot.chooseAction(board, botState)).isInstanceOfAny(PlaceTileAction.class);
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
    }
}
