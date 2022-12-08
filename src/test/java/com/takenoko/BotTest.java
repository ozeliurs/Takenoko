package com.takenoko;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BotTest {
    private Bot bot;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        bot = new Bot();
    }

    @AfterEach
    void tearDown() {
        bot = null;
        board = null;
    }

    @Test
    void testPlaceTile() {
        Tile tile = bot.chooseTileToPlace(board.getAvailableTiles());
        assertThat(tile).isNotNull();
    }

    @Test
    void testGetObjective() {
        PlaceTileObjective testObjective = new PlaceTileObjective(1);
        assertThat(testObjective == bot.getObjective());
    }

    @Test
    void testSetObjective() {
        PlaceTileObjective testObjective = new PlaceTileObjective(2);
        assertThat(testObjective != bot.getObjective());
        bot.setObjective(2);
        assertThat(testObjective == bot.getObjective());
    }
}
