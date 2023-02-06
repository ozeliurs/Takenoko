package com.takenoko.objective;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.takenoko.actions.actors.MoveGardenerAction;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotState;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SingleGardenerObjectiveTest {
    private SingleGardenerObjective objectiveWithImprovement;
    //
    private static final int TARGET_SIZE = 3;
    private static final TileColor TARGET_COLOR = TileColor.PINK;
    private static final ImprovementType TARGET_IMPROVEMENT_TYPE = ImprovementType.ENCLOSURE;

    @BeforeEach
    void setUp() {
        objectiveWithImprovement =
                new SingleGardenerObjective(TARGET_SIZE, TARGET_COLOR, TARGET_IMPROVEMENT_TYPE, 0);
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
                                    TARGET_SIZE, TARGET_COLOR, TARGET_IMPROVEMENT_TYPE, 0));
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
                                        TARGET_SIZE, TileColor.GREEN, TARGET_IMPROVEMENT_TYPE, 0));
            }

            @Test
            @DisplayName("because the improvement is not the same")
            void shouldReturnFalseWhenTheTwoObjectsAreNotEqualBecauseTheImprovementIsNotTheSame() {
                assertThat(objectiveWithImprovement)
                        .isNotEqualTo(
                                new SingleGardenerObjective(
                                        TARGET_SIZE, TARGET_COLOR, ImprovementType.NONE, 0));
            }

            @Test
            @DisplayName("because the size is not the same")
            void shouldReturnFalseWhenTheTwoObjectsAreNotEqualBecauseTheSizeIsNotTheSame() {
                assertThat(objectiveWithImprovement)
                        .isNotEqualTo(
                                new SingleGardenerObjective(
                                        TARGET_SIZE - 1, TARGET_COLOR, TARGET_IMPROVEMENT_TYPE, 0));
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
                                            TARGET_SIZE, TARGET_COLOR, TARGET_IMPROVEMENT_TYPE, 0)
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
                                                TARGET_IMPROVEMENT_TYPE,
                                                0)
                                        .hashCode());
            }

            @Test
            @DisplayName("because the improvement is not the same")
            void shouldReturnADifferentHashcodeWhenTheTwoObjectsAreNotEqual2() {
                assertThat(objectiveWithImprovement.hashCode())
                        .isNotEqualTo(
                                new SingleGardenerObjective(
                                                TARGET_SIZE, TARGET_COLOR, ImprovementType.NONE, 0)
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
                                                TARGET_IMPROVEMENT_TYPE,
                                                0)
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
            BotState botState = mock(BotState.class);
            assertThat(objectiveWithImprovement.getCompletion(board, botState)).isZero();
        }

        @Test
        @DisplayName("should return the completion percentage when the objective is started")
        void getCompletion_shouldReturnTheCompletionPercentageWhenTheObjectiveIsStarted() {
            // Variables
            Board board = mock(Board.class);
            BotState botState = mock(BotState.class);
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
            assertThat(
                            objectiveWithImprovement.getCompletion(board, botState)
                                    - ((float) 1 / TARGET_SIZE))
                    .isLessThan(0.0001f);
        }

        @Test
        @DisplayName("should return the completion percentage when the objective is completed")
        void getCompletion_shouldReturnTheCompletionPercentageWhenTheObjectiveIsCompleted() {
            // Variables
            Board board = mock(Board.class);
            BotState botState = mock(BotState.class);
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
            assertThat(objectiveWithImprovement.getCompletion(board, botState)).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("method verify")
    class TestVerify {

        private static Stream<Arguments> argumentForVerifyShouldSetTheObjectiveAsCompleted() {
            return Stream.of(
                    Arguments.of(
                            TARGET_SIZE,
                            TARGET_COLOR,
                            TARGET_IMPROVEMENT_TYPE,
                            "when the bamboo count is equal and the color and the improvement are"
                                    + " the same"),
                    Arguments.of(
                            TARGET_SIZE,
                            TARGET_COLOR,
                            ImprovementType.ANY,
                            "when the bamboo count is equal and the color is the same and the"
                                    + " improvement is ANY"),
                    Arguments.of(
                            TARGET_SIZE,
                            TileColor.ANY,
                            TARGET_IMPROVEMENT_TYPE,
                            "when the bamboo count is equal and the color is ANY and the"
                                    + " improvement is the same"));
        }

        @ParameterizedTest(name = "{3}")
        @DisplayName("should set the objective as completed")
        @MethodSource("argumentForVerifyShouldSetTheObjectiveAsCompleted")
        void verify_shouldReturnTrue(
                int inputSize,
                TileColor inputColor,
                ImprovementType inputImprovement,
                String message) {
            // Variables
            Board board = mock(Board.class);
            BotState botState = mock(BotState.class);
            PositionVector position = new PositionVector(1, -1, 0);
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            Tile tile = mock(Tile.class);
            tiles.put(position, tile);
            // Behavior
            when(tile.getColor()).thenReturn(inputColor);
            when(board.getTiles()).thenReturn(tiles);
            when(tile.getImprovement()).thenReturn(Optional.of(inputImprovement));
            when(board.getBambooAt(position)).thenReturn(new LayerBambooStack(inputSize));
            // Test
            objectiveWithImprovement.verify(board, botState);
            assertThat(objectiveWithImprovement.isAchieved()).isTrue();
        }

        private static Stream<Arguments> argumentForVerifyShouldSetTheObjectiveAsNotCompleted() {
            return Stream.of(
                    Arguments.of(
                            TARGET_SIZE - 1,
                            TARGET_COLOR,
                            TARGET_IMPROVEMENT_TYPE,
                            "when the bamboo count is not equal and the color and the improvement"
                                    + " are the same"),
                    Arguments.of(
                            TARGET_SIZE,
                            TileColor.NONE,
                            TARGET_IMPROVEMENT_TYPE,
                            "when the bamboo count is equal and the color is different and the"
                                    + " improvement is the same"),
                    Arguments.of(
                            TARGET_SIZE,
                            TARGET_COLOR,
                            ImprovementType.NONE,
                            "when the bamboo count is equal and the color is the same and the"
                                    + " improvement is different"));
        }

        @ParameterizedTest(name = "{3}")
        @DisplayName("should set the objective as not completed")
        @MethodSource("argumentForVerifyShouldSetTheObjectiveAsNotCompleted")
        void verify_shouldReturnFalse(
                int inputSize,
                TileColor inputColor,
                ImprovementType inputImprovement,
                String message) {
            // Variables
            Board board = mock(Board.class);
            BotState botState = mock(BotState.class);
            PositionVector position = new PositionVector(1, -1, 0);
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            Tile tile = mock(Tile.class);
            tiles.put(position, tile);
            // Behavior
            when(tile.getColor()).thenReturn(inputColor);
            when(board.getTiles()).thenReturn(tiles);
            when(tile.getImprovement()).thenReturn(Optional.of(inputImprovement));
            when(board.getBambooAt(position)).thenReturn(new LayerBambooStack(inputSize));
            // Test
            objectiveWithImprovement.verify(board, botState);
            assertThat(objectiveWithImprovement.isAchieved()).isFalse();
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
                    .containsExactly(position01, position04, position05);
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

    @Nested
    @DisplayName("method getActionsToComplete")
    class GetActionsToComplete {
        @Test
        @DisplayName("if no Panda && no Gardener movements are available returns null")
        void getActionsToComplete_ifNoPandaAndNoGardenerMovementsAreAvailable_returnsNull() {
            // Variables
            Board board = mock(Board.class);

            when(board.getPandaPossibleMoves()).thenReturn(List.of());
            when(board.getGardenerPossibleMoves()).thenReturn(List.of());

            // Test
            assertThat(objectiveWithImprovement.getActionsToComplete(board)).isEmpty();
        }

        @Test
        @DisplayName("if Panda && no Gardener movements are available returns null")
        void getActionsToComplete_ifPandaAndNoGardenerMovementsAreAvailable_returnsNull() {
            // Variables
            Board board = mock(Board.class);

            when(board.getPandaPossibleMoves()).thenReturn(List.of(new PositionVector(1, 0, -1)));
            when(board.getGardenerPossibleMoves()).thenReturn(List.of());

            // Test
            assertThat(objectiveWithImprovement.getActionsToComplete(board)).isEmpty();
        }

        @Test
        @Disabled
        @DisplayName("if Panda and Gardener movements are available returns the actions")
        void getActionsToComplete_ifPandaAndGardenerMovementsAreAvailable_returnsTheActions() {
            // Variables
            Board board = mock(Board.class);

            objectiveWithImprovement =
                    new SingleGardenerObjective(3, TileColor.PINK, ImprovementType.WATERSHED, 0);

            when(board.getPandaPossibleMoves()).thenReturn(List.of(new PositionVector(1, 0, -1)));
            // when(board.getGardenerPossibleMoves()).thenReturn(List.of(new PositionVector(1, 0,
            // -1)));
            when(board.getPandaPosition()).thenReturn(new PositionVector(0, 0, 0));
            when(board.getGardenerPosition()).thenReturn(new PositionVector(0, 0, 0));
            when(board.getTileAt(any()))
                    .thenReturn(new Tile(ImprovementType.WATERSHED, TileColor.PINK));
            when(board.getBambooAt(any())).thenReturn(new LayerBambooStack(4));
            when(board.isBambooGrowableAt(any())).thenReturn(true);
            when(board.isBambooEatableAt(any())).thenReturn(true);

            // Test
            assertThat(objectiveWithImprovement.getActionsToComplete(board))
                    .containsExactlyInAnyOrder(
                            // new MovePandaAction(new PositionVector(1, 0, -1)),
                            new MoveGardenerAction(new PositionVector(1, 0, -1)));
        }
    }
}
