package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;

import com.takenoko.Board;
import com.takenoko.objective.MovedPandaObjective;
import com.takenoko.player.Bot;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.*;

public class BotManagerTest {
    Bot bot;
    BotManager botManager;

    @BeforeEach
    void setUp() {
        bot = new Bot();
        botManager = new BotManager(bot);
    }

    @AfterEach
    void tearDown() {
        bot = null;
        botManager = null;
    }

    @Nested
    @DisplayName("Method getObjectiveDescription")
    class TestGetObjectiveDescription {
        @Test
        @DisplayName("The objective is to have a panda moved, returns the correct description")
        void
                getObjectiveDescription_WhenObjectiveIsToHaveTwoAdjacentTiles_ThenReturnsCorrectDescription() {
            assertThat(botManager.getObjectiveDescription())
                    .isEqualTo(new MovedPandaObjective().toString());
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
        @Disabled // TODO: fix this test for MovedPandaObjective
        void verifyObjective_ThenReturnsTrue() {
            Board board = new Board();
            board.placeTile(board.getAvailableTiles().get(0), new PositionVector(1, -1, 0));
            board.placeTile(board.getAvailableTiles().get(0), new PositionVector(0, -1, 1));
            botManager.verifyObjective(board);
            assertThat(botManager.isObjectiveAchieved()).isTrue();
        }
    }

    @Nested
    @DisplayName("Method playBot")
    class playBot {
        @Test
        @DisplayName("when bot has no goals, should place ten tiles")
        void playBot_WhenBotHasNoGoals_ThenPlacesTenTiles() {
            Board board = new Board();
            botManager.setObjective(null);
            botManager.playBot(board);
            assertThat(board.getTiles().size() - 1).isEqualTo(10);
        }

        @Test
        @DisplayName("when bot has no goals, should display ten tile placement messages")
        void playBot_WhenBotHasNoGoals_ThenDisplaysTenTilePlacementMessages() {
            Board board = new Board();
            BotManager botManager = new BotManager(bot);
            botManager.setObjective(null);
            botManager.playBot(board);
            assertThat(board.getTiles()).hasSize(10 + 1); // +1 because of the first empty string
        }
    }
}
