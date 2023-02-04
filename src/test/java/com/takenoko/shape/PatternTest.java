package com.takenoko.shape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.layers.tile.Pond;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.layers.tile.TileType;
import com.takenoko.vector.PositionVector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.*;

class PatternTest {
    @Nested
    @DisplayName("Class constructor")
    class Constructor {
        @Test
        @DisplayName("Should throw an exception when the pattern is empty")
        void shouldThrowExceptionWhenPatternIsEmpty() {
            assertThatThrownBy(Pattern::new).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Method match")
    class TestMatch {
        Board board;

        @BeforeEach
        void setUp() {
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            tiles.put(new PositionVector(0, 0, 0), new Pond());
            tiles.put(new PositionVector(1, 0, -1), new Tile(TileColor.YELLOW));
            tiles.put(new PositionVector(1, -1, 0), new Tile(TileColor.PINK));
            tiles.put(new PositionVector(0, -1, 1), new Tile(TileColor.GREEN));
            tiles.put(new PositionVector(-1, 0, 1), new Tile(TileColor.GREEN));
            tiles.put(new PositionVector(1, -2, 1), new Tile(TileColor.PINK));
            board = mock(Board.class);
            when(board.getTiles()).thenReturn(tiles);
            for (PositionVector position : tiles.keySet()) {
                when(board.isTile(position)).thenReturn(true);
            }
            when(board.getTilesWithoutPond())
                    .thenReturn(
                            tiles.entrySet().stream()
                                    .filter(o -> o.getValue().getType() != TileType.POND)
                                    .collect(
                                            Collectors.toMap(
                                                    Map.Entry::getKey, Map.Entry::getValue)));
        }

        @AfterEach
        void tearDown() {
            board = null;
        }

        @Test
        @DisplayName("should return the patterns when the pattern matches a one tile pattern")
        void match_shouldReturnTrueWhenPatternMatchesSingleTilePattern() {
            PositionVector pos0 = new PositionVector(0, 0, 0);
            PositionVector pos1 = new PositionVector(1, 0, -1);
            PositionVector pos2 = new PositionVector(1, -1, 0);

            Pattern pattern = new Pattern(Pair.of(pos0, new Tile(TileColor.GREEN)));

            when(board.isIrrigatedAt(pos1)).thenReturn(true);
            when(board.isIrrigatedAt(pos2)).thenReturn(false);
            when(board.getTilesWithoutPond())
                    .thenReturn(
                            Map.of(
                                    pos1, new Tile(TileColor.GREEN),
                                    pos2, new Tile(TileColor.GREEN)));

            assertThat(pattern.match(board))
                    .containsExactlyInAnyOrder(new Shape(Pair.of(pos1, new Tile(TileColor.GREEN))));
        }

        @Test
        @DisplayName(
                "should return the patterns when the pattern matches a n tile pattern with a"
                        + " specific color")
        void match_shouldReturnTrueWhenPatternMatchesNTilePattern() {
            when(board.isIrrigatedAt(any())).thenReturn(true);

            Pattern pattern =
                    new Pattern(
                            Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.PINK)),
                            Pair.of(new PositionVector(1, 0, -1), new Tile(TileColor.GREEN)));

            ArrayList<Shape> expected = new ArrayList<>();
            expected.add(
                    new Shape(
                            Pair.of(new PositionVector(1, -1, 0), new Tile(TileColor.PINK)),
                            Pair.of(new PositionVector(0, -1, 1), new Tile(TileColor.GREEN))));

            expected.add(
                    new Shape(
                            Pair.of(new PositionVector(1, -2, 1), new Tile(TileColor.PINK)),
                            Pair.of(new PositionVector(0, -1, 1), new Tile(TileColor.GREEN))));

            assertThat(pattern.match(board)).isEqualTo(expected);
        }

