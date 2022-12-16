package com.takenoko.layers.bamboo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.takenoko.engine.Board;
import com.takenoko.layers.LayerManager;
import com.takenoko.layers.tile.TileLayer;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BambooLayerTest {

    private BambooLayer bambooLayer;
    private Board board;

    @BeforeEach
    void setUp() {
        board = mock(Board.class);
        bambooLayer = new BambooLayer(board);
    }

    @Nested
    @DisplayName("Method addBamboo()")
    class TestAddBamboo {
        @Test
        @DisplayName("should throw an exception if the position is the same as the Pond")
        void shouldThrowAnExceptionIfThePositionIsTheSameAsThePond() {
            PositionVector pondPosition = new PositionVector(0, 0, 0);
            assertThatThrownBy(() -> bambooLayer.addBamboo(pondPosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The bamboo cannot be placed on the pond");
        }

        @Test
        @DisplayName("should add bamboo to the bamboo layer")
        void shouldAddBambooToTheBambooLayer() {
            when(board.getLayerManager()).thenReturn(mock(LayerManager.class));
            when(board.getLayerManager().getTileLayer()).thenReturn(mock(TileLayer.class));
            when(board.getLayerManager().getTileLayer().isTile(any())).thenReturn(true);
            bambooLayer.addBamboo(new PositionVector(-1, 0, 1));
            assertThat(bambooLayer.getBambooAt(new PositionVector(-1, 0, 1)))
                    .isEqualTo(new BambooStack(1));
        }
    }

    @Nested
    @DisplayName("Method getBambooAt()")
    class TestGetBambooAt {
        @Test
        @DisplayName("should return 0 on an empty tile")
        void shouldReturn0OnAnEmptyTile() {
            when(board.getLayerManager()).thenReturn(mock(LayerManager.class));
            when(board.getLayerManager().getTileLayer()).thenReturn(mock(TileLayer.class));
            when(board.getLayerManager().getTileLayer().isTile(any())).thenReturn(true);
            assertThat(bambooLayer.getBambooAt(new PositionVector(-1, 0, 1)))
                    .isEqualTo(new BambooStack(0));
        }

        @Test
        @DisplayName("should throw an exception if the position is not on the board")
        void shouldThrowAnExceptionIfThePositionIsNotOnTheBoard() {
            when(board.getLayerManager()).thenReturn(mock(LayerManager.class));
            when(board.getLayerManager().getTileLayer()).thenReturn(mock(TileLayer.class));
            when(board.getLayerManager().getTileLayer().isTile(any())).thenReturn(false);
            PositionVector position = new PositionVector(-10, 0, 10);
            assertThatThrownBy(() -> bambooLayer.getBambooAt(position))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("should return the bamboo stack at the position")
        void shouldReturnTheBambooStackAtThePosition() {
            when(board.getLayerManager()).thenReturn(mock(LayerManager.class));
            when(board.getLayerManager().getTileLayer()).thenReturn(mock(TileLayer.class));
            when(board.getLayerManager().getTileLayer().isTile(any())).thenReturn(true);
            bambooLayer.addBamboo(new PositionVector(-1, 0, 1));
            assertThat(bambooLayer.getBambooAt(new PositionVector(-1, 0, 1)))
                    .isEqualTo(new BambooStack(1));
        }
    }

    @Nested
    @DisplayName("Method getBamboo()")
    class TestGetBamboo {
        @Test
        @DisplayName("should return the bamboo hash map")
        void shouldReturnTheBambooLayer() {
            assertThat(bambooLayer.getBamboo()).isInstanceOf(HashMap.class);
        }
    }

    @Nested
    @DisplayName("Method removeBamboo()")
    class TestRemoveBamboo {
        @Test
        @DisplayName("should remove the bamboo from the bamboo layer")
        void shouldRemoveTheBambooFromTheBambooLayer() {
            when(board.getLayerManager()).thenReturn(mock(LayerManager.class));
            when(board.getLayerManager().getTileLayer()).thenReturn(mock(TileLayer.class));
            when(board.getLayerManager().getTileLayer().isTile(any())).thenReturn(true);
            bambooLayer.addBamboo(new PositionVector(-1, 0, 1));
            bambooLayer.removeBamboo(new PositionVector(-1, 0, 1));
            assertThat(bambooLayer.getBambooAt(new PositionVector(-1, 0, 1)))
                    .isEqualTo(new BambooStack(0));
        }

        @Test
        @DisplayName("should throw an exception if the position is not on the board")
        void shouldThrowAnExceptionIfThePositionIsNotOnTheBoard() {
            when(board.getLayerManager()).thenReturn(mock(LayerManager.class));
            when(board.getLayerManager().getTileLayer()).thenReturn(mock(TileLayer.class));
            when(board.getLayerManager().getTileLayer().isTile(any())).thenReturn(false);
            PositionVector position = new PositionVector(-10, 0, 10);
            assertThatThrownBy(() -> bambooLayer.removeBamboo(position))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("should throw an exception if the bamboo stack is empty")
        void shouldThrowAnExceptionIfTheBambooStackIsEmpty() {
            when(board.getLayerManager()).thenReturn(mock(LayerManager.class));
            when(board.getLayerManager().getTileLayer()).thenReturn(mock(TileLayer.class));
            when(board.getLayerManager().getTileLayer().isTile(any())).thenReturn(true);
            PositionVector position = new PositionVector(-1, 0, 1);
            assertThatThrownBy(() -> bambooLayer.removeBamboo(position))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("There is no bamboo on this tile");
        }
    }
}
