package com.takenoko.objective;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ColoredBambooSizeObjectiveTest {
    private ColoredBambooSizeObjective coloredBambooSizeObjective;
    private static final TileColor TILE_COLOR = TileColor.GREEN;
    private static final int BAMBOO_SIZE = 3;

    @BeforeEach
    void setUp() {

        coloredBambooSizeObjective = new ColoredBambooSizeObjective(BAMBOO_SIZE, TILE_COLOR);
    }

    @Nested
    @DisplayName("method equals")
    class TestEquals {
        @Test
        @DisplayName("should return true when the two objects are the same")
        void equals_shouldReturnTrueWhenSameObject() {
            assertThat(coloredBambooSizeObjective).isEqualTo(coloredBambooSizeObjective);
        }

        @Test
        @DisplayName("should return true when the two objects are equal")
        void equals_shouldReturnTrueWhenEqual() {
            ColoredBambooSizeObjective other =
                    new ColoredBambooSizeObjective(BAMBOO_SIZE, TILE_COLOR);
            assertThat(coloredBambooSizeObjective).isEqualTo(other);
        }

        @Nested
        @DisplayName("should return false when the two objects are not equal")
        class TestNotEqual {
            @Test
            @DisplayName("when the bamboo size is not equal")
            void equals_shouldReturnFalseWhenBambooSizeNotEqual() {
                ColoredBambooSizeObjective other =
                        new ColoredBambooSizeObjective(BAMBOO_SIZE + 1, TILE_COLOR);
                assertThat(coloredBambooSizeObjective).isNotEqualTo(other);
            }

            @Test
            @DisplayName("when the tile color is not equal")
            void equals_shouldReturnFalseWhenTileColorNotEqual() {
                ColoredBambooSizeObjective other =
                        new ColoredBambooSizeObjective(BAMBOO_SIZE, TileColor.PINK);
                assertThat(coloredBambooSizeObjective).isNotEqualTo(other);
            }

            @Test
            @DisplayName("when the tile color and the bamboo size are not equal")
            void equals_shouldReturnFalseWhenTileColorAndBambooSizeNotEqual() {
                ColoredBambooSizeObjective other =
                        new ColoredBambooSizeObjective(BAMBOO_SIZE + 1, TileColor.PINK);
                assertThat(coloredBambooSizeObjective).isNotEqualTo(other);
            }
        }

        @Test
        @DisplayName("should return false when the other object is null")
        void equals_shouldReturnFalseWhenOtherIsNull() {
            assertThat(coloredBambooSizeObjective).isNotEqualTo(null);
        }

        @Test
        @DisplayName(
                "should return false when the other object is not a ColoredBambooSizeObjective")
        void equals_shouldReturnFalseWhenOtherIsNotColoredBambooSizeObjective() {
            assertThat(coloredBambooSizeObjective).isNotEqualTo(new Object());
        }
    }

    @Nested
    @DisplayName("method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("should return the same hash code when the two objects are the same")
        void hashCode_shouldReturnSameHashCodeWhenSameObject() {
            assertThat(coloredBambooSizeObjective).hasSameHashCodeAs(coloredBambooSizeObjective);
        }

        @Test
        @DisplayName("should return the same hash code when the two objects are equal")
        void hashCode_shouldReturnSameHashCodeWhenEqual() {
            ColoredBambooSizeObjective other =
                    new ColoredBambooSizeObjective(BAMBOO_SIZE, TILE_COLOR);
            assertThat(coloredBambooSizeObjective).hasSameHashCodeAs(other);
        }

        @Nested
        @DisplayName("should return a different hash code when the two objects are not equal")
        class TestNotEqual {
            @Test
            @DisplayName("when the bamboo size is not equal")
            void hashCode_shouldReturnDifferentHashCodeWhenBambooSizeNotEqual() {
                ColoredBambooSizeObjective other =
                        new ColoredBambooSizeObjective(BAMBOO_SIZE + 1, TILE_COLOR);
                assertThat(coloredBambooSizeObjective).doesNotHaveSameHashCodeAs(other);
            }

            @Test
            @DisplayName("when the tile color is not equal")
            void hashCode_shouldReturnDifferentHashCodeWhenTileColorNotEqual() {
                ColoredBambooSizeObjective other =
                        new ColoredBambooSizeObjective(BAMBOO_SIZE, TileColor.PINK);
                assertThat(coloredBambooSizeObjective).doesNotHaveSameHashCodeAs(other);
            }

            @Test
            @DisplayName("when the tile color and the bamboo size are not equal")
            void hashCode_shouldReturnDifferentHashCodeWhenTileColorAndBambooSizeNotEqual() {
                ColoredBambooSizeObjective other =
                        new ColoredBambooSizeObjective(BAMBOO_SIZE + 1, TileColor.PINK);
                assertThat(coloredBambooSizeObjective).doesNotHaveSameHashCodeAs(other);
            }
        }
    }

    @Nested
    @DisplayName("method copy")
    class TestCopy {
        @Test
        @DisplayName("should return a copy of the objective")
        void copy_shouldReturnCopyOfObjective() {
            ColoredBambooSizeObjective copy = coloredBambooSizeObjective.copy();
            assertThat(copy).isEqualTo(coloredBambooSizeObjective);
            assertThat(copy).isNotSameAs(coloredBambooSizeObjective);
        }
    }

    @Nested
    @DisplayName("method getCompletion")
    class TestGetCompletion {
        @Test
        @DisplayName("should return the completion percentage when the objective not started")
        void getCompletion_shouldReturnCompletionPercentage() {
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);

            assertThat(coloredBambooSizeObjective.getCompletion(board, botManager)).isZero();
        }

        @Test
        @DisplayName("should return the completion percentage when the objective is started")
        void getCompletion_shouldReturnCompletionPercentageWhenStarted() {
            coloredBambooSizeObjective = new ColoredBambooSizeObjective(4, TILE_COLOR);
            PositionVector position = new PositionVector(1, -1, 0);
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            Tile tile = mock(Tile.class);
            when(tile.getColor()).thenReturn(TILE_COLOR);
            tiles.put(position, tile);
            when(board.getTiles()).thenReturn(tiles);
            when(board.getBambooAt(position)).thenReturn(new LayerBambooStack(2));

            assertThat(coloredBambooSizeObjective.getCompletion(board, botManager)).isEqualTo(0.5f);
        }

        @Test
        @DisplayName("should return the completion percentage when the objective is completed")
        void getCompletion_shouldReturnCompletionPercentageWhenCompleted() {
            PositionVector position = new PositionVector(1, -1, 0);
            Board board = mock(Board.class);
            BotManager botManager = mock(BotManager.class);
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            Tile tile = mock(Tile.class);
            when(tile.getColor()).thenReturn(TILE_COLOR);
            tiles.put(position, tile);
            when(board.getTiles()).thenReturn(tiles);
            when(board.getBambooAt(position)).thenReturn(new LayerBambooStack(BAMBOO_SIZE));

            assertThat(coloredBambooSizeObjective.getCompletion(board, botManager)).isEqualTo(1.0f);
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
                    "when the bamboo count is equal to the objective and the color is the same")
            void verify_shouldReturnTrueWhenBambooCountEqualToObjectiveAndTheColorIsTheSame() {
                PositionVector position = new PositionVector(1, -1, 0);
                Board board = mock(Board.class);
                BotManager botManager = mock(BotManager.class);
                HashMap<PositionVector, Tile> tiles = new HashMap<>();
                Tile tile = mock(Tile.class);
                when(tile.getColor()).thenReturn(TILE_COLOR);
                tiles.put(position, tile);
                when(board.getTiles()).thenReturn(tiles);
                when(board.getBambooAt(position)).thenReturn(new LayerBambooStack(BAMBOO_SIZE));
                coloredBambooSizeObjective.verify(board, botManager);
                assertThat(coloredBambooSizeObjective.isAchieved()).isTrue();
            }

            @Test
            @DisplayName(
                    "when the bamboo count is greater than the objective and the color is the same")
            void verify_shouldReturnTrueWhenBambooCountGreaterThanObjectiveAndTheColorIsTheSame() {
                PositionVector position = new PositionVector(1, -1, 0);
                Board board = mock(Board.class);
                BotManager botManager = mock(BotManager.class);
                HashMap<PositionVector, Tile> tiles = new HashMap<>();
                Tile tile = mock(Tile.class);
                when(tile.getColor()).thenReturn(TILE_COLOR);
                tiles.put(position, tile);
                when(board.getTiles()).thenReturn(tiles);
                when(board.getBambooAt(position)).thenReturn(new LayerBambooStack(BAMBOO_SIZE + 1));
                coloredBambooSizeObjective.verify(board, botManager);
                assertThat(coloredBambooSizeObjective.isAchieved()).isTrue();
            }
        }

        @Nested
        @DisplayName("should return false")
        class VerifyShouldReturnFalse {
            @Test
            @DisplayName("when the bamboo count is less than the objective")
            void verify_shouldReturnFalseWhenBambooCountLessThanObjective() {
                Board board = mock(Board.class);
                HashMap<PositionVector, Tile> tiles = new HashMap<>();
                Tile tile = mock(Tile.class);
                when(tile.getColor()).thenReturn(TILE_COLOR);
                tiles.put(new PositionVector(1, -1, 0), tile);
                when(board.getTiles()).thenReturn(tiles);
                when(board.getBambooAt(new PositionVector(1, -1, 0)))
                        .thenReturn(new LayerBambooStack(BAMBOO_SIZE - 1));
                coloredBambooSizeObjective.verify(board, mock(BotManager.class));
                assertThat(coloredBambooSizeObjective.isAchieved()).isFalse();
            }

            @Test
            @DisplayName(
                    "when the bamboo count is equal to the objective but the tile color is not"
                            + " equal")
            void verify_shouldReturnFalseWhenBambooCountEqualToObjectiveButTileColorNotEqual() {
                Board board = mock(Board.class);
                HashMap<PositionVector, Tile> tiles = new HashMap<>();
                Tile tile = mock(Tile.class);
                when(tile.getColor()).thenReturn(TileColor.PINK);
                tiles.put(new PositionVector(1, -1, 0), tile);
                when(board.getTiles()).thenReturn(tiles);
                when(board.getBambooAt(new PositionVector(1, -1, 0)))
                        .thenReturn(new LayerBambooStack(BAMBOO_SIZE));
                coloredBambooSizeObjective.verify(board, mock(BotManager.class));
                assertThat(coloredBambooSizeObjective.isAchieved()).isFalse();
            }
        }
    }
}
