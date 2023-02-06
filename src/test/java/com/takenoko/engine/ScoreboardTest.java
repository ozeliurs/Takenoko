package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ScoreboardTest {
    Scoreboard scoreboard;
    BotManager botManager1;
    BotManager botManager2;

    @BeforeEach
    void setUp() {
        botManager1 = mock(BotManager.class);
        botManager2 = mock(BotManager.class);
        scoreboard = new Scoreboard();
    }

    @Nested
    @DisplayName("Method addBotManager")
    class TestAddBotManager {
        @Test
        @DisplayName("using constructor with list of BotManager")
        void usingConstructorWithList() {
            scoreboard.addBotManager(List.of(botManager1, botManager2));
            // Assert that botManager1 and botManager2 are in the scoreboard
            assertThat(scoreboard.getBotManagers()).contains(botManager1, botManager2);
        }

        @Test
        @DisplayName("using constructor with single BotManager")
        void usingConstructorWithSingleBotManager() {
            scoreboard.addBotManager(botManager1);
            // Assert that botManager1 is in the scoreboard
            assertThat(scoreboard.getBotManagers()).contains(botManager1);
        }
    }

    @Test
    @DisplayName("Method incrementNumberOfVictory")
    void incrementNumberOfVictory() {
        scoreboard.addBotManager(botManager1);
        scoreboard.incrementNumberOfVictory(botManager1);
        // Assert that botManager1 has 1 victory
        assertThat(scoreboard.getNumberOfVictory(botManager1)).isEqualTo(1);
    }

    @Test
    @DisplayName("Method getNumberOfVictory")
    void getNumberOfVictory() {
        scoreboard.addBotManager(botManager1);
        scoreboard.incrementNumberOfVictory(botManager1);
        // Assert that botManager1 has 1 victory
        assertThat(scoreboard.getNumberOfVictory(botManager1)).isEqualTo(1);
    }

    @Test
    @DisplayName("Method toString")
    void toString_ThenReturnsCorrectString() {
        String result = scoreboard.toString();
        assertThat(result).contains("============== Scoreboard ==============");
    }
}
