package com.takenoko.objective;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.shape.Shape;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ShapeObjectiveTest {
    @Nested
    @DisplayName("Method verify")
    class TestVerify {
        @Test
        @DisplayName("When board matches Shape, state is ACHIEVED")
        void verify_WhenBoardHasTwoTilesNextToEachOther_ThenObjectiveStateIsACHIEVED() {
            Shape shape = mock(Shape.class);
            when(shape.match(new HashMap<>())).thenReturn(List.of(mock(Shape.class)));
            ShapeObjective shapeObjective = new ShapeObjective(shape);
            shapeObjective.verify(mock(Board.class), mock(BotManager.class));
            assertEquals(ObjectiveState.ACHIEVED, shapeObjective.getState());
        }

        @Test
        @DisplayName("When board doesn't match Shape, state is NOT_ACHIEVED")
        void verify_WhenBoardHasTwoTilesNextToEachOther_ThenObjectiveStateIsNOT_ACHIEVED() {
            Shape shape = mock(Shape.class);
            when(shape.match(new HashMap<>())).thenReturn(List.of());
            ShapeObjective shapeObjective = new ShapeObjective(shape);
            shapeObjective.verify(mock(Board.class), mock(BotManager.class));
            assertEquals(ObjectiveState.NOT_ACHIEVED, shapeObjective.getState());
        }
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {

        @Test
        @DisplayName("When ShapeObjective is compared to itself, it is equal")
        @SuppressWarnings("EqualsWithItself")
        void equals_WhenShapeObjectiveIsComparedToItself_ThenItIsEqual() {
            Shape shape = mock(Shape.class);
            ShapeObjective shapeObjective = new ShapeObjective(shape);
            assertThat(shapeObjective.equals(shapeObjective)).isTrue();
        }

        @Test
        @DisplayName("When ShapeObjective is compared to null, it is not equal")
        @SuppressWarnings("ConstantConditions")
        void equals_WhenShapeObjectiveIsComparedToNull_ThenItIsNotEqual() {
            Shape shape = mock(Shape.class);
            ShapeObjective shapeObjective = new ShapeObjective(shape);
            assertThat(shapeObjective.equals(null)).isFalse();
        }

        @Test
        @DisplayName(
                "When ShapeObjective is compared to an object of another class, it is not equal")
        void equals_WhenShapeObjectiveIsComparedToAnObjectOfAnotherClass_ThenItIsNotEqual() {
            Shape shape = mock(Shape.class);
            ShapeObjective shapeObjective = new ShapeObjective(shape);
            assertThat(shapeObjective.equals(new Object())).isFalse();
        }

        @Test
        @DisplayName(
                "When ShapeObjective is compared to another ShapeObjective with the same Shape, it"
                        + " is equal")
        void
                equals_WhenShapeObjectiveIsComparedToAnotherShapeObjectiveWithTheSameShape_ThenItIsEqual() {
            Shape shape = mock(Shape.class);
            ShapeObjective shapeObjective = new ShapeObjective(shape);
            ShapeObjective shapeObjective2 = new ShapeObjective(shape);
            assertThat(shapeObjective.equals(shapeObjective2)).isTrue();
        }

        @Test
        @DisplayName(
                "When ShapeObjective is compared to another ShapeObjective with a different Shape,"
                        + " it is not equal")
        void
                equals_WhenShapeObjectiveIsComparedToAnotherShapeObjectiveWithADifferentShape_ThenItIsNotEqual() {
            Shape shape = mock(Shape.class);
            Shape shape2 = mock(Shape.class);
            ShapeObjective shapeObjective = new ShapeObjective(shape);
            ShapeObjective shapeObjective2 = new ShapeObjective(shape2);
            assertThat(shapeObjective.equals(shapeObjective2)).isFalse();
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("When ShapeObjective is compared to itself, it has the same hash code")
        void hashCode_WhenShapeObjectiveIsComparedToItself_ThenItHasTheSameHashCode() {
            Shape shape = mock(Shape.class);
            ShapeObjective shapeObjective = new ShapeObjective(shape);
            assertThat(shapeObjective).hasSameHashCodeAs(shapeObjective);
        }

        @Test
        @DisplayName(
                "When ShapeObjective is compared to another ShapeObjective with the same Shape, it"
                        + " has the same hash code")
        void
                hashCode_WhenShapeObjectiveIsComparedToAnotherShapeObjectiveWithTheSameShape_ThenItHasTheSameHashCode() {
            Shape shape = mock(Shape.class);
            ShapeObjective shapeObjective = new ShapeObjective(shape);
            ShapeObjective shapeObjective2 = new ShapeObjective(shape);
            assertThat(shapeObjective).hasSameHashCodeAs(shapeObjective2);
        }

        @Test
        @DisplayName(
                "When ShapeObjective is compared to another ShapeObjective with a different Shape,"
                        + " it has a different hash code")
        void
                hashCode_WhenShapeObjectiveIsComparedToAnotherShapeObjectiveWithADifferentShape_ThenItHasADifferentHashCode() {
            Shape shape = mock(Shape.class);
            Shape shape2 = mock(Shape.class);
            ShapeObjective shapeObjective = new ShapeObjective(shape);
            ShapeObjective shapeObjective2 = new ShapeObjective(shape2);
            assertThat(shapeObjective).doesNotHaveSameHashCodeAs(shapeObjective2);
        }
    }
}
