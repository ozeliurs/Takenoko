package com.takenoko.weather;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import com.takenoko.actions.Action;
import com.takenoko.actions.weather.ChooseAndApplyWeatherAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class QuestionMarkTest {

    @Nested
    @DisplayName("method: apply()")
    class Apply {
        @Test
        @DisplayName("should apply the weather on the board")
        void shouldApplyTheWeatherOnTheBoard() {
            // Given
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            QuestionMark questionMark = new QuestionMark();

            // When
            questionMark.apply(board, botManager);

            verify(board, times(1)).setWeather(questionMark);
        }

        @Test
        @DisplayName("should apply the weather on the bot manager")
        void shouldApplyTheWeatherOnTheBotManager() {
            // Given
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            QuestionMark questionMark = new QuestionMark();

            // When
            List<Class<? extends Action>> actions = questionMark.apply(board, botManager);

            assertThat(actions).containsExactlyInAnyOrder(ChooseAndApplyWeatherAction.class);
        }
    }
}
