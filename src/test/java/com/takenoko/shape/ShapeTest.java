package com.takenoko.shape;

import static org.assertj.core.api.Assertions.assertThat;

import com.takenoko.vector.Vector;
import java.util.ArrayList;
import org.junit.jupiter.api.*;

public class ShapeTest {

    private Shape shape;

    @BeforeEach
    void setUp() {
        ArrayList<Vector> pattern = new ArrayList<>();
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
}
