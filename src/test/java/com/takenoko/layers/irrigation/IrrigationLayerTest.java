package com.takenoko.layers.irrigation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.takenoko.actors.Gardener;
import com.takenoko.actors.Panda;
import com.takenoko.asset.GameAssets;
import com.takenoko.engine.Board;
import com.takenoko.layers.bamboo.BambooLayer;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.layers.tile.TileLayer;
import com.takenoko.vector.PositionVector;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class IrrigationLayerTest {
    IrrigationLayer irrigationLayer;
    EdgePosition edgePosition;
    Board board;

    @BeforeEach
    void setUp() {
        irrigationLayer = new IrrigationLayer();
        edgePosition = mock(EdgePosition.class);
        board =
                spy(
                        new Board(
                                new TileLayer(),
                                new BambooLayer(),
                                new Panda(),
                                new Gardener(),
                                new GameAssets(),
                                irrigationLayer));
    }

    @Nested
    @DisplayName("Constructor")
    class TestConstructor {
        @Test
        @DisplayName("by default the pond is irrigated")
        void defaultIrrigation() {
            assertThat(irrigationLayer.isIrrigated(new PositionVector(0, 0, 0))).isTrue();
        }
    }

    @Nested
    @DisplayName("method placeIrrigation")
    class TestPlaceIrrigation {
        @Test
        @DisplayName("should throw an error when placing irrigation on a non existing position")
        void placeIrrigationShouldThrowError() {
            when(edgePosition.getLeftTilePosition()).thenReturn(new PositionVector(1, 0, -1));
            when(edgePosition.getRightTilePosition()).thenReturn(new PositionVector(1, 1, -2));
            assertThatThrownBy(() -> irrigationLayer.placeIrrigation(edgePosition, board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The irrigation channel position is not available");
        }

        @Test
        @DisplayName("When an irrigation is placed, the position isn't available anymore")
        void placeIrrigationShouldRemovePosition() {
            PositionVector pos1 = new PositionVector(1, -1, 0);
            PositionVector pos2 = new PositionVector(1, 0, -1);
            board.drawTiles();
            board.placeTile(board.peekTileDeck().get(0), pos1);
            board.placeTile(board.peekTileDeck().get(1), pos2);

            irrigationLayer.placeIrrigation(new EdgePosition(pos1, pos2), board);
            assertThat(irrigationLayer.getAvailableEdgePositions()).doesNotContain(edgePosition);
        }

        @Test
        @DisplayName("When an irrigation is placed, the irrigation map is updated")
        void placeIrrigationShouldUpdateIrrigationMap() {
            PositionVector pos1 = new PositionVector(0, 1, -1);
            PositionVector pos2 = new PositionVector(-1, 1, 0);
            board.drawTiles();
            board.placeTile(board.peekTileDeck().get(0), pos1);
            board.placeTile(board.peekTileDeck().get(1), pos2);

            irrigationLayer.placeIrrigation(new EdgePosition(pos1, pos2), board);
            assertThat(irrigationLayer.isIrrigated(pos1)).isTrue();
            assertThat(irrigationLayer.isIrrigated(pos2)).isTrue();
        }
    }

    @Nested
    @DisplayName("method updateAvailableIrrigationChannelPosition")
    class TestUpdateAvailableIrrigationChannelPosition {
        @Test
        @DisplayName("should be idempotent")
        void updateAvailableIrrigationChannelPosition_shouldBeIdempotent() {
            when(board.getTileAt(new PositionVector(-1, 0, 1)))
                    .thenReturn(new Tile(ImprovementType.NONE, TileColor.PINK));
            when(board.getTileAt(new PositionVector(0, -1, 1)))
                    .thenReturn(new Tile(ImprovementType.NONE, TileColor.PINK));
            when(board.getTileAt(new PositionVector(1, -1, 0)))
                    .thenReturn(new Tile(ImprovementType.NONE, TileColor.PINK));
            irrigationLayer.updateAvailableIrrigationChannelPositions(
                    new PositionVector(0, -1, 1), board);
            Set<EdgePosition> FIRSTavailableEdgePositions =
                    irrigationLayer.getAvailableEdgePositions();
            irrigationLayer.updateAvailableIrrigationChannelPositions(
                    new PositionVector(0, -1, 1), board);
            Set<EdgePosition> SECONDavailableEdgePositions =
                    irrigationLayer.getAvailableEdgePositions();
            assertThat(FIRSTavailableEdgePositions).isEqualTo(SECONDavailableEdgePositions);
        }
    }

    @Nested
    @DisplayName("method isIrrigated")
    class TestIsIrrigated {
        @Test
        @DisplayName("should return true for tile next to pond")
        void isIrrigated_shouldReturnTrueForTileNextToPond() {
            IrrigationLayer irrigationLayer = new IrrigationLayer();
            assertThat(irrigationLayer.isIrrigated(new PositionVector(0, -1, 1))).isTrue();
        }
    }
}
