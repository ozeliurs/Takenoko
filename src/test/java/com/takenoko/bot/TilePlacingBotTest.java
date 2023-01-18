package com.takenoko.bot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

import com.takenoko.actions.ChooseIfApplyWeatherAction;
import com.takenoko.actions.MoveGardenerAction;
import com.takenoko.actions.MovePandaAction;
import com.takenoko.actions.PlaceTileAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
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
