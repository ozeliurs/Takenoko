package com.takenoko.weather;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.takenoko.actions.DrawImprovementAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CloudyTest {

    @Nested
    @DisplayName("Method apply()")
    class TestApply {
        @Test
        @DisplayName("should return DrawImprovementAction.class")
        void shouldReturnDrawImprovementActionClass() {
            Board board = mock(Board.class);
            Weather weather = new Cloudy();
            assertThat(weather.apply(board, mock(BotManager.class)))
                    .contains(DrawImprovementAction.class);
            verify(board).setWeather(weather);
        }
    }

    @Nested
    @DisplayName("Method revert()")
    class TestRevert {
        @Test
        @DisplayName("should call board.resetWeather()")
        void shouldCallBoardResetWeather() {
            Board board = mock(Board.class);
            Weather weather = new Cloudy();
            weather.revert(board, mock(BotManager.class));
            verify(board).resetWeather();
        }
    }
}
