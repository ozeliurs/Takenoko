package com.takenoko.actions.weather;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.actions.ActionResult;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.weather.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ChooseIfApplyWeatherActionTest {

    @Nested
    @DisplayName("execute")
    class Execute {

        Board board;
        Weather weather;

        @BeforeEach
        void setUp() {
            board = mock(Board.class);
            weather = mock(Weather.class);
            when(board.peekWeather()).thenReturn(weather);
        }

        @Test
        @DisplayName("should return an ActionResult")
        void shouldReturnAnActionResult() {
            // Given
            ChooseIfApplyWeatherAction chooseIfApplyWeatherAction =
                    new ChooseIfApplyWeatherAction(true);

            // When
            ActionResult result = chooseIfApplyWeatherAction.execute(board, mock(BotManager.class));

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Should apply the weather if applyWeather is true")
        void shouldApplyTheWeatherIfApplyWeatherIsTrue() {
            // Given
            ChooseIfApplyWeatherAction chooseIfApplyWeatherAction =
                    new ChooseIfApplyWeatherAction(true);

            // When

            BotManager botManager = mock(BotManager.class);
            chooseIfApplyWeatherAction.execute(board, botManager);
            verify(weather, times(1)).apply(board, botManager);
        }
    }
}
