package com.takenoko.shape;

import static org.assertj.core.api.Assertions.assertThat;

import com.takenoko.Board;
import com.takenoko.vector.Vector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.*;

public class ShapeTest {

    private Shape shape;

    @BeforeEach
    void setUp() {
        HashSet<Vector> pattern = new HashSet<>();
        pattern.add(new Vector(0, 0, 0));
        pattern.add(new Vector(1, 0, -1));
        pattern.add(new Vector(1, -1, 0));

        this.shape = new Shape(pattern);
    }

    @AfterEach
    void tearDown() {
        shape = null;
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
            assertThat(shape.rotate60().getPattern()).contains(new Vector(0, 0, 0));
        }

        @Test
        @DisplayName("should return a new shape with the tiles rotated 60 degrees")
        void rotate60_shouldReturnNewShapeWithTilesRotated60Degrees() {
            assertThat(shape.rotate60().getPattern()).contains(new Vector(0, 0, 0));
            assertThat(shape.rotate60().getPattern()).contains(new Vector(0, 1, -1));
            assertThat(shape.rotate60().getPattern()).contains(new Vector(1, 0, -1));
        }
    }

    @Nested
    @DisplayName("Method translate")
    class TestTranslate {
        @Test
        @DisplayName("should return a new shape with the same size")
        void translate_shouldReturnNewShapeWithSameSize() {
            assertThat(shape.translate(new Vector(1, 1, -2)).getPattern())
                    .hasSize(shape.getPattern().size());
        }

        @Test
        @DisplayName("should return a new shape with the same origin")
        void translate_shouldReturnNewShapeWithSameOrigin() {
            assertThat(shape.translate(new Vector(1, 1, -2)).getPattern())
                    .contains(new Vector(1, 1, -2));
        }

        @Test
        @DisplayName("should return a new shape with the tiles translated")
        void translate_shouldReturnNewShapeWithTilesTranslated() {
            assertThat(shape.translate(new Vector(1, 1, -2)).getPattern())
                    .contains(new Vector(1, 1, -2));
            assertThat(shape.translate(new Vector(1, 1, -2)).getPattern())
                    .contains(new Vector(2, 1, -3));
            assertThat(shape.translate(new Vector(1, 1, -2)).getPattern())
                    .contains(new Vector(2, 0, -2));
        }
    }

    @Nested
    @DisplayName("Method match")
    class TestMatch {
        Board board;

        @BeforeEach
        void setUp() {
            board = new Board();
            board.placeTile(board.getAvailableTiles().get(0), new Vector(1, 0, -1));
        }

        @AfterEach
        void tearDown() {
            board = null;
        }

        @Test
        @DisplayName("should return the shapes when the shape matches a one tile pattern")
        void match_shouldReturnTrueWhenShapeMatchesSingleTilePattern() {
            Shape shape = new Shape(new HashSet<>(List.of(new Vector(0, 0, 0))));

            ArrayList<Shape> expected = new ArrayList<>();
            expected.add(new Shape(new HashSet<>(List.of(new Vector(0, 0, 0)))));
            expected.add(new Shape(new HashSet<>(List.of(new Vector(1, 0, -1)))));

            assertThat(shape.match(board)).isEqualTo(expected);
        }

        @Test
        @DisplayName("should return the shapes when the shape matches a n tile pattern")
        void match_shouldReturnTrueWhenShapeMatchesNTilePattern() {
            Shape shape =
                    new Shape(new HashSet<>(List.of(new Vector(0, 0, 0), new Vector(1, 0, -1))));

            ArrayList<Shape> expected = new ArrayList<>();
            expected.add(
                    new Shape(
                            new HashSet<>(
                                    new ArrayList<>(
                                            List.of(new Vector(0, 0, 0), new Vector(1, 0, -1))))));

            assertThat(shape.match(board)).isEqualTo(expected);
        }

        @Test
        @DisplayName("should return the shapes when the shape matches a rotated n tile pattern")
        void match_shouldReturnTrueWhenShapeMatchesRotatedNTilePattern() {
            Shape shape =
                    new Shape(new HashSet<>(List.of(new Vector(0, 0, 0), new Vector(0, 1, -1))));

            ArrayList<Shape> expected = new ArrayList<>();
            expected.add(
                    new Shape(new HashSet<>(List.of(new Vector(0, 0, 0), new Vector(1, 0, -1)))));

            assertThat(shape.match(board)).isEqualTo(expected);
        }

        @Test
        @DisplayName("should return false when the shape does not match the pattern")
        void match_shouldReturnFalseWhenShapeDoesNotMatchPattern() {
            ArrayList<Vector> pattern = new ArrayList<>();
            pattern.add(new Vector(0, 0, 0));
            pattern.add(new Vector(1, 0, -1));
            pattern.add(new Vector(2, 0, -2));
            Shape shape = new Shape(new HashSet<>(pattern));
            assertThat(shape.match(board)).isEmpty();
        }
    }
}
