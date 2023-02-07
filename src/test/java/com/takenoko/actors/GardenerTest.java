package com.takenoko.actors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.layers.tile.Pond;
import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;
import com.takenoko.vector.Vector;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.*;

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

        Board board;

        @BeforeEach
        void setUp() {
            board = mock(Board.class);

            List<PositionVector> l =
                    List.of(
                            new PositionVector(1, 0, -1),
                            new PositionVector(1, -1, 0),
                            new PositionVector(0, 0, 0),
                            new PositionVector(0, 1, -1),
                            new PositionVector(1, 1, -2),
                            new PositionVector(2, 0, -2),
                            new PositionVector(2, -1, -1));

            for (PositionVector pos : l) {
                if (pos.equals(new PositionVector(0, 0, 0))) {
                    when(board.getTileAt(pos)).thenReturn(new Pond());
                    when(board.isBambooGrowableAt(pos)).thenReturn(false);
                } else {
                    when(board.getTileAt(pos)).thenReturn(new Tile());
                    when(board.isBambooGrowableAt(pos)).thenReturn(true);
                }
                when(board.isTile(pos)).thenReturn(true);
                when(board.getBambooAt(pos)).thenReturn(mock(LayerBambooStack.class));
            }
        }

        @AfterEach
        void tearDown() {
            board = null;
        }

        @Test
        @DisplayName("should grow bamboo if the gardener can grow bamboo")
        void shouldGrowBambooIfTheGardenerCanGrowBamboo() {
            Gardener gardener = new Gardener(new PositionVector(1, 0, -1));
            Map<PositionVector, LayerBambooStack> bamb = gardener.afterMove(board);
            for (Vector neigbour :
                    gardener.getPositionVector().getNeighbors().stream()
                            .filter(v -> !v.equals(new Vector(0, 0, 0)))
                            .toList()) {
                verify(board, times(1)).growBamboo(neigbour.toPositionVector());
            }
            verify(board, times(1)).growBamboo(gardener.getPositionVector());
            assertThat(bamb).isNotNull();
        }

        @Test
        @DisplayName("should grow bamboo on the neighbouring tiles if they are irrigated")
        void shouldGrowBambooOnTheNeighbouringTilesIfTheyAreIrrigated() {
            Gardener gardener = new Gardener(new PositionVector(1, 0, -1));
            Map<PositionVector, LayerBambooStack> bamb = gardener.afterMove(board);
            verify(board, times(1)).growBamboo(gardener.getPositionVector());
            for (Vector v :
                    gardener.getPositionVector().getNeighbors().stream()
                            .filter(v -> !v.equals(new Vector(0, 0, 0)))
                            .toList()) {
                verify(board, times(1)).growBamboo(v.toPositionVector());
            }
            assertThat(bamb).hasSize(6);
        }

        @Test
        @DisplayName("should not grow bamboo if the gardener cannot grow bamboo")
        void shouldNotGrowBambooIfTheGardenerCannotGrowBamboo() {
            Gardener gardener = new Gardener(new PositionVector(0, 0, 0));
            Map<PositionVector, LayerBambooStack> bamb = gardener.afterMove(board);
            verify(board, times(0)).growBamboo(gardener.getPositionVector());
            assertThat(bamb).isEmpty();
        }
    }
}
