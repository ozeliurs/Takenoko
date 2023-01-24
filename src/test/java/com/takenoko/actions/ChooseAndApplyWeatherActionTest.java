package com.takenoko.actions;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
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
            assertNotNull(chooseAndApplyWeatherAction.execute(null, null));
            verify(weather).apply(null, null);
        }
    }
}
