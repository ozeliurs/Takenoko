package com.takenoko.layers.irrigation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.takenoko.actors.Gardener;
import com.takenoko.actors.Panda;
import com.takenoko.asset.GameAssets;
import com.takenoko.asset.TileDeck;
import com.takenoko.engine.Board;
import com.takenoko.stats.BoardStatistics;
import com.takenoko.layers.bamboo.BambooLayer;
import com.takenoko.layers.tile.*;
import com.takenoko.vector.PositionVector;
import com.takenoko.vector.Vector;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class IrrigationLayerTest {
    IrrigationLayer irrigationLayer;
    EdgePosition edgePosition;
    Board board;
    TileDeck tileDeck;

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
                                irrigationLayer,
                                new BoardStatistics()));
    }

    @Nested
    @DisplayName("Constructor")
    class TestConstructor {
        @Test
        @DisplayName("by default the pond is irrigated")
        void defaultIrrigation() {
            assertThat(irrigationLayer.isIrrigatedAt(new PositionVector(0, 0, 0))).isTrue();
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
            board.drawTiles();
            board.placeTile(board.peekTileDeck().get(0), pos2);

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
            board.drawTiles();
            board.placeTile(board.peekTileDeck().get(0), pos2);

            irrigationLayer.placeIrrigation(new EdgePosition(pos1, pos2), board);
            assertThat(irrigationLayer.isIrrigatedAt(pos1)).isTrue();
            assertThat(irrigationLayer.isIrrigatedAt(pos2)).isTrue();
        }
    }

    @Nested
    @DisplayName("method updateAvailableIrrigationChannelPosition")
    class TestUpdateAvailableIrrigationChannelPosition {
        @BeforeEach
        void setUp() {
            irrigationLayer = new IrrigationLayer();
            edgePosition = mock(EdgePosition.class);

            GameAssets gameAssets = mock(GameAssets.class);
            tileDeck = mock(TileDeck.class);
            when(gameAssets.getTileDeck()).thenReturn(tileDeck);

            board =
                    spy(
                            new Board(
                                    new TileLayer(),
                                    new BambooLayer(),
                                    new Panda(),
                                    new Gardener(),
                                    gameAssets,
                                    irrigationLayer,
                                    new BoardStatistics()));

            when(tileDeck.peek()).thenReturn(List.of(new Tile(), new Tile(), new Tile()));
        }

        private static Stream<Arguments> lifecycle() {
            return IntStream.range(0, 13).mapToObj(Arguments::of); // from 0 to 12
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("lifecycle")
        @DisplayName("Irrigation lifecycle")
        void irrigationLifecycle(int step) {
            // When the board is created no available edge positions
            assertThat(irrigationLayer.getAvailableEdgePositions()).isEmpty();
            if (step <= 1) return;

            // When tiles are placed near the pond, the edge positions are available
            board.drawTiles();
            board.placeTile(board.peekTileDeck().get(0), new PositionVector(1, -1, 0));
            assertThat(irrigationLayer.getAvailableEdgePositions()).isEmpty();

            board.drawTiles();
            board.placeTile(board.peekTileDeck().get(0), new PositionVector(1, 0, -1));
            assertThat(irrigationLayer.getAvailableEdgePositions())
                    .containsExactlyInAnyOrder(
                            new EdgePosition(
                                    new PositionVector(1, -1, 0), new PositionVector(1, 0, -1)));

            board.drawTiles();
            board.placeTile(board.peekTileDeck().get(0), new PositionVector(0, -1, 1));
            assertThat(irrigationLayer.getAvailableEdgePositions())
                    .containsExactlyInAnyOrder(
                            new EdgePosition(
                                    new PositionVector(1, -1, 0), new PositionVector(1, 0, -1)),
                            new EdgePosition(
                                    new PositionVector(1, -1, 0), new PositionVector(0, -1, 1)));

            board.drawTiles();
            board.placeTile(board.peekTileDeck().get(0), new PositionVector(-1, 0, 1));
            assertThat(irrigationLayer.getAvailableEdgePositions())
                    .containsExactlyInAnyOrder(
                            new EdgePosition(
                                    new PositionVector(1, -1, 0), new PositionVector(1, 0, -1)),
                            new EdgePosition(
                                    new PositionVector(1, -1, 0), new PositionVector(0, -1, 1)),
                            new EdgePosition(
                                    new PositionVector(0, -1, 1), new PositionVector(-1, 0, 1)));

            if (step <= 2) return;

            // Place irrigation on the edge position should remove them from the available edge
            // positions and does not add new ones
            irrigationLayer.placeIrrigation(
                    new EdgePosition(new PositionVector(0, -1, 1), new PositionVector(-1, 0, 1)),
                    board);
            assertThat(irrigationLayer.getAvailableEdgePositions())
                    .containsExactlyInAnyOrder(
                            new EdgePosition(
                                    new PositionVector(1, -1, 0), new PositionVector(1, 0, -1)),
                            new EdgePosition(
                                    new PositionVector(1, -1, 0), new PositionVector(0, -1, 1)));

            irrigationLayer.placeIrrigation(
                    new EdgePosition(new PositionVector(1, -1, 0), new PositionVector(1, 0, -1)),
                    board);
            assertThat(irrigationLayer.getAvailableEdgePositions())
                    .containsExactlyInAnyOrder(
                            new EdgePosition(
                                    new PositionVector(1, -1, 0), new PositionVector(0, -1, 1)));

            irrigationLayer.placeIrrigation(
                    new EdgePosition(new PositionVector(0, -1, 1), new PositionVector(1, -1, 0)),
                    board);
            assertThat(irrigationLayer.getAvailableEdgePositions()).isEmpty();

            if (step <= 3) return;

            // Assert that no available edge positions are available
            assertThat(irrigationLayer.getAvailableEdgePositions())
                    .doesNotContain(
                            new EdgePosition(
                                    new PositionVector(0, -1, 1), new PositionVector(1, -2, 1)),
                            new EdgePosition(
                                    new PositionVector(1, -1, 0), new PositionVector(1, -2, 1)));

            if (step <= 4) return;

            // When a new tile is placed, the available edge positions are updated
            board.drawTiles();
            board.placeTile(board.peekTileDeck().get(0), new PositionVector(1, -2, 1));
            assertThat(irrigationLayer.getAvailableEdgePositions())
                    .containsExactlyInAnyOrder(
                            new EdgePosition(
                                    new PositionVector(1, -1, 0), new PositionVector(1, -2, 1)),
                            new EdgePosition(
                                    new PositionVector(0, -1, 1), new PositionVector(1, -2, 1)));

            if (step <= 5) return;

            // When a tile is placed but no irrigation is placed, the available edge positions are
            // correctly updated
            board.drawTiles();
            board.placeTile(board.peekTileDeck().get(0), new PositionVector(0, -2, 2));
            assertThat(irrigationLayer.getAvailableEdgePositions())
                    .containsExactlyInAnyOrder(
                            new EdgePosition(
                                    new PositionVector(1, -1, 0), new PositionVector(1, -2, 1)),
                            new EdgePosition(
                                    new PositionVector(0, -1, 1), new PositionVector(1, -2, 1)));

            if (step <= 6) return;

            // When a tile is placed and irrigation is placed, the available edge positions are
            // correctly updated
            irrigationLayer.placeIrrigation(
                    new EdgePosition(new PositionVector(0, -1, 1), new PositionVector(1, -2, 1)),
                    board);
            assertThat(irrigationLayer.getAvailableEdgePositions())
                    .containsExactlyInAnyOrder(
                            new EdgePosition(
                                    new PositionVector(1, -1, 0), new PositionVector(1, -2, 1)),
                            new EdgePosition(
                                    new PositionVector(0, -1, 1), new PositionVector(0, -2, 2)),
                            new EdgePosition(
                                    new PositionVector(1, -2, 1), new PositionVector(0, -2, 2)));

            if (step <= 7) return;

            // Final irrigation pattern verification
            // POND
            assertThat(irrigationLayer.isIrrigatedAt(new PositionVector(0, 0, 0))).isTrue();

            if (step <= 8) return;

            // Pond neighbors
            for (Vector neighbor : (new PositionVector(0, 0, 0)).getNeighbors()) {
                assertThat(irrigationLayer.isIrrigatedAt(neighbor.toPositionVector())).isTrue();
            }

            if (step <= 9) return;

            // With irrigation
            System.out.println(board.getTileAt(new PositionVector(1, -2, 1)));
            assertThat(irrigationLayer.isIrrigatedAt(new PositionVector(1, -2, 1))).isTrue();

            if (step <= 10) return;

            // Not irrigated
            assertThat(irrigationLayer.isIrrigatedAt(new PositionVector(0, -2, 2))).isFalse();
            assertThat(irrigationLayer.isIrrigatedAt(new PositionVector(2, -2, 0))).isFalse();
            assertThat(irrigationLayer.isIrrigatedAt(new PositionVector(1, -3, 2))).isFalse();
            assertThat(irrigationLayer.isIrrigatedAt(new PositionVector(2, -1, -1))).isFalse();

            if (step <= 11) return;

            // When watershed tile is placed, it is irrigated
            when(tileDeck.peek()).thenReturn(List.of(new Tile(ImprovementType.WATERSHED)));

            Tile watershedTile = board.peekTileDeck().get(0);

            board.placeTile(watershedTile, new PositionVector(2, -1, -1));

            assertThat(watershedTile.getImprovement()).isPresent();
            assertThat(watershedTile.getImprovement()).contains(ImprovementType.WATERSHED);
            assertThat(irrigationLayer.isIrrigatedAt(new PositionVector(2, -1, -1))).isTrue();
        }
    }

    @Nested
    @DisplayName("method isIrrigated")
    class TestIsIrrigated {
        @Test
        @DisplayName("should return true for tile next to pond")
        void isIrrigated_shouldReturnTrueForTileNextToPond() {
            IrrigationLayer irrigationLayer = new IrrigationLayer();
            assertThat(irrigationLayer.isIrrigatedAt(new PositionVector(0, -1, 1))).isTrue();
        }
    }
}
