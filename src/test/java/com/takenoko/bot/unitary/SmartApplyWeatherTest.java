package com.takenoko.bot.unitary;

import static org.assertj.core.api.Assertions.assertThat;

import com.takenoko.actions.weather.ChooseIfApplyWeatherAction;
import com.takenoko.bot.PriorityBot;
import org.junit.jupiter.api.Test;

class SmartApplyWeatherTest {
    @Test
    void shouldFillAndApplyWeather() {
        PriorityBot smartApplyWeather = (new SmartApplyWeather()).compute(null, null, null);
        assertThat(smartApplyWeather.keySet())
                .hasSize(1)
                .containsExactly(new ChooseIfApplyWeatherAction(true));
    }
}
