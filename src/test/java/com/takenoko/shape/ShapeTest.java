package com.takenoko.shape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.takenoko.vector.PositionVector;
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
            assertThat(shape.rotate60().getElements()).hasSize(shape.getElements().size());
        }

        @Test
        @DisplayName("should return a new shape with the same origin")
        void rotate60_shouldReturnNewShapeWithSameOrigin() {
            assertThat(shape.rotate60().getElements()).contains(new PositionVector(0, 0, 0));
        }

        @Test
        @DisplayName("should return a new shape with the tiles rotated 60 degrees")
        void rotate60_shouldReturnNewShapeWithTilesRotated60Degrees() {
            assertThat(shape.rotate60().getElements()).contains(new PositionVector(0, 0, 0));
            assertThat(shape.rotate60().getElements()).contains(new PositionVector(0, 1, -1));
            assertThat(shape.rotate60().getElements()).contains(new PositionVector(1, 0, -1));
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
        @DisplayName("should return a new shape with the same origin")
        void translate_shouldReturnNewShapeWithSameOrigin() {
            assertThat(shape.translate(new PositionVector(1, 1, -2)).getElements())
                    .contains(new PositionVector(1, 1, -2));
        }

        @Test
        @DisplayName("should return a new shape with the tiles translated")
        void translate_shouldReturnNewShapeWithTilesTranslated() {
            assertThat(shape.translate(new PositionVector(1, 1, -2)).getElements())
                    .contains(new PositionVector(1, 1, -2));
            assertThat(shape.translate(new PositionVector(1, 1, -2)).getElements())
                    .contains(new PositionVector(2, 1, -3));
            assertThat(shape.translate(new PositionVector(1, 1, -2)).getElements())
                    .contains(new PositionVector(2, 0, -2));
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
                                    + " s=-1.0], Vector[q=1.0, r=-1.0, s=0.0]],"
                                    + " rotationOrigin=Vector[q=0.0, r=0.0, s=0.0]}");
        }
    }
}
