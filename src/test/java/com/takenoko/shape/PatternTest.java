package com.takenoko.shape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.engine.Board;
import com.takenoko.layers.tile.Pond;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;
import java.util.ArrayList;
import java.util.HashMap;
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
        }

        @AfterEach
        void tearDown() {
            board = null;
        }

        @Test
        @DisplayName("should return the patterns when the pattern matches a one tile pattern")
        void match_shouldReturnTrueWhenPatternMatchesSingleTilePattern() {
            Pattern pattern =
                    new Pattern(Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.GREEN)));
            ArrayList<Shape> expected = new ArrayList<>();
            expected.add(
                    new Shape(Pair.of(new PositionVector(0, -1, 1), new Tile(TileColor.GREEN))));
            expected.add(
                    new Shape(Pair.of(new PositionVector(-1, 0, 1), new Tile(TileColor.GREEN))));

            assertThat(pattern.match(board.getTiles())).isEqualTo(expected);
        }

        @Test
        @DisplayName(
                "should return the patterns when the pattern matches a n tile pattern with a"
                        + " specific color")
        void match_shouldReturnTrueWhenPatternMatchesNTilePattern() {
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

            assertThat(pattern.match(board.getTiles())).isEqualTo(expected);
        }

        @Test
        @DisplayName("should return false when the pattern does not match the pattern")
        void match_shouldReturnFalseWhenPatternDoesNotMatchPattern() {
            Pattern pattern =
                    new Pattern(
                            Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.PINK)),
                            Pair.of(new PositionVector(1, 0, -1), new Tile(TileColor.GREEN)),
                            Pair.of(new PositionVector(1, -1, 0), new Tile(TileColor.GREEN)));
            assertThat(pattern.match(board.getTiles())).isEmpty();
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
            Pattern pattern1 = PatternFactory.LINE.createPattern();
            Pattern pattern2 = PatternFactory.LINE.createPattern();
            assertThat(pattern1.equals(pattern2)).isTrue();
        }

        @Test
        @DisplayName("should return false when the patterns are not equal")
        void equals_shouldReturnFalseWhenPatternsAreNotEqual() {
            Pattern pattern1 = PatternFactory.DIAMOND.createPattern();
            Pattern pattern2 = PatternFactory.LINE.createPattern();
            assertThat(pattern1.equals(pattern2)).isFalse();
        }
    }

    @Nested
    @DisplayName("Test hashCode")
    class TestHashCode {
        @Test
        @DisplayName("should return the same hashcode when the patterns are equal")
        void hashCode_shouldReturnSameHashCodeWhenPatternsAreEqual() {
            Pattern pattern1 = PatternFactory.LINE.createPattern();
            Pattern pattern2 = PatternFactory.LINE.createPattern();
            assertThat(pattern1).hasSameHashCodeAs(pattern2);
        }

        @Test
        @DisplayName("should return different hashcode when the patterns are not equal")
        void hashCode_shouldReturnDifferentHashCodeWhenPatternsAreNotEqual() {
            Pattern pattern1 = PatternFactory.DIAMOND.createPattern();

            Pattern pattern2 = PatternFactory.LINE.createPattern();
            assertThat(pattern1.hashCode()).isNotEqualTo(pattern2.hashCode());
        }
    }
    //
    //    @Nested
    //    @DisplayName("Test matchRatio")
    //    class TestMatchRatio {
    //
    //        @Test
    //        @DisplayName("should return 1 when the pattern matches a one tile pattern")
    //        void matchRatio_shouldReturnOneWhenPatternMatchesSingleTilePattern() {
    //            Pattern pattern = new Pattern(new PositionVector(0, 0, 0));
    //            HashMap<PositionVector, Tile> tiles = new HashMap<>();
    //            tiles.put(new PositionVector(0, 0, 0), new Pond());
    //            assertThat(pattern.matchRatio(tiles)).isEqualTo(1);
    //        }
    //
    //        @Test
    //        @DisplayName("should return 1 when the pattern matches a n tile pattern")
    //        void matchRatio_shouldReturnOneWhenPatternMatchesNTilePattern() {
    //            Pattern pattern =
    //                    new Pattern(new PositionVector(0, 0, 0), new PositionVector(1, 0, -1));
    //            HashMap<PositionVector, Tile> tiles = new HashMap<>();
    //            tiles.put(new PositionVector(0, 0, 0), new Pond());
    //            tiles.put(new PositionVector(1, 0, -1), new Tile());
    //            assertThat(pattern.matchRatio(tiles)).isEqualTo(1);
    //        }
    //
    //        @Test
    //        @DisplayName("should return 0.5 when the pattern matches half of a n tile pattern")
    //        void matchRatio_shouldReturnOneWhenPatternMatchesRotatedNTilePattern() {
    //            Pattern pattern =
    //                    new Pattern(new PositionVector(0, 0, 0), new PositionVector(0, 1, -1));
    //            HashMap<PositionVector, Tile> tiles = new HashMap<>();
    //            tiles.put(new PositionVector(0, 0, 0), new Pond());
    //            assertThat(pattern.matchRatio(tiles)).isEqualTo(0.5f);
    //        }
    //
    //        @Test
    //        @DisplayName("should return 0 when the pattern does not match the pattern")
    //        void matchRatio_shouldReturnZeroWhenPatternDoesNotMatchPattern() {
    //            Pattern pattern =
    //                    new Pattern(
    //                            new PositionVector(0, 0, 0),
    //                            new PositionVector(1, 0, -1),
    //                            new PositionVector(2, 0, -2));
    //            assertThat(pattern.matchRatio(new HashMap<>())).isZero();
    //        }
    //    }
}
