package com.takenoko;

import static org.assertj.core.api.Assertions.assertThat;

import com.takenoko.ui.ConsoleUserInterface;
import com.takenoko.vector.Vector;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
        @DisplayName("The objective is to place two tiles, returns the correct description")
        void getObjectiveDescription_WhenObjectiveIsToPlace2Tiles_ThenReturnsCorrectDescription() {
            assertThat(botManager.getObjectiveDescription())
                    .isEqualTo(new PlaceTileObjective(2).toString());
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
            assertThat(botManager.objectiveIsAchieved()).isFalse();
        }

        @Test
        @DisplayName("When board satisfies objective, objective is achieved")
        void verifyObjective_ThenReturnsTrue() {
            Board board = new Board();
            board.placeTile(board.getAvailableTiles().get(0), new Vector(1, -1, 0));
            board.placeTile(board.getAvailableTiles().get(0), new Vector(-1, 1, 0));
            botManager.verifyObjective(board);
            assertThat(botManager.objectiveIsAchieved()).isTrue();
        }
    }
}
