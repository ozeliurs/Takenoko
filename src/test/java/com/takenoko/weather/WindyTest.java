package com.takenoko.weather;

import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WindyTest {
    Board board;
    BotManager botManager;
    Windy windy;

    @BeforeEach
    void setUp() {
        board = mock(Board.class);
        botManager = mock(BotManager.class);
        windy = new Windy();
    }

    @Test
    @DisplayName("method: apply()")
    void Apply() {
        windy.apply(board, botManager);

        verify(board, times(1)).setWeather(windy);
    }

    @Test
    @DisplayName("method: revert()")
    void Revert() {
        windy.revert(board, botManager);

        verify(board, times(1)).resetWeather();
    }
}
