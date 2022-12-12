package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
    @DisplayName("Method newGame")
    class TestNewGame {
        /** Test that the game state is set to READY after a new game is created. */
        @Test
        @DisplayName("newGame should set the game state to READY")
        void newGame_shouldSetGameStateToReady() {
            gameEngine.newGame();
            assertThat(gameEngine.getGameState()).isEqualTo(GameState.READY);
        }

        @Test
        @DisplayName("newGame should throw exception when the game state is not READY")
        void newGame_shouldThrowExceptionWhenGameStateIsNotReady() {
            gameEngine.newGame();
            assertThatThrownBy(() -> gameEngine.newGame())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("The game is already started. You must end the game first.");
        }
    }

    @Nested
    @DisplayName("Method startGame")
    class TestStartGame {
        @Nested
        @DisplayName("when started at the wrong time")
        class TestStartGame_WhenStartedIncorrectly {
            @Test
            @DisplayName("should throw an exception when the game state is INITIALIZED")
            void startGame_shouldThrowExceptionWhenGameStateIsInitialized() {
                assertThatThrownBy(() -> gameEngine.startGame())
                        .isInstanceOf(IllegalStateException.class)
                        .hasMessage("The game is not ready yet. You must first create a new game.");
            }

            @Test
            @DisplayName("should throw an exception when the game state is PLAYING")
            void startGame_shouldThrowExceptionWhenGameStateIsPlaying() {
                gameEngine.setGameState(GameState.PLAYING);
                assertThatThrownBy(() -> gameEngine.startGame())
                        .isInstanceOf(IllegalStateException.class)
                        .hasMessage(
                                "The game is already started. You must create a new game to call"
                                        + " this method.");
            }

            @Test
            @DisplayName("should throw an exception when the game state is FINISHED")
            void startGame_shouldThrowExceptionWhenGameStateIsFinished() {
                gameEngine.setGameState(GameState.FINISHED);
                assertThatThrownBy(() -> gameEngine.startGame())
                        .isInstanceOf(IllegalStateException.class)
                        .hasMessage(
                                "The game is already started. You must create a new game to call"
                                        + " this method.");
            }
        }

        @Nested
        @DisplayName("when started at the right time")
        class TestStartGame_WhenStartedCorrectly {
            @BeforeEach
            void setUp() {
                gameEngine.newGame();
                gameEngine.startGame();
            }

            /** Test that the game state is set to PLAYING after the game is started. */
            @Test
            @DisplayName("should set the game state to PLAYING")
            void startGame_shouldSetGameStateToPlaying() {
                assertThat(gameEngine.getGameState()).isEqualTo(GameState.PLAYING);
            }
        }
    }

    @Nested
    @DisplayName("Method playGame")
    class TestPlayGame {
        @Test
        @DisplayName("When called, a tile is placed on the board")
        void playGame_whenCalled_aTileIsPlacedOnTheBoard() {
            gameEngine.newGame();
            gameEngine.startGame();
            gameEngine.playGame();
            assertThat(gameEngine.getBoard().getTiles()).hasSize(3);
        }
    }

    @Nested
    @DisplayName("Method endGame")
    class TestEndGame {
        @Nested
        @DisplayName("when ended at the wrong time")
        class TestEndGame_WhenEndedIncorrectly {
            private static Stream<Arguments> streamOfStatesThatAreNotPLAYING() {
                return Stream.of(
                        Arguments.of(GameState.INITIALIZED),
                        Arguments.of(GameState.READY),
                        Arguments.of(GameState.FINISHED));
            }

            @ParameterizedTest(name = "i.e. : {0}")
            @MethodSource("streamOfStatesThatAreNotPLAYING")
            @DisplayName("should throw an exception when the game state is not PLAYING")
            void endGame_shouldThrowException_WhenGameStateIsNotPLAYING(
                    GameState currentGameState) {
                gameEngine.setGameState(currentGameState);
                assertThatThrownBy(() -> gameEngine.endGame())
                        .isInstanceOf(IllegalStateException.class)
                        .hasMessage("The game is not started yet. You must first start the game.");
            }
        }

        @Nested
        @DisplayName("when ended at the right time")
        class TestStartGame_WhenStartedCorrectly {
            /** Test that the game state is set to FINISHED after the game is ended. */
            @Test
            @DisplayName("endGame should set the game state to FINISHED")
            void endGame_shouldSetGameStateToFinished() {
                gameEngine.newGame();
                gameEngine.startGame();
                gameEngine.endGame();
                assertThat(gameEngine.getGameState()).isEqualTo(GameState.FINISHED);
            }
        }
    }
}
