package com.takenoko;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BotTest {
    private Bot bot;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        bot = new Bot(board);
    }

    @AfterEach
    void tearDown() {
        bot = null;
        board = null;
    }

    @Test
    void testPlaceTile() {
        int availableTileNumber = board.getAvailableTileNumber();
        bot.placeTile();
        assertThat(board.getAvailableTileNumber()).isEqualTo(availableTileNumber - 1);
        assertThat(board.getTiles()).hasSize(1);
    }
}
