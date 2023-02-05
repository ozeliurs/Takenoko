package com.takenoko.actors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Pond;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.vector.PositionVector;
import com.takenoko.vector.Vector;
import java.util.HashMap;
import java.util.Map;
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
            PositionVector position = new PositionVector(1, 0, -1);

            when(board.getTileAt(position)).thenReturn(new Tile());
            when(board.isTile(position)).thenReturn(true);
            Gardener gardener = new Gardener(position);
            Map<PositionVector, LayerBambooStack> bamb = gardener.afterMove(board);
            verify(board, times(1)).growBamboo(gardener.getPositionVector());
            assertThat(bamb).containsEntry(gardener.getPositionVector(), new LayerBambooStack(1));
        }

        @Test
        @DisplayName("should grow bamboo on the neighbouring tiles if they are irrigated")
        void shouldGrowBambooOnTheNeighbouringTilesIfTheyAreIrrigated() {
            Board board = spy(Board.class);
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(new Tile());
            when(board.isIrrigatedAt(any())).thenReturn(true);
            Gardener gardener = new Gardener(new PositionVector(1, 0, -1));
            Map<PositionVector, LayerBambooStack> bamb = gardener.afterMove(board);
            verify(board, times(1)).growBamboo(gardener.getPositionVector());

            for (Vector v : gardener.getPositionVector().getNeighbors()) {
                verify(board, times(1)).growBamboo(v.toPositionVector());
            }

            assertThat(bamb).hasSize(7);
        }

        @Test
        @DisplayName(
                "should not grow bamboo on the neighbouring tiles if they are not the same color")
        void shouldNotGrowBambooOnTheNeighbouringTilesIfTheyAreNotTheSameColor() {
            Board board = spy(Board.class);
            HashMap<PositionVector, Tile> tiles = new HashMap<>();
            tiles.put(
                    new PositionVector(1, 0, -1),
                    new Tile(ImprovementType.FERTILIZER, TileColor.GREEN));
            tiles.put(new PositionVector(2, -1, -1), new Tile(TileColor.GREEN));
            tiles.put(new PositionVector(3, -1, -2), new Tile(TileColor.GREEN));
            tiles.put(
                    new PositionVector(3, 0, -3),
                    new Tile(ImprovementType.FERTILIZER, TileColor.PINK));
            tiles.put(
                    new PositionVector(2, 1, -3),
                    new Tile(ImprovementType.WATERSHED, TileColor.PINK));
            tiles.put(new PositionVector(1, 1, -2), new Tile(TileColor.PINK));
            tiles.put(new PositionVector(2, 0, -2), new Tile(TileColor.PINK));
            when(board.getTiles()).thenReturn(tiles);
            when(board.isTile(any())).thenReturn(true);
            when(board.isIrrigatedAt(any())).thenReturn(true);

            for (PositionVector v : tiles.keySet()) {
                when(board.getTileAt(v)).thenReturn(tiles.get(v));
            }

            Gardener gardener = new Gardener(new PositionVector(2, 0, -2));
            Map<PositionVector, LayerBambooStack> bamb = gardener.afterMove(board);

            assertThat(bamb)
                    .containsExactlyInAnyOrderEntriesOf(
                            Map.of(
                                    new PositionVector(2, 0, -2), new LayerBambooStack(1),
                                    new PositionVector(1, 1, -2), new LayerBambooStack(1),
                                    new PositionVector(2, 1, -3), new LayerBambooStack(1),
                                    new PositionVector(3, 0, -3),
                                            new LayerBambooStack(2) // Because Fertilizer
                                    ));
            verify(board, times(1)).growBamboo(new PositionVector(2, 0, -2));
            verify(board, times(1)).growBamboo(new PositionVector(1, 1, -2));
            verify(board, times(1)).growBamboo(new PositionVector(2, 1, -3));
            verify(board, times(1)).growBamboo(new PositionVector(3, 0, -3));
        }

        @Test
        @DisplayName("should not grow bamboo if the gardener cannot grow bamboo")
        void shouldNotGrowBambooIfTheGardenerCannotGrowBamboo() {
            Board board = spy(Board.class);
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(new Pond());
            Gardener gardener = new Gardener(new PositionVector(1, 0, -1));
            Map<PositionVector, LayerBambooStack> bamb = gardener.afterMove(board);
            verify(board, times(0)).growBamboo(gardener.getPositionVector());
            assertThat(bamb).isEmpty();
        }
    }
}
