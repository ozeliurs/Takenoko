package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.actions.Action;
import com.takenoko.bot.Bot;
import com.takenoko.inventory.Inventory;
import com.takenoko.objective.Objective;
import com.takenoko.ui.ConsoleUserInterface;
import org.junit.jupiter.api.*;

class BotManagerTest {
    BotManager botManager;
    ConsoleUserInterface consoleUserInterface;
    Bot bot;
    BotState botState;
    Board board;

    @BeforeEach
    void setUp() {
        consoleUserInterface = mock(ConsoleUserInterface.class);
        bot = mock(Bot.class);
        botState = mock(BotState.class);
        board = mock(Board.class);

        botManager = new BotManager(consoleUserInterface, "Bot", bot, botState);
    }

    @AfterEach
    void tearDown() {
        botManager = null;
    }

    @Nested
    @DisplayName("Method playBot")
    class PlayBot {
        @Test
        @DisplayName("should do the lifecyle of a bot with win")
        void shouldDoTheLifecyleOfABotWithWin() {
            when(botState.getNumberOfActions()).thenReturn(2);
            Action action = mock(Action.class);
            when(bot.chooseAction(any(), any())).thenReturn(action);
            when(board.copy()).thenReturn(board);
            when(botState.copy()).thenReturn(botState);
            when(botState.getObjective()).thenReturn(mock(Objective.class));
            when(botState.getObjective().isAchieved()).thenReturn(true);

            botManager.playBot(board);
            verify(bot, times(1)).chooseAction(board, botState);
            verify(action, times(1)).execute(board, botManager);
            verify(botState.getObjective(), times(1)).isAchieved();
        }

        @Test
        @DisplayName("should do the lifecyle of a bot with no win")
        void shouldDoTheLifecyleOfABotWithNoWin() {
            when(botState.getNumberOfActions()).thenReturn(2);
            Action action = mock(Action.class);
            when(bot.chooseAction(any(), any())).thenReturn(action);
            when(board.copy()).thenReturn(board);
            when(botState.copy()).thenReturn(botState);
            when(botState.getObjective()).thenReturn(mock(Objective.class));
            when(botState.getObjective().isAchieved()).thenReturn(false);

            botManager.playBot(board);
            verify(bot, times(2)).chooseAction(board, botState);
            verify(action, times(2)).execute(board, botManager);
            verify(botState.getObjective(), times(2)).isAchieved();
        }
    }

    @Nested
    @DisplayName("Method getObjectiveDescription")
    class TestGetObjectiveDescription {
        @Test
        @DisplayName("should return the objective description")
        void shouldReturnTheObjectiveDescription() {
            Objective objective = mock(Objective.class);
            when(botState.getObjective()).thenReturn(objective);
            when(objective.toString()).thenReturn("Objective description");

            assertThat(botManager.getObjectiveDescription()).isEqualTo("Objective description");
        }

        @Test
        @DisplayName("When there is no objective, returns correctly")
        void getObjectiveDescription_WhenThereIsNoObjective_ThenReturnsCorrectly() {
            botManager.setObjective(null);
            assertThat(botManager.getObjectiveDescription()).isEqualTo("No current objective");
        }
    }

    @Nested
    @DisplayName("Method getNumberOfActions")
    class TestGetNumberOfActions {
        @Test
        @DisplayName("Default number of actions should be 2")
        void getNumberOfActions_ThenReturns2() {
            when(botState.getNumberOfActions()).thenReturn(2);
            assertThat(botManager.getNumberOfActions()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("Method displayMessage")
    class TestDisplayMessage {
        @Test
        @DisplayName("should display the message")
        void shouldDisplayTheMessage() {
            botManager.displayMessage("Message");
            verify(consoleUserInterface, times(1)).displayMessage("Message");
        }
    }

    @Nested
    @DisplayName("Method objectiveIsAchieved")
    class TestObjectiveIsAchieved {
        @Test
        @DisplayName("By default when board does not satisfy objective, objective is not achieved")
        void verifyObjective_whenObjectiveIsNotAchieved_thenReturnFalse() {
            when(botState.getObjective()).thenReturn(mock(Objective.class));
            when(botState.getObjective().isAchieved()).thenReturn(false);
            assertThat(botManager.isObjectiveAchieved()).isFalse();
        }

        @Test
        @DisplayName("By default when board satisfies objective, objective is achieved")
        void verifyObjective_whenObjectiveIsAchieved_thenReturnTrue() {
            when(botState.getObjective()).thenReturn(mock(Objective.class));
            when(botState.getObjective().isAchieved()).thenReturn(true);
            assertThat(botManager.isObjectiveAchieved()).isTrue();
        }

        @Test
        @DisplayName("When there is no objective, objective is not achieved")
        void verifyObjective_whenThereIsNoObjective_thenReturnFalse() {
            botManager.setObjective(null);
            botManager.verifyObjective(mock(Board.class));
            assertThat(botManager.isObjectiveAchieved()).isFalse();
        }
    }

    @Nested
    @DisplayName("Method verifyObjective")
    class TestVerifyObjective {
        @Test
        @DisplayName("When called should call verify of objective if not null")
        void verifyObjective_whenCalled_shouldCallVerifyOfObjectiveIfNotNull() {
            Objective objective = mock(Objective.class);
            when(botState.getObjective()).thenReturn(objective);
            botManager.verifyObjective(board);
            verify(objective, times(1)).verify(board, botManager);
        }

        @Test
        @DisplayName("When called should not call verify of objective if null")
        void verifyObjective_whenCalled_shouldNotCallVerifyOfObjectiveIfNull() {
            when(botState.getObjective()).thenReturn(null);
            botManager.verifyObjective(board);
            verify(botState, times(1)).getObjective();
        }
    }

    @Test
    @DisplayName("Method setObjective")
    void setObjective() {
        Objective objective = mock(Objective.class);
        botManager.setObjective(objective);
        verify(botState, times(1)).setObjective(objective);
    }

    @Test
    @DisplayName("Method getName")
    void getName() {
        assertThat(botManager.getName()).isEqualTo("Bot");
    }

    @Test
    @DisplayName("Method getEatenBambooCounter")
    void getEatenBambooCounter() {
        when(botState.getEatenBambooCounter()).thenReturn(2);

        botManager.getEatenBambooCounter();
        verify(botState, times(1)).getEatenBambooCounter();

        assertThat(botManager.getEatenBambooCounter()).isEqualTo(2);
    }

    @Test
    @DisplayName("Method getInventory")
    void getInventory() {
        when(botState.getInventory()).thenReturn(mock(Inventory.class));

        botManager.getInventory();
        verify(botState, times(1)).getInventory();
    }

    @Test
    @DisplayName("Method incrementScoreget & Score")
    void incrementScore() {
        botManager.incrementScore(2);
        assertThat(botManager.getScore()).isEqualTo(2);
    }

    @Test
    @DisplayName("Method reset")
    void reset() {
        botManager.reset();
        verify(botState, times(1)).reset();
    }
}
