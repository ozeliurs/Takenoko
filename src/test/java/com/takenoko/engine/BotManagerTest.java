package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.objective.MovePandaObjective;
import com.takenoko.objective.Objective;
import com.takenoko.player.TilePlacingBot;
import org.junit.jupiter.api.*;

public class BotManagerTest {
    TilePlacingBot tilePlacingBot;
    BotManager botManager;

    @BeforeEach
    void setUp() {
        tilePlacingBot = new TilePlacingBot();
        botManager = new BotManager(tilePlacingBot);
    }

    @AfterEach
    void tearDown() {
        tilePlacingBot = null;
        botManager = null;
    }

    @Nested
    @DisplayName("Method getObjectiveDescription")
    class TestGetObjectiveDescription {
        @Test
        @DisplayName("The objective is to have two adjacent tiles, returns the correct description")
        void
                getObjectiveDescription_WhenObjectiveIsToHaveTwoAdjacentTiles_ThenReturnsCorrectDescription() {
            assertThat(botManager.getObjectiveDescription())
                    .isEqualTo(new MovePandaObjective().toString());
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
            assertThat(botManager.getNumberOfActions()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("Method verifyObjective and objectiveIsAchieved")
    class TestVerifyObjective {
        @Test
        @DisplayName("By default when board does not satisfy objective, objective is not achieved")
        void verifyObjective_ThenReturnsFalse() {
            Board board = new Board();
            botManager.verifyObjective(board);
            assertThat(botManager.isObjectiveAchieved()).isFalse();
        }

        @Test
        @DisplayName("When board satisfies objective, objective is achieved")
        void verifyObjective_ThenReturnsTrue() {
            Objective objective = mock(Objective.class);
            when(objective.isAchieved()).thenReturn(true);
            botManager.setObjective(objective);
            botManager.verifyObjective(mock(Board.class));
            assertThat(botManager.isObjectiveAchieved()).isTrue();
        }
    }

    @Nested
    @DisplayName("Method playBot")
    class playTilePlacingBot {
        @Test
        @DisplayName("when bot has no goals, should place ten tiles")
        void playBot_WhenBotHasNoGoals_ThenPlacesTenTiles() {
            Board board = new Board();
            botManager.setObjective(null);
            botManager.playBot(board);
            assertThat(board.getLayerManager().getTileLayer().getTiles().size() - 1).isEqualTo(10);
        }

        @Test
        @DisplayName("when bot has no goals, should display ten tile placement messages")
        void playBot_WhenBotHasNoGoals_ThenDisplaysTenTilePlacementMessages() {
            Board board = new Board();
            BotManager botManager = new BotManager(tilePlacingBot);
            botManager.setObjective(null);
            botManager.playBot(board);
            assertThat(board.getLayerManager().getTileLayer().getTiles())
                    .hasSize(10 + 1); // +1 because of the first empty string
        }
    }
}
