package com.takenoko.objective;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BambooSizeObjectiveTest {
    private BambooSizeObjective bambooSizeObjective;

    @BeforeEach
    void setUp() {
        bambooSizeObjective = new BambooSizeObjective(3);
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {
        @Test
        @DisplayName("should return true when the two objects are the same")
        void equals_shouldReturnTrueWhenSameObject() {
            assertThat(bambooSizeObjective).isEqualTo(bambooSizeObjective);
        }

        @Test
        @DisplayName("should return true when the two objects are equal")
        void equals_shouldReturnTrueWhenEqual() {
            BambooSizeObjective other = new BambooSizeObjective(3);
            assertThat(bambooSizeObjective).isEqualTo(other);
        }

        @Test
        @DisplayName("should return false when the two objects are not equal")
        void equals_shouldReturnFalseWhenNotEqual() {
            BambooSizeObjective other = new BambooSizeObjective(4);
            assertThat(bambooSizeObjective).isNotEqualTo(other);
        }

        @Test
        @DisplayName("should return false when the other object is null")
        void equals_shouldReturnFalseWhenOtherIsNull() {
            assertThat(bambooSizeObjective).isNotEqualTo(null);
        }

        @Test
        @DisplayName("should return false when the other object is not a BambooSizeObjective")
        void equals_shouldReturnFalseWhenOtherIsNotBambooSizeObjective() {
            assertThat(bambooSizeObjective).isNotEqualTo(new Object());
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("should return the same hash code when the two objects are equal")
        void hashCode_shouldReturnSameHashCodeWhenEqual() {
            BambooSizeObjective other = new BambooSizeObjective(3);
            assertThat(bambooSizeObjective).hasSameHashCodeAs(other);
        }

        @Test
        @DisplayName("should return a different hash code when the two objects are not equal")
        void hashCode_shouldReturnDifferentHashCodeWhenNotEqual() {
            BambooSizeObjective other = new BambooSizeObjective(4);
            assertThat(bambooSizeObjective).doesNotHaveSameHashCodeAs(other);
        }
    }

    @Nested
    @DisplayName("Method copy()")
    class TestCopy {
        @Test
        @DisplayName("should return a copy of the object")
        void copy_shouldReturnCopyOfObject() {
            BambooSizeObjective copy = bambooSizeObjective.copy();
            assertThat(copy).isEqualTo(bambooSizeObjective).isNotSameAs(bambooSizeObjective);
        }
    }

    @Nested
    @DisplayName("Method verify()")
    class TestVerify {
        @Test
        @DisplayName("should return true when the bamboo count is greater than the objective")
        void verify_shouldReturnTrueWhenBambooCountGreaterThanObjective() {
            Board board = mock(Board.class);
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            tiles.put(new PositionVector(1, -1, 0), mock(Tile.class));
            when(board.getTiles()).thenReturn(tiles);
            when(board.getBambooAt(new PositionVector(1, -1, 0)))
                    .thenReturn(new LayerBambooStack(4));
            bambooSizeObjective.verify(board, mock(BotManager.class));
            assertThat(bambooSizeObjective.isAchieved()).isTrue();
        }

        @Test
        @DisplayName("should return false when the bamboo count is less than the objective")
        void verify_shouldReturnFalseWhenBambooCountLessThanObjective() {
            Board board = mock(Board.class);
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            tiles.put(new PositionVector(1, -1, 0), mock(Tile.class));
            when(board.getTiles()).thenReturn(tiles);
            when(board.getBambooAt(new PositionVector(1, -1, 0)))
                    .thenReturn(new LayerBambooStack(1));
            bambooSizeObjective.verify(board, mock(BotManager.class));
            assertThat(bambooSizeObjective.isAchieved()).isFalse();
        }
    }
}
