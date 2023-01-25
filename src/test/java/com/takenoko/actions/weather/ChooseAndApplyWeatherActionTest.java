package com.takenoko.actions.weather;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.weather.Weather;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ChooseAndApplyWeatherActionTest {
    @Nested
    @DisplayName("Method apply()")
    class TestApply {
        @Test
        @DisplayName("should return an ActionResult")
        void shouldReturnAnActionResult() {
            Weather weather = mock(Weather.class);
            ChooseAndApplyWeatherAction chooseAndApplyWeatherAction =
                    new ChooseAndApplyWeatherAction(weather);
            assertNotNull(
                    chooseAndApplyWeatherAction.execute(mock(Board.class), mock(BotManager.class)));
            verify(weather).apply(null, null);
        }
    }
}
