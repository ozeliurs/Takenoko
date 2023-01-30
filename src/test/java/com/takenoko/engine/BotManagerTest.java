package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.bot.Bot;
import com.takenoko.inventory.Inventory;
import com.takenoko.objective.Objective;
import com.takenoko.ui.ConsoleUserInterface;
import java.util.List;
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
    @DisplayName("Method verifyObjective")
    class TestVerifyObjective {
        @Test
        @DisplayName("When called should call verify of objective if not null")
        void verifyObjective_whenCalled_shouldCallVerifyOfObjectiveIfNotNull() {
            Objective objective = mock(Objective.class);
            when(botState.getObjectives()).thenReturn(List.of(objective));
            botManager.verifyObjectives(board);
            verify(objective, times(1)).verify(board, botManager);
        }

        @Test
        @DisplayName("When called should not call verify of objective if null")
        void verifyObjective_whenCalled_shouldNotCallVerifyOfObjectiveIfNull() {
            when(botState.getObjectives()).thenReturn(null);
            botManager.verifyObjectives(board);
            verify(botState, times(1)).getObjectives();
        }
    }

    @Test
    @DisplayName("Method setObjectives")
    void setObjective() {
        Objective objective = mock(Objective.class);
        botManager.setObjective(objective);
        verify(botState, times(1)).addObjective(objective);
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
    @DisplayName("Method getScore")
    void getScore() {
        when(botState.getObjectiveScore()).thenReturn(2);

        botManager.getObjectiveScore();
        verify(botState, times(1)).getObjectiveScore();

        assertThat(botManager.getObjectiveScore()).isEqualTo(2);
    }

    @Test
    @DisplayName("Method reset")
    void reset() {
        botManager.reset();
        verify(botState, times(1)).reset();
    }
}
