package com.takenoko.actors.panda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.engine.Board;
import com.takenoko.layers.LayerManager;
import com.takenoko.layers.tile.TileLayer;
import com.takenoko.tile.Pond;
import com.takenoko.tile.Tile;
import com.takenoko.vector.PositionVector;
import com.takenoko.vector.Vector;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PandaTest {
    private Board board;

    @BeforeEach
    void setUp() {
        HashMap<PositionVector, Tile> tiles = new HashMap<>();
        tiles.put(new PositionVector(0, 0, 0), new Pond());
        tiles.put(new PositionVector(1, 0, -1), new Tile());
        tiles.put(new PositionVector(1, -1, 0), new Tile());
        tiles.put(new PositionVector(2, -1, -1), new Tile());
        tiles.put(new PositionVector(2, -2, 0), new Tile());
        tiles.put(new PositionVector(1, -2, 1), new Tile());
        tiles.put(new PositionVector(2, -3, 1), new Tile());
        tiles.put(new PositionVector(1, -3, 2), new Tile());
        tiles.put(new PositionVector(0, -2, 2), new Tile());

        TileLayer tileLayer = mock(TileLayer.class);
        when(tileLayer.getTiles()).thenReturn(tiles);
        for (PositionVector position : tiles.keySet()) {
            when(tileLayer.isTile(position)).thenReturn(true);
        }

        LayerManager layerManager = mock(LayerManager.class);
        when(layerManager.getTileLayer()).thenReturn(tileLayer);
        board = mock(Board.class);
        when(board.getLayerManager()).thenReturn(layerManager);
    }

    @Nested
    @DisplayName("Method getPosition()")
    class TestGetPosition {
        @Test
        @DisplayName("should return the position of the panda")
        void shouldReturnThePositionOfThePanda() {
            Panda panda = new Panda(board);
            assertEquals(new PositionVector(0, 0, 0), panda.getPosition());
        }
    }

    @Nested
    @DisplayName("Method positionMessage()")
    class TestPositionMessage {
        @Test
        @DisplayName("should return a string explaining where the panda is on the board")
        void shouldReturnAStringExplainingWhereThePandaIsOnTheBoard() {
            Panda panda = new Panda(board);
            assertEquals("The panda is at Vector[q=0.0, r=0.0, s=0.0]", panda.positionMessage());
        }
    }

    @Nested
    @DisplayName("Method move()")
    class TestMove {

        @Test
        @DisplayName("should move the panda with a valid vector")
        void shouldMoveThePandaWithAVector() {
            Panda panda = new Panda(board);
            panda.move(new PositionVector(1, 0, -1));
            assertThat(panda.getPosition()).isEqualTo(new PositionVector(1, 0, -1));

            panda.move(new PositionVector(0, -1, 1));
            assertThat(panda.getPosition()).isEqualTo(new PositionVector(1, -1, 0));

            panda.move(new PositionVector(1, -1, -0));
            assertThat(panda.getPosition()).isEqualTo(new PositionVector(2, -2, 0));
        }

        @Test
        @DisplayName("should throw an exception if the panda is not moving with a valid vector")
        void shouldThrowAnExceptionIfThePandaIsNotMovingWithAValidVector() {
            Panda panda = new Panda(board);
            Vector vector = new PositionVector(1, 1, -2);
            assertThatThrownBy(() -> panda.move(vector))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("This move is not possible");
        }
    }

    @Nested
    @DisplayName("Method getPossibleMoves()")
    class TestGetPossibleMoves {
        @Test
        @DisplayName("should return a list of possible moves")
        void shouldReturnAListOfPossibleMoves() {
            Panda panda = new Panda(board);
            assertThat(panda.getPossibleMoves())
                    .containsExactlyInAnyOrder(
                            new PositionVector(1, -1, 0),
                            new PositionVector(2, -2, 0),
                            new PositionVector(1, 0, -1));
        }
    }
}
