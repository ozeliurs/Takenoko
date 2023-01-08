package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.takenoko.bot.TilePlacingBot;
import com.takenoko.ui.ConsoleUserInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
        gameEngine = spy(GameEngine.class);
    }

    /** Tear down the game engine after each test. */
    @AfterEach
    void tearDown() {
        gameEngine = null;
    }

    @Nested
    @DisplayName("Class Constructors")
    class Constructors {
        // tmp
    }

    @Nested
    @DisplayName("Method newGame")
    class TestNewGame {
        /** Test that the game state is set to READY after a new game is created. */
        @Test
        @DisplayName("Should throw an exception if the game state is not INITIALIZED or FINISHED")
        void shouldThrowExceptionIfGameStateIsNotInitializedOrFinished() {
            gameEngine.setGameState(GameState.READY);

            assertThatThrownBy(() -> gameEngine.newGame())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("The game is already started. You must end the game first.");

            gameEngine.setGameState(GameState.PLAYING);

            assertThatThrownBy(() -> gameEngine.newGame())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("The game is already started. You must end the game first.");
        }

        @Test
        @DisplayName("Should recreate the board and reset the bot managers")
        void shouldRecreateBoardAndResetBotManagers() {

            BotManager botManager1 = spy(BotManager.class);
            BotManager botManager2 = spy(BotManager.class);

            gameEngine = spy(new GameEngine(List.of(botManager1, botManager2)));

            gameEngine.newGame();

            verify(botManager1, times(1)).reset();
            verify(botManager2, times(1)).reset();

            assertThat(gameEngine.getBoard()).isEqualTo(new Board());
        }

        @Test
        @DisplayName("newGame should set the game state to READY")
        void newGame_shouldSetGameStateToReady() {
            gameEngine.newGame();
            assertThat(gameEngine.getGameState()).isEqualTo(GameState.READY);
        }

        @Test
        @DisplayName("newGame should display a lot of messages")
        void newGame_shouldDisplayALotOfMessages() {
            ConsoleUserInterface consoleUserInterface = mock(ConsoleUserInterface.class);

            gameEngine =
                    new GameEngine(
                            1,
                            new Board(),
                            consoleUserInterface,
                            GameState.INITIALIZED,
                            new ArrayList<>(List.of(spy(BotManager.class), spy(BotManager.class))),
                            new Scoreboard());

            gameEngine.newGame();

            verify(consoleUserInterface, times(1)).displayLineSeparator();
            verify(consoleUserInterface, times(1)).displayMessage("Welcome to Takenoko!");
            verify(consoleUserInterface, times(1))
                    .displayMessage("The new game has been set up. You can start the game !");
        }
    }

    @Nested
    @DisplayName("Method startGame")
    class TestStartGame {
        @Nested
        @DisplayName("Exceptions when started at the wrong time")
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

        @Test
        @DisplayName("Should change the game state to PLAYING")
        void startGame_shouldChangeGameStateToPlaying() {
            gameEngine.setGameState(GameState.READY);
            gameEngine.startGame();
            assertThat(gameEngine.getGameState()).isEqualTo(GameState.PLAYING);
        }

        @Test
        @DisplayName("Should display a lot of messages")
        void startGame_shouldDisplayALotOfMessages() {
            ConsoleUserInterface consoleUserInterface = mock(ConsoleUserInterface.class);

            BotManager botManager1 = mock(BotManager.class);
            when(botManager1.getName()).thenReturn("Bot 1");
            when(botManager1.getObjectiveDescription()).thenReturn("Objective 1");
            BotManager botManager2 = mock(BotManager.class);
            when(botManager2.getName()).thenReturn("Bot 2");
            when(botManager2.getObjectiveDescription()).thenReturn("Objective 2");

            gameEngine =
                    new GameEngine(
                            1,
                            new Board(),
                            consoleUserInterface,
                            GameState.READY,
                            new ArrayList<>(List.of(botManager1, botManager2)),
                            new Scoreboard());

            gameEngine.startGame();

            verify(consoleUserInterface, times(1)).displayMessage("The game has started !");
            verify(consoleUserInterface, times(1))
                    .displayMessage("Bot 1 has the objective : Objective 1");
            verify(consoleUserInterface, times(1))
                    .displayMessage("Bot 2 has the objective : Objective 2");
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
        @DisplayName("when bots have no objective then they should play all the rounds")
        void playGame_whenBotsHaveNoObjective_shouldPlayAllTheRounds() {

            BotManager botManager01 = spy(new BotManager(new TilePlacingBot()));
            BotManager botManager02 = spy(new BotManager(new TilePlacingBot()));
            when(botManager01.isObjectiveAchieved()).thenReturn(false);
            when(botManager02.isObjectiveAchieved()).thenReturn(false);

            GameEngine gameEngine =
                    new GameEngine(new ArrayList<>(List.of(botManager01, botManager02)));

            gameEngine.newGame();
            gameEngine.startGame();
            gameEngine.playGame();

            verify(botManager01, times(GameEngine.DEFAULT_NUMBER_OF_ROUNDS)).playBot(any());
            verify(botManager02, times(GameEngine.DEFAULT_NUMBER_OF_ROUNDS)).playBot(any());
        }

        @Test
        @DisplayName("should display a lot of messages when playing")
        void playGame_shouldDisplayALotOfMessagesWhenPlaying() {
            ConsoleUserInterface consoleUserInterface = mock(ConsoleUserInterface.class);

            BotManager botManager1 = mock(BotManager.class);
            when(botManager1.getName()).thenReturn("Bot 1");
            BotManager botManager2 = mock(BotManager.class);
            when(botManager2.getName()).thenReturn("Bot 2");

            gameEngine =
                    new GameEngine(
                            1,
                            new Board(),
                            consoleUserInterface,
                            GameState.READY,
                            new ArrayList<>(List.of(botManager1, botManager2)),
                            new Scoreboard());

            gameEngine.startGame();
            gameEngine.playGame();

            verify(consoleUserInterface, times(1)).displayMessage("===== Round 1 =====");
            verify(consoleUserInterface, times(1)).displayMessage("===== <Bot 1> is playing =====");
            verify(consoleUserInterface, times(1)).displayMessage("===== <Bot 2> is playing =====");
        }

        @Test
        @DisplayName("should increment score when a bot has achieved its objective")
        void playGame_shouldIncrementScoreWhenABotHasAchievedItsObjective() {
            ConsoleUserInterface consoleUserInterface = mock(ConsoleUserInterface.class);

            BotManager botManager1 = mock(BotManager.class);
            when(botManager1.getName()).thenReturn("Bot 1");
            when(botManager1.getObjectiveDescription()).thenReturn("Objective 1");
            when(botManager1.isObjectiveAchieved()).thenReturn(true);
            when(botManager1.getScore()).thenReturn(10);
            BotManager botManager2 = mock(BotManager.class);
            when(botManager2.getName()).thenReturn("Bot 2");

            gameEngine =
                    new GameEngine(
                            1,
                            new Board(),
                            consoleUserInterface,
                            GameState.READY,
                            new ArrayList<>(List.of(botManager1, botManager2)),
                            new Scoreboard());

            gameEngine.startGame();
            gameEngine.playGame();

            verify(botManager1, times(1)).incrementScore(anyInt());
        }

        @Test
        @DisplayName("should display a lot of messages when objective is achieved")
        void playGame_shouldDisplayALotOfMessagesWhenObjectiveIsAchieved() {
            ConsoleUserInterface consoleUserInterface = mock(ConsoleUserInterface.class);

            BotManager botManager1 = mock(BotManager.class);
            when(botManager1.getName()).thenReturn("Bot 1");
            when(botManager1.getObjectiveDescription()).thenReturn("Objective 1");
            when(botManager1.isObjectiveAchieved()).thenReturn(true);
            when(botManager1.getScore()).thenReturn(10);
            BotManager botManager2 = mock(BotManager.class);
            when(botManager2.getName()).thenReturn("Bot 2");

            gameEngine =
                    new GameEngine(
                            1,
                            new Board(),
                            consoleUserInterface,
                            GameState.READY,
                            new ArrayList<>(List.of(botManager1, botManager2)),
                            new Scoreboard());

            gameEngine.startGame();
            gameEngine.playGame();

            verify(consoleUserInterface, times(1))
                    .displayMessage(
                            "Bot 1 has achieved the objective Objective 1, it has won with 10"
                                    + " points");
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
                        Arguments.of(GameState.INITIALIZED), Arguments.of(GameState.READY));
            }

            @ParameterizedTest(name = "i.e. : {0}")
            @MethodSource("streamOfStatesThatAreNotPLAYING")
            @DisplayName("should throw an exception when the game state is not PLAYING or FINISHED")
            void endGame_shouldThrowException_WhenGameStateIsNotPLAYING(
                    GameState currentGameState) {
                gameEngine.setGameState(currentGameState);
                assertThatThrownBy(() -> gameEngine.endGame())
                        .isInstanceOf(IllegalStateException.class)
                        .hasMessage("The game is not started yet. You must first start the game.");
            }
        }

        @Test
        @DisplayName("endGame should set the game state to FINISHED")
        void endGame_shouldSetGameStateToFinished() {
            gameEngine.newGame();
            gameEngine.startGame();
            gameEngine.endGame();
            assertThat(gameEngine.getGameState()).isEqualTo(GameState.FINISHED);
        }

        @Test
        @DisplayName("endGame should display a lot of messages")
        void endGame_shouldDisplayALotOfMessages() {
            ConsoleUserInterface consoleUserInterface = mock(ConsoleUserInterface.class);

            Scoreboard scoreboard = spy(Scoreboard.class);

            gameEngine =
                    new GameEngine(
                            1,
                            new Board(),
                            consoleUserInterface,
                            GameState.READY,
                            new ArrayList<>(List.of(spy(BotManager.class), spy(BotManager.class))),
                            scoreboard);

            gameEngine.startGame();
            gameEngine.endGame();

            verify(consoleUserInterface, times(1)).displayMessage(scoreboard.toString());
            verify(consoleUserInterface, times(1))
                    .displayMessage("The game is finished. Thanks for playing !");
        }
    }

    @Nested
    @DisplayName("Method runGame")
    class TestRunGame {
        @Test
        @DisplayName("should run every steps of the game")
        void runGame_shouldRunEveryStepsOfTheGame() {
            GameEngine gameEngine = spy(new GameEngine());
            gameEngine.runGame();
            verify(gameEngine).newGame();
            verify(gameEngine).startGame();
            verify(gameEngine).playGame();
            verify(gameEngine).endGame();
        }

        @Test
        @DisplayName("should update scoreboard")
        void runGame_shouldUpdateScoreboard() {
            Scoreboard scoreboard = spy(Scoreboard.class);

            GameEngine gameEngine =
                    new GameEngine(
                            1,
                            new Board(),
                            new ConsoleUserInterface(),
                            GameState.INITIALIZED,
                            new ArrayList<>(List.of(spy(BotManager.class), spy(BotManager.class))),
                            scoreboard);

            gameEngine.runGame();

            verify(scoreboard, times(2)).addScore(any(UUID.class), anyInt());
            verify(scoreboard).incrementNumberOfGamesPlayed();
        }

        @Test
        @DisplayName("should run runGame multiple times")
        void runGame_shouldRunRunGameMultipleTimes() {
            GameEngine gameEngine = spy(new GameEngine());
            gameEngine.runGame(2);
            verify(gameEngine, times(2)).runGame();
        }
    }
}
