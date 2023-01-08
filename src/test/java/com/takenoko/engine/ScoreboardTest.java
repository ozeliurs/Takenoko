package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.*;

class ScoreboardTest {
    Scoreboard scoreboard;
    BotManager botManager1;
    BotManager botManager2;

    @BeforeEach
    void setUp() {
        botManager1 = mock(BotManager.class);
        botManager2 = mock(BotManager.class);
        scoreboard = new Scoreboard();
        when(botManager1.getBotId()).thenReturn(UUID.nameUUIDFromBytes("bot1".getBytes()));
        when(botManager2.getBotId()).thenReturn(UUID.nameUUIDFromBytes("bot2".getBytes()));
    }

    @Test
    @DisplayName("Method incrementNumberOfGamesPlayed & getNumberOfGamesPlayed")
    void incrementNumberOfGamesPlayed_ThenReturnsCorrectNumberOfGamesPlayed() {
        scoreboard.incrementNumberOfGamesPlayed();
        assertThat(scoreboard.getNumberOfGamesPlayed()).isEqualTo(1);
    }

    @Test
    @DisplayName("Method toString")
    void TestToString() {
        when(botManager1.getName()).thenReturn("Bot1");
        when(botManager2.getName()).thenReturn("Bot2");
        when(botManager1.getScore()).thenReturn(1);
        when(botManager2.getScore()).thenReturn(2);

        scoreboard.addBotManager(List.of(botManager1, botManager2));

        assertThat(scoreboard).hasToString("Scoreboard:\nBot2 : 2 - Bot1 : 1 - ");
    }

    @Test
    @DisplayName("Method addScore & getScore")
    void addScoreAndGetScore() {
        scoreboard.addBotManager(List.of(botManager1));
        assertThat(scoreboard.getScore(botManager1.getBotId())).isZero();

        scoreboard.addScore(botManager1.getBotId(), 1);
        assertThat(scoreboard.getScore(botManager1.getBotId())).isEqualTo(1);
    }
}
