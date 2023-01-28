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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SingleGardenerObjectiveTest {
    private SingleGardenerObjective objectiveWithImprovement;
    //
    private static final int TARGET_SIZE = 3;
    private static final TileColor TARGET_COLOR = TileColor.PINK;
    private static final ImprovementType TARGET_IMPROVEMENT_TYPE = ImprovementType.ENCLOSURE;

    @BeforeEach
    void setUp() {
        objectiveWithImprovement =
                new SingleGardenerObjective(TARGET_SIZE, TARGET_COLOR, TARGET_IMPROVEMENT_TYPE);
    }

    @Nested
    @DisplayName("method equals")
    class TestEquals {
        @Test
        @DisplayName("should return true when the two objects are equal")
        void shouldReturnTrueWhenTheTwoObjectsAreTheEqual() {
            assertThat(objectiveWithImprovement)
                    .isEqualTo(
                            new SingleGardenerObjective(
                                    TARGET_SIZE, TARGET_COLOR, TARGET_IMPROVEMENT_TYPE));
        }

        @Nested
        @DisplayName("should return false when the two objects are not equal")
        class TestNotEquals {
            @Test
            @DisplayName("because the color is not the same")
            void shouldReturnFalseWhenTheTwoObjectsAreNotEqualBecauseThecolorIsNotTheSame() {
                assertThat(objectiveWithImprovement)
                        .isNotEqualTo(
                                new SingleGardenerObjective(
                                        TARGET_SIZE, TileColor.GREEN, TARGET_IMPROVEMENT_TYPE));
            }

            @Test
            @DisplayName("because the improvement is not the same")
            void shouldReturnFalseWhenTheTwoObjectsAreNotEqualBecauseTheImprovementIsNotTheSame() {
                assertThat(objectiveWithImprovement)
                        .isNotEqualTo(
                                new SingleGardenerObjective(
                                        TARGET_SIZE, TARGET_COLOR, ImprovementType.NONE));
            }

            @Test
            @DisplayName("because the size is not the same")
            void shouldReturnFalseWhenTheTwoObjectsAreNotEqualBecauseTheSizeIsNotTheSame() {
                assertThat(objectiveWithImprovement)
                        .isNotEqualTo(
                                new SingleGardenerObjective(
                                        TARGET_SIZE - 1, TARGET_COLOR, TARGET_IMPROVEMENT_TYPE));
            }

            @Test
            @DisplayName("because the other object is null")
            void shouldReturnFalseWhenTheTwoObjectsAreNotEqualBecauseTheOtherObjectIsNull() {
                assertThat(objectiveWithImprovement).isNotEqualTo(null);
            }

            @Test
            @DisplayName("because the other object is not a SingleGardenerObjective")
            void
                    shouldReturnFalseWhenTheTwoObjectsAreNotEqualBecauseTheOtherObjectIsNotASingleGardenerObjective() {
                assertThat(objectiveWithImprovement).isNotEqualTo(new Object());
            }
        }
    }

    @Nested
    @DisplayName("method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("should return the same hashcode when the two objects are equal")
        void shouldReturnTheSameHashcodeWhenTheTwoObjectsAreEqual() {
            assertThat(
                            new SingleGardenerObjective(
                                            TARGET_SIZE, TARGET_COLOR, TARGET_IMPROVEMENT_TYPE)
                                    .hashCode())
                    .hasSameHashCodeAs(objectiveWithImprovement);
        }

        @Nested
        @DisplayName("should return a different hashcode when the two objects are not equal")
        class TestNotEquals {
            @Test
            @DisplayName("because the color is not the same")
            void shouldReturnADifferentHashcodeWhenTheTwoObjectsAreNotEqual() {
                assertThat(objectiveWithImprovement.hashCode())
                        .isNotEqualTo(
                                new SingleGardenerObjective(
                                                TARGET_SIZE,
                                                TileColor.GREEN,
                                                TARGET_IMPROVEMENT_TYPE)
                                        .hashCode());
            }

            @Test
            @DisplayName("because the improvement is not the same")
            void shouldReturnADifferentHashcodeWhenTheTwoObjectsAreNotEqual2() {
                assertThat(objectiveWithImprovement.hashCode())
                        .isNotEqualTo(
                                new SingleGardenerObjective(
                                                TARGET_SIZE, TARGET_COLOR, ImprovementType.NONE)
                                        .hashCode());
            }

            @Test
            @DisplayName("because the size is not the same")
            void shouldReturnADifferentHashcodeWhenTheTwoObjectsAreNotEqual3() {
                assertThat(objectiveWithImprovement.hashCode())
                        .isNotEqualTo(
                                new SingleGardenerObjective(
                                                TARGET_SIZE - 1,
                                                TARGET_COLOR,
                                                TARGET_IMPROVEMENT_TYPE)
                                        .hashCode());
            }
        }
    }

    @Nested
    @DisplayName("method copy")
    class TestCopy {
        @Test
        @DisplayName("should return a copy of the objective")
        void shouldReturnACopyOfTheObjective() {
            assertThat(objectiveWithImprovement.copy())
                    .isEqualTo(objectiveWithImprovement)
                    .isNotSameAs(objectiveWithImprovement);
        }
    }

    @Nested
    @DisplayName("method getCompletion")
    class TestGetCompletion {
        @Test
        @DisplayName("should return the completion percentage when the objective is not started")
        void getCompletion_shouldReturnTheCompletionPercentageWhenTheObjectiveIsNotStarted() {
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            assertThat(objectiveWithImprovement.getCompletion(board, botManager)).isZero();
        }

        @Test
        @DisplayName("should return the completion percentage when the objective is started")
        void getCompletion_shouldReturnTheCompletionPercentageWhenTheObjectiveIsStarted() {
            // Variables
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            PositionVector position = new PositionVector(1, -1, 0);
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            Tile tile = mock(Tile.class);
            tiles.put(position, tile);
            int currentBambooCount = 1;
            // Behavior
            when(tile.getColor()).thenReturn(TARGET_COLOR);
            when(board.getTiles()).thenReturn(tiles);
            when(tile.getImprovement()).thenReturn(Optional.of(TARGET_IMPROVEMENT_TYPE));
            when(board.getBambooAt(position)).thenReturn(new LayerBambooStack(currentBambooCount));
            // Test
            assertThat(objectiveWithImprovement.getCompletion(board, botManager))
                    .isEqualTo((float) 1 / TARGET_SIZE);
        }

        @Test
        @DisplayName("should return the completion percentage when the objective is completed")
        void getCompletion_shouldReturnTheCompletionPercentageWhenTheObjectiveIsCompleted() {
            // Variables
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            PositionVector position = new PositionVector(1, -1, 0);
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            Tile tile = mock(Tile.class);
            tiles.put(position, tile);
            // Behavior
            when(tile.getColor()).thenReturn(TARGET_COLOR);
            when(board.getTiles()).thenReturn(tiles);
            when(tile.getImprovement()).thenReturn(Optional.of(TARGET_IMPROVEMENT_TYPE));
            when(board.getBambooAt(position)).thenReturn(new LayerBambooStack(TARGET_SIZE));
            // Test
            assertThat(objectiveWithImprovement.getCompletion(board, botManager)).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("method verify")
    class TestVerify {
        @Nested
        @DisplayName("should return true")
        class VerifyShouldReturnTrue {
            @Test
            @DisplayName(
                    "when the bamboo count is equal and the color is the same and the improvement"
                            + " is the same")
            void
                    verify_shouldReturnTrue_whenTheBambooCountIsEqualAndTheColorIsTheSameAndTheImprovementIsTheSame() {
                // Variables
                Board board = mock(Board.class);
                BotManager botManager = mock(BotManager.class);
                PositionVector position = new PositionVector(1, -1, 0);
                HashMap<PositionVector, Tile> tiles = new HashMap<>();
                Tile tile = mock(Tile.class);
                tiles.put(position, tile);
                // Behavior
                when(tile.getColor()).thenReturn(TARGET_COLOR);
                when(board.getTiles()).thenReturn(tiles);
                when(tile.getImprovement()).thenReturn(Optional.of(TARGET_IMPROVEMENT_TYPE));
                when(board.getBambooAt(position)).thenReturn(new LayerBambooStack(TARGET_SIZE));
                // Test
                objectiveWithImprovement.verify(board, botManager);
                assertThat(objectiveWithImprovement.isAchieved()).isTrue();
            }

            @Test
            @DisplayName(
                    "when the bamboo count is equal and the color is the same and the improvement"
                            + " is any")
            void
                    verify_shouldReturnTrue_whenTheBambooCountIsEqualAndTheColorIsTheSameAndTheImprovementIsAny() {
                // Variables
                Board board = mock(Board.class);
                BotManager botManager = mock(BotManager.class);
                PositionVector position = new PositionVector(1, -1, 0);
                HashMap<PositionVector, Tile> tiles = new HashMap<>();
                Tile tile = mock(Tile.class);
                tiles.put(position, tile);
                // Behavior
                when(tile.getColor()).thenReturn(TARGET_COLOR);
                when(board.getTiles()).thenReturn(tiles);
                when(tile.getImprovement()).thenReturn(Optional.of(TARGET_IMPROVEMENT_TYPE));
                when(board.getBambooAt(position)).thenReturn(new LayerBambooStack(TARGET_SIZE));
                // Test
                objectiveWithImprovement.verify(board, botManager);
                assertThat(objectiveWithImprovement.isAchieved()).isTrue();
            }

            @Test
            @DisplayName(
                    "when the bamboo count is equal and the color is any and the improvement is the"
                            + " same")
            void
                    verify_shouldReturnTrue_whenTheBambooCountIsEqualAndTheColorIsAnyAndTheImprovementIsTheSame() {
                // Variables
                Board board = mock(Board.class);
                BotManager botManager = mock(BotManager.class);
                PositionVector position = new PositionVector(1, -1, 0);
                HashMap<PositionVector, Tile> tiles = new HashMap<>();
                Tile tile = mock(Tile.class);
                tiles.put(position, tile);
                // Behavior
                when(tile.getColor()).thenReturn(TARGET_COLOR);
                when(board.getTiles()).thenReturn(tiles);
                when(tile.getImprovement()).thenReturn(Optional.of(TARGET_IMPROVEMENT_TYPE));
                when(board.getBambooAt(position)).thenReturn(new LayerBambooStack(TARGET_SIZE));
                // Test
                objectiveWithImprovement.verify(board, botManager);
                assertThat(objectiveWithImprovement.isAchieved()).isTrue();
            }
        }

        @Nested
        @DisplayName("should return false")
        class VerifyShouldReturnFalse {
            @Test
            @DisplayName(
                    "when the bamboo count is not equal and the color is the same and the"
                            + " improvement is the same")
            void
                    verify_shouldReturnFalse_whenTheBambooCountIsNotEqualAndTheColorIsTheSameAndTheImprovementIsTheSame() {
                // Variables
                Board board = mock(Board.class);
                PositionVector position = new PositionVector(1, -1, 0);
                HashMap<PositionVector, Tile> tiles = new HashMap<>();
                Tile tile = mock(Tile.class);
                tiles.put(position, tile);
                // Behavior
                when(tile.getColor()).thenReturn(TARGET_COLOR);
                when(board.getTiles()).thenReturn(tiles);
                when(tile.getImprovement()).thenReturn(Optional.of(TARGET_IMPROVEMENT_TYPE));
                when(board.getBambooAt(position)).thenReturn(new LayerBambooStack(TARGET_SIZE - 1));
                // Test
                assertThat(objectiveWithImprovement.isAchieved()).isFalse();
            }

            @Test
            @DisplayName(
                    "when the bamboo count is equal and the color is not the same and the"
                            + " improvement is the same")
            void
                    verify_shouldReturnFalse_whenTheBambooCountIsEqualAndTheColorIsNotTheSameAndTheImprovementIsTheSame() {
                // Variables
                Board board = mock(Board.class);
                PositionVector position = new PositionVector(1, -1, 0);
                HashMap<PositionVector, Tile> tiles = new HashMap<>();
                Tile tile = mock(Tile.class);
                tiles.put(position, tile);
                // Behavior
                when(tile.getColor()).thenReturn(TileColor.NONE);
                when(board.getTiles()).thenReturn(tiles);
                when(tile.getImprovement()).thenReturn(Optional.of(TARGET_IMPROVEMENT_TYPE));
                when(board.getBambooAt(position)).thenReturn(new LayerBambooStack(TARGET_SIZE));
                // Test
                assertThat(objectiveWithImprovement.isAchieved()).isFalse();
            }

            @Test
            @DisplayName(
                    "when the bamboo count is equal and the color is the same and the improvement"
                            + " is not the same")
            void
                    verify_shouldReturnFalse_whenTheBambooCountIsEqualAndTheColorIsTheSameAndTheImprovementIsNotTheSame() {
                // Variables
                Board board = mock(Board.class);
                PositionVector position = new PositionVector(1, -1, 0);
                HashMap<PositionVector, Tile> tiles = new HashMap<>();
                Tile tile = mock(Tile.class);
                tiles.put(position, tile);
                // Behavior
                when(tile.getColor()).thenReturn(TARGET_COLOR);
                when(board.getTiles()).thenReturn(tiles);
                when(tile.getImprovement()).thenReturn(Optional.of(ImprovementType.NONE));
                when(board.getBambooAt(position)).thenReturn(new LayerBambooStack(TARGET_SIZE));
                // Test
                assertThat(objectiveWithImprovement.isAchieved()).isFalse();
            }
        }
    }

    @Nested
    @DisplayName("method getEligiblePositions")
    class GetEligiblePositions {
        @Test
        @DisplayName("should return the eligible positions")
        void getEligiblePositions_shouldReturnTheEligiblePositions() {
            // Variables
            Board board = mock(Board.class);
            HashMap<PositionVector, Tile> tiles = new HashMap<>();

            // Position for all the tiles
            PositionVector position01 = new PositionVector(1, -1, 0);
            PositionVector position02 = new PositionVector(2, -2, 0);
            PositionVector position03 = new PositionVector(3, -3, 0);
            PositionVector position04 = new PositionVector(4, -4, 0);
            PositionVector position05 = new PositionVector(5, -5, 0);

            // Define all tiles
            Tile tileCorrectColorImprovementSize = new Tile(TARGET_IMPROVEMENT_TYPE, TARGET_COLOR);
            when(board.getBambooAt(position01)).thenReturn(new LayerBambooStack(TARGET_SIZE));
            //
            Tile tileCorrectImprovementSize = new Tile(TARGET_IMPROVEMENT_TYPE, TileColor.NONE);
            when(board.getBambooAt(position02)).thenReturn(new LayerBambooStack(TARGET_SIZE));
            //
            Tile tileCorrectColorSize = new Tile(ImprovementType.NONE, TARGET_COLOR);
            when(board.getBambooAt(position03)).thenReturn(new LayerBambooStack(TARGET_SIZE));
            //
            Tile tileCorrectColorImprovementSmallerSize =
                    new Tile(TARGET_IMPROVEMENT_TYPE, TARGET_COLOR);
            when(board.getBambooAt(position04)).thenReturn(new LayerBambooStack(TARGET_SIZE - 1));
            //
            Tile tileCorrectColorImprovementGreaterSize =
                    new Tile(TARGET_IMPROVEMENT_TYPE, TARGET_COLOR);
            when(board.getBambooAt(position05)).thenReturn(new LayerBambooStack(TARGET_SIZE + 1));

            // Place tile on the board
            tiles.put(position01, tileCorrectColorImprovementSize);
            tiles.put(position02, tileCorrectImprovementSize);
            tiles.put(position03, tileCorrectColorSize);
            tiles.put(position04, tileCorrectColorImprovementSmallerSize);
            tiles.put(position05, tileCorrectColorImprovementGreaterSize);

            // Board getTiles()
            when(board.getTiles()).thenReturn(tiles);

            // Test
            assertThat(objectiveWithImprovement.getEligiblePositions(board))
                    .containsExactly(position01, position04);
        }
    }

    @Nested
    @DisplayName("method getMatchingPositions")
    class GetMatchingPositions {
        @Test
        @DisplayName("should return the matching positions")
        void getMatchingPositions_shouldReturnTheMatchingPositions() {
            // Variables
            Board board = mock(Board.class);
            HashMap<PositionVector, Tile> tiles = new HashMap<>();

            // Position for all the tiles
            PositionVector position01 = new PositionVector(1, -1, 0);
            PositionVector position02 = new PositionVector(2, -2, 0);
            PositionVector position03 = new PositionVector(3, -3, 0);
            PositionVector position04 = new PositionVector(4, -4, 0);
            PositionVector position05 = new PositionVector(5, -5, 0);

            // Define all tiles
            Tile tileCorrectColorImprovementSize = new Tile(TARGET_IMPROVEMENT_TYPE, TARGET_COLOR);
            when(board.getBambooAt(position01)).thenReturn(new LayerBambooStack(TARGET_SIZE));
            //
            Tile tileCorrectImprovementSize = new Tile(TARGET_IMPROVEMENT_TYPE, TileColor.NONE);
            when(board.getBambooAt(position02)).thenReturn(new LayerBambooStack(TARGET_SIZE));
            //
            Tile tileCorrectColorSize = new Tile(ImprovementType.NONE, TARGET_COLOR);
            when(board.getBambooAt(position03)).thenReturn(new LayerBambooStack(TARGET_SIZE));
            //
            Tile tileCorrectColorImprovementSmallerSize =
                    new Tile(TARGET_IMPROVEMENT_TYPE, TARGET_COLOR);
            when(board.getBambooAt(position04)).thenReturn(new LayerBambooStack(TARGET_SIZE - 1));
            //
            Tile tileCorrectColorImprovementGreaterSize =
                    new Tile(TARGET_IMPROVEMENT_TYPE, TARGET_COLOR);
            when(board.getBambooAt(position05)).thenReturn(new LayerBambooStack(TARGET_SIZE + 1));

            // Place tile on the board
            tiles.put(position01, tileCorrectColorImprovementSize);
            tiles.put(position02, tileCorrectImprovementSize);
            tiles.put(position03, tileCorrectColorSize);
            tiles.put(position04, tileCorrectColorImprovementSmallerSize);
            tiles.put(position05, tileCorrectColorImprovementGreaterSize);

            // Board getTiles()
            when(board.getTiles()).thenReturn(tiles);

            // Test
            assertThat(objectiveWithImprovement.getMatchingPositions(board))
                    .containsExactly(position01);
        }
    }
}
