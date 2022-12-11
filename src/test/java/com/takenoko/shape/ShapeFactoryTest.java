package com.takenoko.shape;

import static org.assertj.core.api.Assertions.assertThat;

import com.takenoko.vector.Vector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ShapeFactoryTest {
    @Nested
    @DisplayName("Method createShape for ADJACENT")
    class TestCreateAdjacent {

        @Test
        @DisplayName("When the shape is ADJACENT should be of length 2")
        void createShape_shouldReturnAdjacentOfLength2() {
            assertThat(ShapeFactory.ADJACENT.createShape().getPattern()).hasSize(2);
        }

        @Test
        @DisplayName("When the shape is ADJACENT should contain the origin")
        void createShape_shouldReturnAdjacentContainingOrigin() {
            assertThat(ShapeFactory.ADJACENT.createShape().getPattern())
                    .contains(new Vector(0, 0, 0));
        }

        @Test
        @DisplayName("When the shape is ADJACENT should contain the adjacent tile")
        void createShape_shouldReturnAdjacentContainingAdjacentTile() {
            assertThat(ShapeFactory.ADJACENT.createShape().getPattern())
                    .contains(new Vector(1, 0, -1));
        }
    }

    @Nested
    @DisplayName("Method createShape for LINE")
    class TestCreateLine {

        @Test
        @DisplayName("When the shape is LINE should be of length 3")
        void createShape_shouldReturnLineOfLength3() {
            assertThat(ShapeFactory.LINE.createShape().getPattern()).hasSize(3);
        }

        @Test
        @DisplayName("When the shape is LINE should contain the origin")
        void createShape_shouldReturnLineContainingOrigin() {
            assertThat(ShapeFactory.LINE.createShape().getPattern()).contains(new Vector(0, 0, 0));
        }

        @Test
        @DisplayName("When the shape is LINE should contain the adjacent tile")
        void createShape_shouldReturnLineContainingAdjacentTile() {
            assertThat(ShapeFactory.LINE.createShape().getPattern()).contains(new Vector(1, 0, -1));
        }

        @Test
        @DisplayName("When the shape is LINE should contain the second adjacent tile")
        void createShape_shouldReturnLineContainingSecondAdjacentTile() {
            assertThat(ShapeFactory.LINE.createShape().getPattern()).contains(new Vector(2, 0, -2));
        }
    }
}
