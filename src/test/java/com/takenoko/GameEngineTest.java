package com.takenoko;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Test class for the GameEngine class. */
class GameEngineTest {
    private GameEngine gameEngine;

    /** Set up the game engine before each test. */
    @BeforeEach
    void setUp() {
        gameEngine = new GameEngine();
    }

    /** Tear down the game engine after each test. */
    @AfterEach
    void tearDown() {
        gameEngine = null;
    }

    /** Test that the game engine is correctly created with only one tile. */
    @Test
    void startGame_thenBoardHasOneTile() {
        gameEngine.startGame();
        assertThat(gameEngine.getBoard().getTiles()).hasSize(1);
    }
}
