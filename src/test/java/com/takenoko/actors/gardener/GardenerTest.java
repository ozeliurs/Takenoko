package com.takenoko.actors.gardener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.layers.bamboo.BambooStack;
import com.takenoko.layers.tile.Pond;
import com.takenoko.layers.tile.Tile;
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
            assertThat(gardener.getPositionVector()).isEqualTo(new PositionVector(0, 0, 0));
        }

        @Test
        @DisplayName("should instantiate the gardener at the given position")
        void shouldInstantiateTheGardenerAtTheGivenPosition() {
            Gardener gardener = new Gardener(new PositionVector(1, 2, -3));
            assertThat(gardener.getPositionVector()).isEqualTo(new PositionVector(1, 2, -3));
        }
    }

    @Nested
    @DisplayName("Method positionMessage()")
    class TestPositionMessage {
        @Test
        @DisplayName("should return a string explaining where the gardener is on the board")
        void shouldReturnAStringExplainingWhereTheGardenerIsOnTheBoard() {
            Gardener gardener = new Gardener(new PositionVector(1, 2, -3));
            assertThat(gardener.positionMessage())
                    .isEqualTo("The gardener is at Vector[q=1.0, r=2.0, s=-3.0]");
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
            assertThat(gardener.getPositionVector()).isEqualTo(gardenerCopy.getPositionVector());
        }
    }

    @Nested
    @DisplayName("Method afterMove()")
    class TestAfterMove {

        @Test
        @DisplayName("should grow bamboo if the gardener can grow bamboo")
        void shouldGrowBambooIfTheGardenerCanGrowBamboo() {
            Board board = spy(Board.class);
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(new Tile());
            Gardener gardener = new Gardener(new PositionVector(1, 0, -1));
            BambooStack bamb = gardener.afterMove(board);
            verify(board, times(1)).growBamboo(gardener.getPositionVector());
            assertThat(bamb.getBambooCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("should not grow bamboo if the gardener cannot grow bamboo")
        void shouldNotGrowBambooIfTheGardenerCannotGrowBamboo() {
            Board board = spy(Board.class);
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(new Pond());
            Gardener gardener = new Gardener(new PositionVector(1, 0, -1));
            BambooStack bamb = gardener.afterMove(board);
            verify(board, times(0)).growBamboo(gardener.getPositionVector());
            assertThat(bamb.getBambooCount()).isZero();
        }
    }
}
