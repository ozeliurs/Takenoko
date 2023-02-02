package com.takenoko.layers.irrigation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EdgePositionTest {

    @Nested
    @DisplayName("test constructor")
    class TestConstructor {
        @Test
        @DisplayName("if the irrigation channel positions are the same then throw an exception")
        void ifTheIrrigationChannelPositionsAreTheSameThenThrowAnException() {
            PositionVector positionVector = new PositionVector(0, 0, 0);
            assertThatThrownBy(() -> new EdgePosition(positionVector, positionVector))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("if the irrigation channel positions are not adjacent then throw an exception")
        void ifTheIrrigationChannelPositionsAreNotAdjacentThenThrowAnException() {
            PositionVector positionVector = new PositionVector(0, 0, 0);
            PositionVector positionVector1 = new PositionVector(2, -1, -1);
            assertThatThrownBy(() -> new EdgePosition(positionVector, positionVector1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The irrigation channel position must be adjacent");
        }
    }

    @Test
    @DisplayName("method getNeighbours")
    void methodGetNeighbours() {
        PositionVector positionVector = new PositionVector(0, 0, 0);
        PositionVector positionVector1 = new PositionVector(1, 0, -1);
        EdgePosition edgePosition = new EdgePosition(positionVector, positionVector1);

        assertThat(edgePosition.getNeighbours())
                .containsExactlyInAnyOrder(
                        new EdgePosition(positionVector, new PositionVector(1, -1, 0)),
                        new EdgePosition(positionVector, new PositionVector(0, 1, -1)),
                        new EdgePosition(positionVector1, new PositionVector(1, -1, 0)),
                        new EdgePosition(positionVector1, new PositionVector(0, 1, -1)));
    }
}
