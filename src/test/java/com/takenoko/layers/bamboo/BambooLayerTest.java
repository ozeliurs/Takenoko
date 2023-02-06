package com.takenoko.layers.bamboo;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.takenoko.engine.Board;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileType;
import com.takenoko.vector.PositionVector;
import java.util.Optional;
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
        bambooLayer = new BambooLayer();
    }

    @Nested
    @DisplayName("Method addBamboo()")
    class TestGrowBamboo {
        @Test
        @DisplayName("should throw an exception if the position is the same as the Pond")
        void shouldThrowAnExceptionIfThePositionIsTheSameAsThePond() {
            PositionVector pondPosition = new PositionVector(0, 0, 0);
            assertThatThrownBy(() -> bambooLayer.growBamboo(pondPosition, board))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("should throw an exception if the position is not on the board")
        void shouldThrowAnExceptionIfThePositionIsNotOnTheBoard() {
            PositionVector position = new PositionVector(1, 0, -1);
            when(board.isTile(position)).thenReturn(false);
            assertThatThrownBy(() -> bambooLayer.growBamboo(position, board))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("should add a bamboo to the bamboo layer")
        void shouldAddABambooToTheBambooLayerIfTheKeyExists() {
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(mock(Tile.class));
            PositionVector position = new PositionVector(1, 0, -1);
            when(board.isBambooGrowableAt(position)).thenReturn(true);
            bambooLayer.growBamboo(position, board);
            bambooLayer.growBamboo(position, board);
            assertThat(bambooLayer.getBambooAt(position, board).getBambooCount()).isEqualTo(2);
        }

        @Test
        @DisplayName("should add two bamboos to the bamboo layer when fertilized")
        void shouldAddTwoBamboosToTheBambooLayerWhenFertilized() {
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(mock(Tile.class));
            when(board.isBambooGrowableAt(any())).thenReturn(true);
            when(board.getTileAt(any()).getImprovement())
                    .thenReturn(Optional.of(ImprovementType.FERTILIZER));
            PositionVector position = new PositionVector(1, 0, -1);
            bambooLayer.growBamboo(position, board);
            bambooLayer.growBamboo(position, board);
            assertThat(bambooLayer.getBambooAt(position, board).getBambooCount()).isEqualTo(4);
        }

        @Test
        @DisplayName("should not grow bamboo past the maximum number of bamboos")
        void shouldNotGrowBambooPastTheMaximumNumberOfBamboos() {
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(mock(Tile.class));
            when(board.isBambooGrowableAt(any())).thenReturn(true);
            PositionVector position = new PositionVector(1, 0, -1);
            bambooLayer.growBamboo(position, board);
            when(board.getTileAt(any()).getImprovement())
                    .thenReturn(Optional.of(ImprovementType.FERTILIZER));
            bambooLayer.growBamboo(position, board);
            bambooLayer.growBamboo(position, board);
            assertThat(bambooLayer.getBambooAt(position, board).getBambooCount()).isEqualTo(4);
        }
    }

    @Nested
    @DisplayName("Method getBambooAt()")
    class TestGetBambooAt {
        @Test
        @DisplayName("should return 0 on an empty tile")
        void shouldReturn0OnAnEmptyTile() {
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(new Tile());
            assertThat(bambooLayer.getBambooAt(new PositionVector(-1, 0, 1), board))
                    .isEqualTo(new LayerBambooStack(0));
        }

        @Test
        @DisplayName("should throw an exception if the position is not on the board")
        void shouldThrowAnExceptionIfThePositionIsNotOnTheBoard() {
            when(board.isTile(any())).thenReturn(false);
            PositionVector position = new PositionVector(-10, 0, 10);
            assertThatThrownBy(() -> bambooLayer.getBambooAt(position, board))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("should return the bamboo stack at the position")
        void shouldReturnTheBambooStackAtThePosition() {
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(mock(Tile.class));
            when(board.isBambooGrowableAt(any())).thenReturn(true);
            bambooLayer.growBamboo(new PositionVector(-1, 0, 1), board);
            assertThat(bambooLayer.getBambooAt(new PositionVector(-1, 0, 1), board))
                    .isEqualTo(new LayerBambooStack(1));
        }
    }

    @Nested
    @DisplayName("Method removeBamboo()")
    class TestRemoveBamboo {
        @Test
        @DisplayName("should remove the bamboo from the bamboo layer")
        void shouldRemoveTheBambooFromTheBambooLayer() {
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(mock(Tile.class));
            // LayerBambooStack bambooStack1 = new LayerBambooStack(1);
            LayerBambooStack bambooStack =
                    bambooLayer.getBambooAt(new PositionVector(-1, 0, 1), board);
            bambooStack.growBamboo();
            when(board.getBambooAt(any())).thenReturn(bambooStack);
            when(board.isBambooEatableAt(any())).thenReturn(true);
            bambooLayer.eatBamboo(new PositionVector(-1, 0, 1), board);
            assertThat(bambooLayer.getBambooAt(new PositionVector(-1, 0, 1), board))
                    .isEqualTo(new LayerBambooStack(0));
        }

        @Test
        @DisplayName("should throw an exception if the position is not on the board")
        void shouldThrowAnExceptionIfThePositionIsNotOnTheBoard() {
            when(board.isTile(any())).thenReturn(false);
            doThrow(IllegalArgumentException.class).when(board).getBambooAt(any());
            PositionVector position = new PositionVector(-10, 0, 10);
            assertThatThrownBy(() -> bambooLayer.eatBamboo(position, board))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("should throw an exception if the bamboo stack is empty")
        void shouldThrowAnExceptionIfTheBambooStackIsEmpty() {
            when(board.isTile(any())).thenReturn(true);
            LayerBambooStack bambooStack =
                    bambooLayer.getBambooAt(new PositionVector(-1, 0, 1), board);
            when(board.getBambooAt(any())).thenReturn(bambooStack);
            when(board.isBambooEatableAt(any())).thenReturn(true);
            PositionVector position = new PositionVector(-1, 0, 1);
            assertThatThrownBy(() -> bambooLayer.eatBamboo(position, board))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Method equals()")
    class TestEquals {

        @Test
        @DisplayName("should return true if the bamboo layer is itself")
        void shouldReturnTrueIfTheBambooLayerEqualsItself() {
            assertThat(bambooLayer).isEqualTo(bambooLayer);
        }

        @Test
        @DisplayName("should return false if it is not a bamboo layer")
        void shouldReturnFalseIfItIsNotABambooLayer() {
            assertThat(bambooLayer).isNotEqualTo(new Object());
        }

        @Test
        @DisplayName("should return true if the bamboo layers are equal")
        void shouldReturnTrueIfTheBambooLayersAreEqual() {
            BambooLayer bambooLayer1 = new BambooLayer();
            BambooLayer bambooLayer2 = new BambooLayer();
            assertThat(bambooLayer1).isEqualTo(bambooLayer2);
        }

        @Test
        @DisplayName("should return false if the bamboo layers are not equal")
        void shouldReturnFalseIfTheBambooLayersAreNotEqual() {
            BambooLayer bambooLayer1 = new BambooLayer();
            BambooLayer bambooLayer2 = new BambooLayer();
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(mock(Tile.class));
            when(board.isBambooGrowableAt(any())).thenReturn(true);
            bambooLayer1.growBamboo(new PositionVector(-1, 0, 1), board);
            assertThat(bambooLayer1).isNotEqualTo(bambooLayer2);
        }
    }

    @Nested
    @DisplayName("Method hashCode()")
    class TestHashCode {
        @Test
        @DisplayName("should return the same hash code if the bamboo layers are equal")
        void shouldReturnTheSameHashCodeIfTheBambooLayersAreEqual() {
            BambooLayer bambooLayer1 = new BambooLayer();
            BambooLayer bambooLayer2 = new BambooLayer();
            assertThat(bambooLayer1).hasSameHashCodeAs(bambooLayer2);
        }

        @Test
        @DisplayName("should return a different hash code if the bamboo layers are not equal")
        void shouldReturnADifferentHashCodeIfTheBambooLayersAreNotEqual() {
            BambooLayer bambooLayer1 = new BambooLayer();
            BambooLayer bambooLayer2 = new BambooLayer();
            when(board.getTileAt(any())).thenReturn(mock(Tile.class));
            when(board.isTile(any())).thenReturn(true);
            when(board.isBambooGrowableAt(any())).thenReturn(true);
            bambooLayer1.growBamboo(new PositionVector(-1, 0, 1), board);
            assertThat(bambooLayer1).doesNotHaveSameHashCodeAs(bambooLayer2);
        }
    }

    @Nested
    @DisplayName("Method copy()")
    class TestCopy {
        @Test
        @DisplayName("should return a copy of the bamboo layer")
        void shouldReturnACopyOfTheBambooLayer() {
            BambooLayer bambooLayer1 = new BambooLayer();
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(mock(Tile.class));
            when(board.isBambooGrowableAt(any())).thenReturn(true);
            bambooLayer1.growBamboo(new PositionVector(-1, 0, 1), board);
            BambooLayer bambooLayer2 = bambooLayer1.copy();
            assertThat(bambooLayer1).isEqualTo(bambooLayer2);
        }
    }

    @Nested
    @DisplayName("Method isEatableAt()")
    class TestIsEatableAt {
        @Test
        @DisplayName("when it is a pond should return false")
        void whenItIsAPondShouldReturnFalse() {
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(mock(Tile.class));
            when(board.getTileAt(any()).getType()).thenReturn(TileType.POND);
            assertThat(bambooLayer.isEatableAt(new PositionVector(-1, 0, 1), board)).isFalse();
        }

        @Test
        @DisplayName("when the tile has an improvement Enclosure should return false")
        void whenTheTileHasAnImprovementEnclosureShouldReturnFalse() {
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(mock(Tile.class));

            when(board.getTileAt(any()).getImprovement())
                    .thenReturn(Optional.of(ImprovementType.ENCLOSURE));
            assertThat(bambooLayer.isEatableAt(new PositionVector(-1, 0, 1), board)).isFalse();
        }

        @Test
        @DisplayName(
                "when the tile is neither a pond nor has an improvement Enclosure should return"
                        + " true")
        void whenTheTileIsNeitherAPondNorHasAnImprovementEnclosureShouldReturnTrue() {
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(mock(Tile.class));
            when(board.getTileAt(any()).getImprovement()).thenReturn(Optional.empty());
            when(board.getBambooAt(any())).thenReturn(mock(LayerBambooStack.class));
            when(board.getBambooAt(any()).isEatable()).thenReturn(true);
            assertThat(bambooLayer.isEatableAt(new PositionVector(-1, 0, 1), board)).isTrue();
        }
    }

    @Nested
    @DisplayName("Method isGrowableAt()")
    class TestIsGrowableAt {
        @Test
        @DisplayName("when it is a pond should return false")
        void whenItIsAPondShouldReturnFalse() {
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(mock(Tile.class));
            when(board.getTileAt(any()).getType()).thenReturn(TileType.POND);
            assertThat(bambooLayer.isGrowableAt(new PositionVector(-1, 0, 1), board)).isFalse();
        }

        @Test
        @DisplayName("when the tile is not a growable tile should return false")
        void whenTheTileIsNotAGrowableTileShouldReturnFalse() {
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(mock(Tile.class));
            when(board.getTileAt(any()).getType()).thenReturn(mock(TileType.class));
            when(board.getBambooAt(any())).thenReturn(mock(LayerBambooStack.class));
            when(board.getBambooAt(any()).isGrowable()).thenReturn(false);
            assertThat(bambooLayer.isGrowableAt(new PositionVector(-1, 0, 1), board)).isFalse();
        }

        @Test
        @DisplayName(
                "when the tile is not a pond and is a growable tile and is irrigated should return"
                        + " true")
        void whenTheTileIsNotAPondAndIsAGrowableTileShouldReturnTrue() {
            when(board.isTile(any())).thenReturn(true);
            when(board.getTileAt(any())).thenReturn(mock(Tile.class));
            when(board.getTileAt(any()).getType()).thenReturn(mock(TileType.class));
            when(board.getBambooAt(any())).thenReturn(mock(LayerBambooStack.class));
            when(board.getBambooAt(any()).isGrowable()).thenReturn(true);
            when(board.isIrrigatedAt(any())).thenReturn(true);
            assertThat(bambooLayer.isGrowableAt(new PositionVector(-1, 0, 1), board)).isTrue();
        }
    }
}
