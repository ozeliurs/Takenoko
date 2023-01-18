package com.takenoko.weather;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class WeatherDiceTest {

    @Nested
    @DisplayName("rollWeather")
    class RollWeather {
        @Test
        @DisplayName("should return a Weather when random is between 0 and 5")
        void shouldReturnAWeatherWhenRandomIsBetween0And5() {
            Random random = mock(Random.class);
            when(random.nextInt(any(Integer.class))).thenReturn(0);
            WeatherDice weatherDice = new WeatherDice(random);
            Weather weather = weatherDice.rollWeather();
            assertThat(weather).isInstanceOf(WeatherFactory.values()[0].createWeather().getClass());
        }
    }
}
