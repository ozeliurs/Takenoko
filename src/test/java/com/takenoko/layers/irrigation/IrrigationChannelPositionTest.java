package com.takenoko.layers.irrigation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class IrrigationChannelPositionTest {

    @Nested
    @DisplayName("test constructor")
    class TestConstructor {
        @Test
        @DisplayName("if the irrigation channel positions are the same then throw an exception")
        void ifTheIrrigationChannelPositionsAreTheSameThenThrowAnException() {
            PositionVector positionVector = new PositionVector(0, 0, 0);
            assertThatThrownBy(() -> new IrrigationChannelPosition(positionVector, positionVector))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The irrigation channel positions cannot be the same");
        }

        @Test
        @DisplayName("if the irrigation channel positions are not adjacent then throw an exception")
        void ifTheIrrigationChannelPositionsAreNotAdjacentThenThrowAnException() {
            PositionVector positionVector = new PositionVector(0, 0, 0);
            PositionVector positionVector1 = new PositionVector(2, -1, -1);
            assertThatThrownBy(() -> new IrrigationChannelPosition(positionVector, positionVector1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The irrigation channel position must be adjacent");
        }
    }
}
