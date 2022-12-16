package com.takenoko.layers.bamboo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.spy;

import com.takenoko.engine.Board;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BambooLayerTest {

    private BambooLayer bambooLayer;

    @BeforeEach
    void setUp() {
        bambooLayer = new BambooLayer(spy(Board.class));
    }

    @Nested
    @DisplayName("Method addBamboo()")
    class TestAddBamboo {
        @Test
        @DisplayName("should add bamboo to the bamboo layer")
        void shouldAddBambooToTheBambooLayer() {
            bambooLayer.addBamboo(new PositionVector(0, 0, 0));
            assertThat(bambooLayer.getBambooAt(new PositionVector(0, 0, 0))).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("Method getBambooAt()")
    class TestGetBambooAt {
        @Test
        @DisplayName("should return 1 on an irrigated tile")
        void shouldReturn1OnAnIrrigatedTile() {
            assertThat(bambooLayer.getBambooAt(new PositionVector(0, 0, 0))).isEqualTo(1);
        }

        @Test
        @DisplayName("should throw an exception if the position is not on the board")
        void shouldThrowAnExceptionIfThePositionIsNotOnTheBoard() {
            PositionVector position = new PositionVector(-10, 0, 10);
            assertThatThrownBy(() -> bambooLayer.getBambooAt(position))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Method getBamboo()")
    class TestGetBamboo {
        @Test
        @DisplayName("should return the bamboo hash map")
        void shouldReturnTheBambooLayer() {
            bambooLayer.addBamboo(new PositionVector(0, 0, 0));
            assertThat(bambooLayer.getBamboo()).containsEntry(new PositionVector(0, 0, 0), 2);
        }
    }
}
