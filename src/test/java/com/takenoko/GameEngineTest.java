package com.takenoko;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;

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

    @Nested
    @DisplayName("Test newGame method")
    class TestNewGame {
        /** Test that the game state is set to READY after a new game is created. */
        @Test
        @DisplayName("newGame should set the game state to READY")
        void newGame_shouldSetGameStateToReady() {
            gameEngine.newGame();
            assertThat(gameEngine.getGameState()).isEqualTo(GameState.READY);
        }
    }

    @Nested
    @DisplayName("Test startGame method")
    class TestStartGame {
        /** Test that the game state is set to PLAYING after the game is started. */
        @Test
        @DisplayName("startGame should set the game state to PLAYING")
        void startGame_shouldSetGameStateToPlaying() {
            gameEngine.startGame();
            assertThat(gameEngine.getGameState()).isEqualTo(GameState.PLAYING);
        }

        /** Test that the game is correctly started with the first tile in the board. */
        @Test
        @DisplayName("startGame should add the first tile to the board")
        void startGame_thenBoardHasOneTile() {
            gameEngine.startGame();
            assertThat(gameEngine.getBoard().getTiles()).hasSize(1);
        }
    }

    @Nested
    @DisplayName("Test endGame method")
    class TestEndGame {
        /** Test that the game state is set to FINISHED after the game is ended. */
        @Test
        @DisplayName("endGame should set the game state to FINISHED")
        void endGame_shouldSetGameStateToFinished() {
            gameEngine.endGame();
            assertThat(gameEngine.getGameState()).isEqualTo(GameState.FINISHED);
        }
    }
}
