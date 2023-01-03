package com.takenoko.shape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.engine.Board;
import com.takenoko.layers.tile.Pond;
import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.junit.jupiter.api.*;

public class ShapeTest {

    private Shape shape;

    @BeforeEach
    void setUp() {
        this.shape =
                new Shape(
                        new PositionVector(0, 0, 0),
                        new PositionVector(1, 0, -1),
                        new PositionVector(1, -1, 0));
    }

    @AfterEach
    void tearDown() {
        shape = null;
    }

    @Nested
    @DisplayName("parameterized constructor")
    class TestParameterizedConstructor {
        @Test
        @DisplayName("should throw an exception when the pattern is empty")
        void parameterizedConstructor_shouldThrowExceptionWhenPatternIsEmpty() {
            HashSet<PositionVector> pattern = new HashSet<>();
            PositionVector origin = new PositionVector(0, 0, 0);
            assertThatThrownBy(() -> new Shape(pattern, origin))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The shape cannot be empty");
        }
    }

    @Nested
    @DisplayName("Method rotate60")
    class TestRotate60 {
        @Test
        @DisplayName("should return a new shape with the same size")
        void rotate60_shouldReturnNewShapeWithSameSize() {
            assertThat(shape.rotate60().getPattern()).hasSize(shape.getPattern().size());
        }

        @Test
        @DisplayName("should return a new shape with the same origin")
        void rotate60_shouldReturnNewShapeWithSameOrigin() {
            assertThat(shape.rotate60().getPattern()).contains(new PositionVector(0, 0, 0));
        }

        @Test
        @DisplayName("should return a new shape with the tiles rotated 60 degrees")
        void rotate60_shouldReturnNewShapeWithTilesRotated60Degrees() {
            assertThat(shape.rotate60().getPattern()).contains(new PositionVector(0, 0, 0));
            assertThat(shape.rotate60().getPattern()).contains(new PositionVector(0, 1, -1));
            assertThat(shape.rotate60().getPattern()).contains(new PositionVector(1, 0, -1));
        }
    }

    @Nested
    @DisplayName("Method translate")
    class TestTranslate {
        @Test
        @DisplayName("should return a new shape with the same size")
        void translate_shouldReturnNewShapeWithSameSize() {
            assertThat(shape.translate(new PositionVector(1, 1, -2)).getPattern())
                    .hasSize(shape.getPattern().size());
        }

        @Test
        @DisplayName("should return a new shape with the same origin")
        void translate_shouldReturnNewShapeWithSameOrigin() {
            assertThat(shape.translate(new PositionVector(1, 1, -2)).getPattern())
                    .contains(new PositionVector(1, 1, -2));
        }