        @Test
        @DisplayName("should return false when the pattern does not match the pattern")
        void match_shouldReturnFalseWhenPatternDoesNotMatchPattern() {
            Pattern pattern =
                    new Pattern(
                            Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.PINK)),
                            Pair.of(new PositionVector(1, 0, -1), new Tile(TileColor.GREEN)),
                            Pair.of(new PositionVector(1, -1, 0), new Tile(TileColor.GREEN)));
            assertThat(pattern.match(board)).isEmpty();
        }
    }

    @Nested
    @DisplayName("Test equals")
    class TestEquals {
        @Test
        @SuppressWarnings("EqualsWithItself")
        @DisplayName("should return true called on self")
        void equals_shouldReturnTrueWhenCalledOnSelf() {
            Pattern pattern =
                    new Pattern(Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.GREEN)));
            assertThat(pattern.equals(pattern)).isTrue();
        }

        @Test
        @DisplayName("should return false when called on another class")
        void equals_shouldReturnFalseWhenCalledOnAnotherClass() {
            Pattern pattern =
                    new Pattern(Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.GREEN)));
            assertThat(pattern.equals(new Object())).isFalse();
        }

        @Test
        @DisplayName("should return true when the patterns are equal")
        void equals_shouldReturnTrueWhenPatternsAreEqual() {
            Pattern pattern1 = PatternFactory.LINE.createPattern(TileColor.ANY);
            Pattern pattern2 = PatternFactory.LINE.createPattern(TileColor.ANY);
            assertThat(pattern1.equals(pattern2)).isTrue();
        }

        @Test
        @DisplayName("should return false when the patterns are not equal")
        void equals_shouldReturnFalseWhenPatternsAreNotEqual() {
            Pattern pattern1 = PatternFactory.DIAMOND.createPattern(TileColor.ANY);
            Pattern pattern2 = PatternFactory.LINE.createPattern(TileColor.ANY);
            assertThat(pattern1.equals(pattern2)).isFalse();
        }
    }

    @Nested
    @DisplayName("Test hashCode")
    class TestHashCode {
        @Test
        @DisplayName("should return the same hashcode when the patterns are equal")
        void hashCode_shouldReturnSameHashCodeWhenPatternsAreEqual() {
            Pattern pattern1 = PatternFactory.LINE.createPattern(TileColor.ANY);
            Pattern pattern2 = PatternFactory.LINE.createPattern(TileColor.ANY);
            assertThat(pattern1).hasSameHashCodeAs(pattern2);
        }

        @Test
        @DisplayName("should return different hashcode when the patterns are not equal")
        void hashCode_shouldReturnDifferentHashCodeWhenPatternsAreNotEqual() {
            Pattern pattern1 = PatternFactory.DIAMOND.createPattern(TileColor.ANY);

            Pattern pattern2 = PatternFactory.LINE.createPattern(TileColor.ANY);
            assertThat(pattern1.hashCode()).isNotEqualTo(pattern2.hashCode());
        }
    }

    @Test
    @DisplayName("test pattern matching with a curve")
    void testPatternMatching() {
        HashMap<PositionVector, Tile> tiles = new HashMap<>();
        tiles.put(new PositionVector(2, 1, -3), new Tile(TileColor.GREEN));
        tiles.put(new PositionVector(3, -1, -2), new Tile(TileColor.YELLOW));
        tiles.put(new PositionVector(3, 0, -3), new Tile(TileColor.YELLOW));
        tiles.put(new PositionVector(1, 1, -2), new Tile(TileColor.GREEN));
        tiles.put(new PositionVector(-1, 1, 0), new Tile(TileColor.PINK));
        tiles.put(new PositionVector(0, 0, 0), new Tile(TileColor.NONE));
        tiles.put(new PositionVector(2, 0, -2), new Tile(TileColor.GREEN));
        tiles.put(new PositionVector(2, -1, -1), new Tile(TileColor.YELLOW));
        tiles.put(new PositionVector(-3, 3, 0), new Tile(TileColor.GREEN));
        tiles.put(new PositionVector(1, -1, 0), new Tile(TileColor.YELLOW));
        tiles.put(new PositionVector(0, 1, -1), new Tile(TileColor.PINK));
        tiles.put(new PositionVector(0, -1, 1), new Tile(TileColor.PINK));
        tiles.put(new PositionVector(-2, 2, 0), new Tile(TileColor.GREEN));
        tiles.put(new PositionVector(0, -2, 2), new Tile(TileColor.YELLOW));
        tiles.put(new PositionVector(2, -2, 0), new Tile(TileColor.GREEN));
        tiles.put(new PositionVector(0, 2, -2), new Tile(TileColor.PINK));
        tiles.put(new PositionVector(0, 3, -3), new Tile(TileColor.YELLOW));
        tiles.put(new PositionVector(-3, 2, 1), new Tile(TileColor.GREEN));
        tiles.put(new PositionVector(-4, 3, 1), new Tile(TileColor.PINK));
        tiles.put(new PositionVector(-2, 3, -1), new Tile(TileColor.YELLOW));
        tiles.put(new PositionVector(-1, 3, -2), new Tile(TileColor.PINK));
        tiles.put(new PositionVector(-3, 4, -1), new Tile(TileColor.GREEN));
        tiles.put(new PositionVector(-1, 4, -3), new Tile(TileColor.GREEN));
        tiles.put(new PositionVector(4, -1, -3), new Tile(TileColor.PINK));
        tiles.put(new PositionVector(-1, 2, -1), new Tile(TileColor.GREEN));
        tiles.put(new PositionVector(1, 0, -1), new Tile(TileColor.YELLOW));
        tiles.put(new PositionVector(-1, 0, 1), new Tile(TileColor.YELLOW));
        tiles.put(new PositionVector(1, -2, 1), new Tile(TileColor.GREEN));
        Board board = mock(Board.class);
        when(board.isIrrigatedAt(any())).thenReturn(true);
        when(board.getTiles()).thenReturn(tiles);
        for (PositionVector position : tiles.keySet()) {
            when(board.isTile(position)).thenReturn(true);
        }
        when(board.getTilesWithoutPond())
                .thenReturn(
                        tiles.entrySet().stream()
                                .filter(o -> o.getValue().getType() != TileType.POND)
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        Triangle yellowTriangle = new Triangle(TileColor.YELLOW);
        assertThat(yellowTriangle.match(board)).hasSize(1);

        Triangle greenTriangle = new Triangle(TileColor.GREEN);
        assertThat(greenTriangle.match(board)).hasSize(2);

        Line greenLine = new Line(TileColor.GREEN);
        assertThat(greenLine.match(board)).hasSize(2);

        Curve greenCurve = new Curve(TileColor.GREEN);
        assertThat(greenCurve.match(board)).hasSize(2);

        Curve pinkCurve = new Curve(TileColor.PINK);
        assertThat(pinkCurve.match(board)).hasSize(2);
    }
}
