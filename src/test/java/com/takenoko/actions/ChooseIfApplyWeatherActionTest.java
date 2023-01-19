package com.takenoko.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
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
            ActionResult result = chooseIfApplyWeatherAction.execute(board, null);

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Should apply the weather if applyWeather is true")
        void shouldApplyTheWeatherIfApplyWeatherIsTrue() {
            // Given
            ChooseIfApplyWeatherAction chooseIfApplyWeatherAction =
                    new ChooseIfApplyWeatherAction(true);

            // When
            chooseIfApplyWeatherAction.execute(board, null);
            verify(weather, times(1)).apply(board, null);
        }
    }
}