        @Test
        @DisplayName("should return a new shape with the tiles translated")
        void translate_shouldReturnNewShapeWithTilesTranslated() {
            assertThat(shape.translate(new PositionVector(1, 1, -2)).getPattern())
                    .contains(new PositionVector(1, 1, -2));
            assertThat(shape.translate(new PositionVector(1, 1, -2)).getPattern())
                    .contains(new PositionVector(2, 1, -3));
            assertThat(shape.translate(new PositionVector(1, 1, -2)).getPattern())
                    .contains(new PositionVector(2, 0, -2));
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
            tiles.put(new PositionVector(1, 0, -1), new Tile());
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
        @DisplayName("should return the shapes when the shape matches a one tile pattern")
        void match_shouldReturnTrueWhenShapeMatchesSingleTilePattern() {
            Shape shape = new Shape(new PositionVector(0, 0, 0));

            ArrayList<Shape> expected = new ArrayList<>();
            expected.add(new Shape(new PositionVector(0, 0, 0)));
            expected.add(new Shape(new PositionVector(1, 0, -1)));

            assertThat(shape.match(board.getTiles())).isEqualTo(expected);
        }

        @Test
        @DisplayName("should return the shapes when the shape matches a n tile pattern")
        void match_shouldReturnTrueWhenShapeMatchesNTilePattern() {
            Shape shape = new Shape(new PositionVector(0, 0, 0), new PositionVector(1, 0, -1));

            ArrayList<Shape> expected = new ArrayList<>();
            expected.add(new Shape(new PositionVector(0, 0, 0), new PositionVector(1, 0, -1)));

            assertThat(shape.match(board.getTiles())).isEqualTo(expected);
        }

        @Test
        @DisplayName("should return the shapes when the shape matches a rotated n tile pattern")
        void match_shouldReturnTrueWhenShapeMatchesRotatedNTilePattern() {
            Shape shape = new Shape(new PositionVector(0, 0, 0), new PositionVector(0, 1, -1));

            ArrayList<Shape> expected = new ArrayList<>();
            expected.add(new Shape(new PositionVector(0, 0, 0), new PositionVector(1, 0, -1)));

            assertThat(shape.match(board.getTiles())).isEqualTo(expected);
        }

        @Test
        @DisplayName("should return false when the shape does not match the pattern")
        void match_shouldReturnFalseWhenShapeDoesNotMatchPattern() {
            Shape shape =
                    new Shape(
                            new PositionVector(0, 0, 0),
                            new PositionVector(1, 0, -1),
                            new PositionVector(2, 0, -2));
            assertThat(shape.match(board.getTiles())).isEmpty();
        }
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {
        @Test
        @DisplayName("should return true when the shape is itself")
        @SuppressWarnings("EqualsWithItself")
        void equals_shouldReturnTrueWhenShapeIsItself() {
            assertThat(shape.equals(shape)).isTrue();
        }

        @Test
        @DisplayName("should return false when the shape is null")
        @SuppressWarnings("ConstantConditions")
        void equals_shouldReturnFalseWhenShapeIsNull() {
            assertThat(shape.equals(null)).isFalse();
        }

        @Test
        @DisplayName("should return false when the shape is of another class")
        void equals_shouldReturnFalseWhenShapeIsOfAnotherClass() {
            assertThat(shape.equals(new Object())).isFalse();
        }

        @Test
        @DisplayName("should return true when the shape has the same pattern")
        void equals_shouldReturnTrueWhenShapeHasSamePattern() {
            Shape otherShape =
                    new Shape(
                            new PositionVector(0, 0, 0),
                            new PositionVector(1, 0, -1),
                            new PositionVector(1, -1, 0));
            assertThat(shape.equals(otherShape)).isTrue();
        }

        @Test
        @DisplayName("should return false when the shape has a different pattern")
        void equals_shouldReturnFalseWhenShapeHasDifferentPattern() {
            Shape otherShape =
                    new Shape(
                            new PositionVector(0, 0, 0),
                            new PositionVector(1, 0, -1),
                            new PositionVector(2, 0, -2));
            assertThat(shape.equals(otherShape)).isFalse();
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("should return the same hash code when the shape has the same pattern")
        void hashCode_shouldReturnSameHashCodeWhenShapeHasSamePattern() {
            Shape otherShape =
                    new Shape(
                            new PositionVector(0, 0, 0),
                            new PositionVector(1, 0, -1),
                            new PositionVector(1, -1, 0));
            assertThat(shape).hasSameHashCodeAs(otherShape.hashCode());
        }

        @Test
        @DisplayName("should return a different hash code when the shape has a different pattern")
        void hashCode_shouldReturnDifferentHashCodeWhenShapeHasDifferentPattern() {
            Shape otherShape =
                    new Shape(
                            new PositionVector(0, 0, 0),
                            new PositionVector(1, 0, -1),
                            new PositionVector(2, 0, -2));
            assertThat(shape).doesNotHaveSameHashCodeAs(otherShape.hashCode());
        }
    }

    @Nested
    @DisplayName("Method toString")
    class TestToString {
        @Test
        @DisplayName("should return a string representation of the shape")
        void toString_shouldReturnStringRepresentationOfShape() {
            assertThat(shape.toString())
                    .hasToString(
                            "Shape{pattern=[Vector[q=0.0, r=0.0, s=0.0], Vector[q=1.0, r=0.0,"
                                + " s=-1.0], Vector[q=1.0, r=-1.0, s=0.0]], origin=Vector[q=0.0,"
                                + " r=0.0, s=0.0]}");
        }
    }
}
