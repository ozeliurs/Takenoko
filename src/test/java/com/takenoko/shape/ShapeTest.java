package com.takenoko.shape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.*;

public class ShapeTest {

    private Shape shape;

    @BeforeEach
    void setUp() {

        this.shape =
                new Shape(
                        Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.YELLOW)),
                        Pair.of(new PositionVector(1, 0, -1), new Tile(TileColor.GREEN)),
                        Pair.of(new PositionVector(1, -1, 0), new Tile(TileColor.PINK)));
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
            HashMap<PositionVector, Tile> pattern = new HashMap<>();
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
            assertThat(shape.rotate60().getElements()).hasSize(shape.getElements().size());
        }

        @Test
        @DisplayName("should return a new shape with the same origin")
        void rotate60_shouldReturnNewShapeWithSameOrigin() {
            assertThat(shape.rotate60().getElements())
                    .containsEntry(new PositionVector(0, 0, 0), new Tile(TileColor.YELLOW));
        }

        @Test
        @DisplayName("should return a new shape with the tiles rotated 60 degrees")
        void rotate60_shouldReturnNewShapeWithTilesRotated60Degrees() {
            assertThat(shape.rotate60().getElements())
                    .containsEntry(new PositionVector(0, 1, -1), new Tile(TileColor.GREEN))
                    .containsEntry(new PositionVector(1, 0, -1), new Tile(TileColor.PINK))
                    .containsEntry(new PositionVector(0, 0, 0), new Tile(TileColor.YELLOW));
        }
    }

    @Nested
    @DisplayName("Method getRotatedShape")
    class TestGetRotatedShape {
        @Test
        @DisplayName("should return a new shape with the same size")
        void getRotatedShape_shouldReturnNewShapeWithSameSize() {
            assertThat(shape.getRotatedShape(0).getElements()).hasSize(shape.getElements().size());
        }

        @Test
        @DisplayName("should return a new shape with the same origin")
        void getRotatedShape_shouldReturnNewShapeWithSameOrigin() {
            assertThat(shape.getRotatedShape(0).getElements())
                    .containsEntry(new PositionVector(0, 0, 0), new Tile(TileColor.YELLOW));
        }

        @Test
        @DisplayName("should return a new shape with the tiles rotated 60 degrees")
        void getRotatedShape_shouldReturnNewShapeWithTilesRotated60Degrees() {
            assertThat(shape.getRotatedShape(1)).isEqualTo(shape.rotate60());
        }

        @Test
        @DisplayName(
                "should return the same shape when the rotation is is a multiple of 360 degrees")
        void getRotatedShape_shouldReturnSameShapeWhenRotationIsMultipleOf360() {
            assertThat(shape.getRotatedShape(6)).isEqualTo(shape);
        }
    }

    @Nested
    @DisplayName("Method translate")
    class TestTranslate {
        @Test
        @DisplayName("should return a new shape with the same size")
        void translate_shouldReturnNewShapeWithSameSize() {
            assertThat(shape.translate(new PositionVector(1, 1, -2)).getElements())
                    .hasSize(shape.getElements().size());
        }

        @Test
        @DisplayName("should return a new shape with translated origin")
        void translate_shouldReturnNewShapeWithTranslatedOrigin() {
            assertThat(shape.translate(new PositionVector(1, 1, -2)).getElements())
                    .containsEntry(new PositionVector(1, 1, -2), new Tile(TileColor.YELLOW));
        }

        @Test
        @DisplayName("should return a new shape with the tiles translated")
        void translate_shouldReturnNewShapeWithTilesTranslated() {
            assertThat(shape.translate(new PositionVector(1, 1, -2)).getElements())
                    .containsEntry(new PositionVector(2, 1, -3), new Tile(TileColor.GREEN))
                    .containsEntry(new PositionVector(2, 0, -2), new Tile(TileColor.PINK))
                    .containsEntry(new PositionVector(1, 1, -2), new Tile(TileColor.YELLOW));
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
        @DisplayName("should return true when the shape has the same elements")
        void equals_shouldReturnTrueWhenShapeHasSameElements() {
            Shape otherShape = new Shape(shape.getElements());

            assertThat(shape).isEqualTo(otherShape);
        }

        @Test
        @DisplayName("should return false when the shape has a different elements color")
        void equals_shouldReturnFalseWhenShapeHasDifferentElementsColor() {
            Shape otherShape =
                    new Shape(
                            Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(1, 0, -1), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(1, -1, 0), new Tile(TileColor.PINK)));

            assertThat(shape).isNotEqualTo(otherShape);
        }

        @Test
        @DisplayName("should return false when the shape has a different elements position")
        void equals_shouldReturnFalseWhenShapeHasDifferentElementsPosition() {
            Shape otherShape =
                    new Shape(
                            Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(1, 1, -2), new Tile(TileColor.GREEN)),
                            Pair.of(new PositionVector(1, -1, 0), new Tile(TileColor.PINK)));

            assertThat(shape).isNotEqualTo(otherShape);
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("should return the same hash code when the shape is itself")
        void hashCode_shouldReturnSameHashCodeWhenShapeIsItself() {
            assertThat(shape).hasSameHashCodeAs(shape);
        }

        @Test
        @DisplayName("should return the same hash code when the shape has the same elements")
        void hashCode_shouldReturnSameHashCodeWhenShapeHasSameElements() {
            Shape otherShape = new Shape(shape.getElements());

            assertThat(shape).hasSameHashCodeAs(otherShape);
        }

        @Test
        @DisplayName(
                "should return a different hash code when the shape has a different elements color")
        void hashCode_shouldReturnDifferentHashCodeWhenShapeHasDifferentElementsColor() {
            Shape otherShape =
                    new Shape(
                            Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(1, 0, -1), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(1, -1, 0), new Tile(TileColor.PINK)));

            assertThat(shape).doesNotHaveSameHashCodeAs(otherShape);
        }

        @Test
        @DisplayName(
                "should return a different hash code when the shape has a different elements"
                        + " position")
        void hashCode_shouldReturnDifferentHashCodeWhenShapeHasDifferentElementsPosition() {
            Shape otherShape =
                    new Shape(
                            Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(1, 1, -2), new Tile(TileColor.GREEN)),
                            Pair.of(new PositionVector(1, -1, 0), new Tile(TileColor.PINK)));

            assertThat(shape).doesNotHaveSameHashCodeAs(otherShape);
        }
    }

    @Nested
    @DisplayName("Method toString")
    class TestToString {
        @Test
        @DisplayName("should return a string representation of the shape")
        void toString_shouldReturnStringRepresentationOfShape() {
            assertThat(shape.toString()).isNotNull();
        }
    }

    @Nested
    @DisplayName("method getMissingShape")
    class TestGetMissingShape {
        @Test
        @DisplayName("should return a shape corresponding to the missing part of the shape")
        void getMissingShape_shouldReturnShapeCorrespondingToMissingPartOfShape() {
            Shape startShape =
                    new Shape(
                            Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(1, 0, -1), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(1, -1, 0), new Tile(TileColor.PINK)));

            Shape otherShape =
                    new Shape(
                            Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(1, 0, -1), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(1, -1, 0), new Tile(TileColor.PINK)),
                            Pair.of(new PositionVector(1, 1, -2), new Tile(TileColor.GREEN)));

            assertThat(otherShape.getMissingShape(startShape))
                    .isEqualTo(
                            new Shape(
                                    Pair.of(
                                            new PositionVector(1, 1, -2),
                                            new Tile(TileColor.GREEN))));
        }

        @Test
        @DisplayName("should return a complex shape corresponding to the missing part of the shape")
        void getMissingShape_shouldReturnAComplexShapeCorrespondingToMissingPartOfShape() {
            Shape startShape =
                    new Shape(
                            Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(1, 0, -1), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(1, -1, 0), new Tile(TileColor.PINK)));

            Shape otherShape =
                    new Shape(
                            Pair.of(new PositionVector(0, 0, 0), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(1, 0, -1), new Tile(TileColor.YELLOW)),
                            Pair.of(new PositionVector(1, -1, 0), new Tile(TileColor.PINK)),
                            Pair.of(new PositionVector(1, 1, -2), new Tile(TileColor.GREEN)),
                            Pair.of(new PositionVector(2, 1, -3), new Tile(TileColor.GREEN)),
                            Pair.of(new PositionVector(2, 0, -2), new Tile(TileColor.GREEN)));

            assertThat(otherShape.getMissingShape(startShape))
                    .isEqualTo(
                            new Shape(
                                    Pair.of(
                                            new PositionVector(1, 1, -2),
                                            new Tile(TileColor.GREEN)),
                                    Pair.of(
                                            new PositionVector(2, 1, -3),
                                            new Tile(TileColor.GREEN)),
                                    Pair.of(
                                            new PositionVector(2, 0, -2),
                                            new Tile(TileColor.GREEN))));
        }
    }
}
