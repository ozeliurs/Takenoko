package com.takenoko.objective;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MultipleGardenerObjectiveTest {
    private MultipleGardenerObjective multipleGardenerObjective;
    private static final int NUMBER_OF_TIMES = 2;
    private static final int TARGET_SIZE = 3;
    private static final TileColor TARGET_COLOR = TileColor.PINK;
    private static final ImprovementType TARGET_IMPROVEMENT_TYPE = ImprovementType.ENCLOSURE;
    private static final SingleGardenerObjective SINGLE_GARDENER_OBJECTIVE =
            new SingleGardenerObjective(TARGET_SIZE, TARGET_COLOR, TARGET_IMPROVEMENT_TYPE);

    @BeforeEach
    void setUp() {
        multipleGardenerObjective =
                new MultipleGardenerObjective(SINGLE_GARDENER_OBJECTIVE, NUMBER_OF_TIMES);
    }

    @Nested
    @DisplayName("method equals")
    class TestEquals {
        @Test
        @DisplayName("should return true when the object is equal")
        void shouldReturnTrueWhenTheObjectIsEqual() {
            MultipleGardenerObjective multipleGardenerObjective2 =
                    new MultipleGardenerObjective(SINGLE_GARDENER_OBJECTIVE, NUMBER_OF_TIMES);
            assertThat(multipleGardenerObjective.equals(multipleGardenerObjective2)).isTrue();
        }

        @Test
        @DisplayName("should return false when the object is not equal")
        void shouldReturnFalseWhenTheObjectIsNotEqual() {
            MultipleGardenerObjective multipleGardenerObjective2 =
                    new MultipleGardenerObjective(SINGLE_GARDENER_OBJECTIVE, NUMBER_OF_TIMES + 1);
            assertThat(multipleGardenerObjective.equals(multipleGardenerObjective2)).isFalse();
        }

        @Test
        @DisplayName("should return false when the object is null")
        void shouldReturnFalseWhenTheObjectIsNull() {
            assertThat(multipleGardenerObjective).isNotNull();
        }

        @Test
        @DisplayName(
                "should return false when the object is not an instance of"
                        + " MultipleGardenerObjective")
        void shouldReturnFalseWhenTheObjectIsNotAnInstanceOfMultipleGardenerObjective() {
            assertThat(multipleGardenerObjective).isNotEqualTo(new Object());
        }
    }

    @Nested
    @DisplayName("method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("should return the same hash code when the object is equal")
        void shouldReturnTheSameHashCodeWhenTheObjectIsEqual() {
            MultipleGardenerObjective multipleGardenerObjective2 =
                    new MultipleGardenerObjective(SINGLE_GARDENER_OBJECTIVE, NUMBER_OF_TIMES);
            assertThat(multipleGardenerObjective2.hashCode())
                    .hasSameHashCodeAs(multipleGardenerObjective);
        }

        @Test
        @DisplayName("should return a different hash code when the object is not equal")
        void shouldReturnADifferentHashCodeWhenTheObjectIsNotEqual() {
            MultipleGardenerObjective multipleGardenerObjective2 =
                    new MultipleGardenerObjective(SINGLE_GARDENER_OBJECTIVE, NUMBER_OF_TIMES + 1);
            assertThat(multipleGardenerObjective.hashCode())
                    .isNotEqualTo(multipleGardenerObjective2.hashCode());
        }
    }

    @Nested
    @DisplayName("method copy")
    class TestCopy {
        @Test
        @DisplayName("should return a copy of the objective")
        void shouldReturnACopyOfTheObjective() {
            MultipleGardenerObjective multipleGardenerObjective2 = multipleGardenerObjective.copy();
            assertThat(multipleGardenerObjective2).isEqualTo(multipleGardenerObjective);
        }
    }

    @Nested
    @DisplayName("method getCompletion")
    class TestGetCompletion {
        @Test
        @DisplayName("should return the completion percentage when the objective is not started")
        void shouldReturnTheCompletionPercentageWhenTheObjectiveIsNotStarted() {
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            assertThat(multipleGardenerObjective.getCompletion(board, botManager)).isZero();
        }

        @Test
        @DisplayName("should return the completion percentage when the objective is started")
        void shouldReturnTheCompletionPercentageWhenTheObjectiveIsStarted() {
            // Variables
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            PositionVector positionVector01 = new PositionVector(1, -1, 0);
            PositionVector positionVector02 = new PositionVector(2, -2, 0);
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            Tile tile01 = new Tile(ImprovementType.ENCLOSURE, TARGET_COLOR);
            Tile tile02 = new Tile(ImprovementType.ENCLOSURE, TARGET_COLOR);
            tiles.put(positionVector01, tile01);
            tiles.put(positionVector02, tile02);
            // Behavior
            when(board.getTiles()).thenReturn(tiles);
            when(board.getBambooAt(positionVector01)).thenReturn(new LayerBambooStack(1));
            when(board.getBambooAt(positionVector02)).thenReturn(new LayerBambooStack(1));
            // Test
            assertThat(
                            Math.abs(
                                    multipleGardenerObjective.getCompletion(board, botManager)
                                            - (float) 1 / TARGET_SIZE))
                    .isLessThan(0.0001f);
        }

        @Test
        @DisplayName("should return the completion percentage when the objective is completed")
        void shouldReturnTheCompletionPercentageWhenTheObjectiveIsCompleted() {
            // Variables
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            PositionVector positionVector01 = new PositionVector(1, -1, 0);
            PositionVector positionVector02 = new PositionVector(2, -2, 0);
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            Tile tile01 = new Tile(ImprovementType.ENCLOSURE, TARGET_COLOR);
            Tile tile02 = new Tile(ImprovementType.ENCLOSURE, TARGET_COLOR);
            tiles.put(positionVector01, tile01);
            tiles.put(positionVector02, tile02);
            // Behavior
            when(board.getTiles()).thenReturn(tiles);
            when(board.getBambooAt(positionVector01)).thenReturn(new LayerBambooStack(TARGET_SIZE));
            when(board.getBambooAt(positionVector02)).thenReturn(new LayerBambooStack(TARGET_SIZE));
            // Test
            assertThat(multipleGardenerObjective.getCompletion(board, botManager)).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("method verify")
    class TestVerify {
        @Test
        @DisplayName("should return true when the objective is completed")
        void verify_shouldReturnTrueWhenTheObjectiveIsCompleted() {
            // Variables
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            PositionVector positionVector01 = new PositionVector(1, -1, 0);
            PositionVector positionVector02 = new PositionVector(2, -2, 0);
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            Tile tile01 = new Tile(ImprovementType.ENCLOSURE, TARGET_COLOR);
            Tile tile02 = new Tile(ImprovementType.ENCLOSURE, TARGET_COLOR);
            tiles.put(positionVector01, tile01);
            tiles.put(positionVector02, tile02);
            // Behavior
            when(board.getTiles()).thenReturn(tiles);
            when(board.getBambooAt(positionVector01)).thenReturn(new LayerBambooStack(TARGET_SIZE));
            when(board.getBambooAt(positionVector02)).thenReturn(new LayerBambooStack(TARGET_SIZE));
            // Test
            multipleGardenerObjective.verify(board, botManager);
            assertThat(multipleGardenerObjective.isAchieved()).isTrue();
        }

        @Test
        @DisplayName("should return false when the objective is not completed")
        void verify_shouldReturnFalseWhenTheObjectiveIsNotCompleted() {
            // Variables
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            PositionVector positionVector01 = new PositionVector(1, -1, 0);
            PositionVector positionVector02 = new PositionVector(2, -2, 0);
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            Tile tile01 = new Tile(ImprovementType.ENCLOSURE, TARGET_COLOR);
            Tile tile02 = new Tile(ImprovementType.ENCLOSURE, TARGET_COLOR);
            tiles.put(positionVector01, tile01);
            tiles.put(positionVector02, tile02);
            // Behavior
            when(board.getTiles()).thenReturn(tiles);
            when(board.getBambooAt(positionVector01)).thenReturn(new LayerBambooStack(1));
            when(board.getBambooAt(positionVector02)).thenReturn(new LayerBambooStack(1));
            // Test
            multipleGardenerObjective.verify(board, botManager);
            assertThat(multipleGardenerObjective.isAchieved()).isFalse();
        }
    }
}
