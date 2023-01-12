package com.takenoko.weather;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;
import org.junit.jupiter.api.*;

class WeatherDiceTest {

    @Nested
    @DisplayName("rollWeather")
    class RollWeather {
        @Test
        @DisplayName("should return a Weather when random is between 0 and 5")
        void shouldReturnAWeatherWhenRandomIsBetween0And5() {
            // Given
            Random random = mock(Random.class);
            when(random.nextInt(any(Integer.class))).thenReturn(0, 1, 2, 3, 4, 5);
            WeatherDice weatherDice = new WeatherDice(random);

            // When
            for (int i = 0; i < 6; i++) {
                Weather weather = weatherDice.rollWeather();

                // Then
                assertThat(weather).isNotNull();
            }
        }

        @Test
        @DisplayName("should throw an IllegalStateException when random is out of bounds")
        void shouldThrowAnIllegalStateExceptionWhenRandomIsOutOfBounds() {
            // Given
            Random random = mock(Random.class);
            when(random.nextInt(any(Integer.class))).thenReturn(6);
            WeatherDice weatherDice = new WeatherDice(random);

            // When
            Throwable throwable = catchThrowable(weatherDice::rollWeather);

            // Then
            assertThat(throwable).isInstanceOf(IllegalStateException.class);
        }
    }
}
