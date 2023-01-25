package com.takenoko.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.actions.actors.MovePandaAction;
import com.takenoko.actions.tile.PlaceTileAction;
import com.takenoko.actions.weather.ChooseIfApplyWeatherAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;
import java.util.ArrayList;
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
            board = mock(Board.class);
            when(botState.getAvailableActions()).thenReturn(List.of(PlaceTileAction.class));
            when(board.peekTileDeck()).thenReturn(List.of(mock(Tile.class)));
            when(board.getAvailableTilePositions()).thenReturn(List.of(mock(PositionVector.class)));

            assertThat(tilePlacingBot.chooseAction(board, botState)).isNotNull();
        }

        @Test
        @DisplayName("should return an action of type PlaceTileAction")
        void shouldReturnAnActionOfTypePlaceTile() {
            board = mock(Board.class);
            when(botState.getAvailableActions())
                    .thenReturn(List.of(PlaceTileAction.class, MoveGardenerAction.class));
            when(board.peekTileDeck()).thenReturn(List.of(mock(Tile.class)));
            when(board.getAvailableTilePositions()).thenReturn(List.of(mock(PositionVector.class)));
            assertThat(tilePlacingBot.chooseAction(board, botState))
                    .isInstanceOf(PlaceTileAction.class);
        }

        @Test
        @DisplayName("should throw an exception when no available actions")
        void shouldThrowAnExceptionWhenNoAvailableActions() {
            when(botState.getAvailableActions()).thenReturn(new ArrayList<>());
            assertThatThrownBy(() -> tilePlacingBot.chooseAction(board, botState))
                    .isInstanceOf(IllegalStateException.class);
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
            assertThat(tilePlacingBot.chooseAction(board, botState))
                    .isInstanceOf(ChooseIfApplyWeatherAction.class);
        }
    }
}
