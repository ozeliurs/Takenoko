package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.bot.Bot;
import com.takenoko.inventory.Inventory;
import com.takenoko.ui.ConsoleUserInterface;
import org.junit.jupiter.api.*;

class BotManagerTest {
    BotManager botManager;
    ConsoleUserInterface consoleUserInterface;
    Bot bot;
    BotState botState;
    Board board;
    SingleBotStatistics singleBotStatistics;

    @BeforeEach
    void setUp() {
        consoleUserInterface = mock(ConsoleUserInterface.class);
        bot = mock(Bot.class);
        botState = mock(BotState.class);
        board = mock(Board.class);
        singleBotStatistics = mock(SingleBotStatistics.class);

        botManager =
                new BotManager(consoleUserInterface, "Bot", bot, botState, singleBotStatistics);
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

    @Nested
    @DisplayName("Method playBot()")
    class TestPlayBot {}
}
