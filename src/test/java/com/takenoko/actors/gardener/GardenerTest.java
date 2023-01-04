package com.takenoko.actors.gardener;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GardenerTest {
    @Nested
    @DisplayName("Constructor")
    class TestConstructor {
        @Test
        @DisplayName("should instantiate the gardener at the origin")
        void shouldInstantiateTheGardenerAtTheOrigin() {
            Gardener gardener = new Gardener();
            assertEquals(new PositionVector(0, 0, 0), gardener.getPositionVector());
        }

        @Test
        @DisplayName("should instantiate the gardener at the given position")
        void shouldInstantiateTheGardenerAtTheGivenPosition() {
            Gardener gardener = new Gardener(new PositionVector(1, 2, -3));
            assertEquals(new PositionVector(1, 2, -3), gardener.getPositionVector());
        }
    }

    @Nested
    @DisplayName("Method positionMessage()")
    class TestPositionMessage {
        @Test
        @DisplayName("should return a string explaining where the gardener is on the board")
        void shouldReturnAStringExplainingWhereTheGardenerIsOnTheBoard() {
            Gardener gardener = new Gardener(new PositionVector(1, 2, -3));
            assertEquals(
                    "The gardener is at Vector[q=1.0, r=2.0, s=-3.0]", gardener.positionMessage());
        }
    }

    @Nested
    @DisplayName("Method copy()")
    class TestCopy {
        @Test
        @DisplayName("should make a copy of the gardener")
        void shouldMakeACopyOfTheGardener() {
            Gardener gardener = new Gardener(new PositionVector(1, 2, -3));
            Gardener gardenerCopy = gardener.copy();
            assertEquals(gardener.getPositionVector(), gardenerCopy.getPositionVector());
        }
    }

    @Nested
    @DisplayName("Method afterMove()")
    class TestAfterMove {
        @Test
        @DisplayName("should grow bamboo at the position of the gardener")
        void shouldGrowBambooAtThePositionOfTheGardener() {
            Board board = spy(Board.class);
            when(board.isTile(any())).thenReturn(true);
            // when(board.placeTile(any(), any())).thenReturn(new LayerBambooStack(1));
            // board.placeTile(board.getAvailableTiles().get(0), new PositionVector(1, 0, -1));
            Gardener gardener = new Gardener(new PositionVector(1, 0, -1));
            gardener.afterMove(board);
            verify(board, times(1)).growBamboo(gardener.getPositionVector());
        }
    }
}
