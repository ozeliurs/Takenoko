package com.takenoko.bot.unitary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.engine.History;
import com.takenoko.weather.Weather;
import com.takenoko.weather.WeatherFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SmartChooseAndApplyWeatherTest {

    @Nested
    @DisplayName("method fillAction")
    class FillAction {
        @Test
        void shouldApplySun() {
            Board board = mock(Board.class);
            Weather sun = WeatherFactory.SUNNY.createWeather();
            when(board.peekWeather()).thenReturn(sun);
            SmartChooseAndApplyWeather smartChooseAndApplyWeather =
                    new SmartChooseAndApplyWeather();
            smartChooseAndApplyWeather.fillAction(board, mock(BotState.class), mock(History.class));

            assertThat(smartChooseAndApplyWeather.keySet())
                    .hasSize(1)
                    .containsExactly(new ChooseAndApplyWeatherAction(sun));
        }
    }
}
