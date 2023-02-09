package com.takenoko.bot.unitary;

import static org.assertj.core.api.Assertions.assertThat;

import com.takenoko.actions.weather.ChooseIfApplyWeatherAction;
import org.junit.jupiter.api.Test;

class SmartApplyWeatherTest {
    @Test
    void shouldFillAndApplyWeather() {
        SmartApplyWeather smartApplyWeather = new SmartApplyWeather();
        smartApplyWeather.fillAction(null, null, null);
        assertThat(smartApplyWeather.keySet())
                .hasSize(1)
                .containsExactly(new ChooseIfApplyWeatherAction(true));
    }
}
