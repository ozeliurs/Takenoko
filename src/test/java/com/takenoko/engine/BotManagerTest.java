package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.takenoko.actors.panda.MovePandaAction;
import com.takenoko.layers.tile.PlaceTileAction;
import com.takenoko.objective.EatBambooObjective;
import com.takenoko.objective.Objective;
import com.takenoko.player.TilePlacingAndPandaMovingBot;
import com.takenoko.player.TilePlacingBot;
import com.takenoko.tile.Tile;
import com.takenoko.vector.PositionVector;
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
                    .isEqualTo(new EatBambooObjective(2).toString());
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
    class playTilePlacingBot {}

    @Nested
    @DisplayName("Method getEatenBambooCounter")
    class TestGetEatenBambooCounter {
        @Test
        @DisplayName("When initialized, a botManager's bamboo counter is equal to zero")
        void getEatenBambooCounter_WhenInitialized_BambooCounterIsEqualToZero() {
            assertThat(botManager.getEatenBambooCounter()).isZero();
        }

        @Test
        @DisplayName(
                "When the botManager makes the panda eat a bamboo, the bamboo counter is at least"
                        + " one")
        void
                getEatenBambooCounter_WhenTheBotManagerMakesThePandaEatABamboo_TheBambooCounterIsAtLeastOne() {
            Board board = new Board();
            TilePlacingAndPandaMovingBot bot = mock(TilePlacingAndPandaMovingBot.class);
            when(bot.chooseAction(any(Board.class)))
                    .thenReturn(
                            new PlaceTileAction(new Tile(), new PositionVector(0, 1, -1)),
                            new MovePandaAction(new PositionVector(0, 1, -1)));
            BotManager botManager = new BotManager(bot);
            botManager.playBot(board);
            assertThat(botManager.getEatenBambooCounter()).isPositive();
        }
    }
}
