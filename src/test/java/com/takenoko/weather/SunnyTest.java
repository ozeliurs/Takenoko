package com.takenoko.weather;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SunnyTest {
    @Nested
    @DisplayName("method: apply()")
    class Apply {
        @Test
        @DisplayName("should apply the weather on the board")
        void shouldApplyTheWeatherOnTheBoard() {
            // Given
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            Sunny sunny = new Sunny();

            // When
            sunny.apply(board, botManager);

            verify(board, times(1)).setWeather(sunny);
        }

        @Test
        @DisplayName("should apply the weather on the bot manager")
        void shouldApplyTheWeatherOnTheBotManager() {
            // Given
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            Sunny sunny = new Sunny();

            // When
            sunny.apply(board, botManager);

            verify(botManager, times(1)).addAction();
        }
    }

    @Nested
    @DisplayName("method: revert()")
    class Revert {
        @Test
        @DisplayName("should revert the weather on the board")
        void shouldRevertTheWeatherOnTheBoard() {
            // Given
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            Sunny sunny = new Sunny();

            // When
            sunny.revert(board, botManager);

            verify(board, times(1)).resetWeather();
        }
    }
}
