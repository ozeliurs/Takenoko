package com.takenoko.shape;

import static org.assertj.core.api.Assertions.assertThat;

import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PatternFactoryTest {
    @Nested
    @DisplayName("Method createShape for ADJACENT")
    class TestCreateAdjacent {

        @Test
        @DisplayName("When the shape is ADJACENT should be of length 2")
        void createShape_shouldReturnAdjacentOfLength2() {
            assertThat(PatternFactory.ADJACENT.createPattern().getElements()).hasSize(2);
        }

        @Test
        @DisplayName("When the shape is ADJACENT should contain the origin")
        void createShape_shouldReturnAdjacentContainingOrigin() {
            assertThat(PatternFactory.ADJACENT.createPattern().getElements().keySet())
                    .contains(new PositionVector(0, 0, 0));
        }

        @Test
        @DisplayName("When the shape is ADJACENT should contain the adjacent tile")
        void createShape_shouldReturnAdjacentContainingAdjacentTile() {
            assertThat(PatternFactory.ADJACENT.createPattern().getElements().keySet())
                    .contains(new PositionVector(1, 0, -1));
        }
    }

    @Nested
    @DisplayName("Method createShape for LINE")
    class TestCreateLine {

        @Test
        @DisplayName("When the shape is LINE should be of length 3")
        void createShape_shouldReturnLineOfLength3() {
            assertThat(PatternFactory.LINE.createPattern().getElements()).hasSize(3);
        }

        @Test
        @DisplayName("When the shape is LINE should contain the origin")
        void createShape_shouldReturnLineContainingOrigin() {
            assertThat(PatternFactory.LINE.createPattern().getElements().keySet())
                    .contains(new PositionVector(0, 0, 0));
        }

        @Test
        @DisplayName("When the shape is LINE should contain the adjacent tile")
        void createShape_shouldReturnLineContainingAdjacentTile() {
            assertThat(PatternFactory.LINE.createPattern().getElements().keySet())
                    .contains(new PositionVector(1, 0, -1));
        }

        @Test
        @DisplayName("When the shape is LINE should contain the second adjacent tile")
        void createShape_shouldReturnLineContainingSecondAdjacentTile() {
            assertThat(PatternFactory.LINE.createPattern().getElements().keySet())
                    .contains(new PositionVector(2, 0, -2));
        }
    }
}
