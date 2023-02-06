package com.takenoko.bot.irrigation.pathfinding;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.takenoko.actors.Gardener;
import com.takenoko.actors.Panda;
import com.takenoko.asset.GameAssets;
import com.takenoko.asset.TileDeck;
import com.takenoko.engine.Board;
import com.takenoko.layers.bamboo.BambooLayer;
import com.takenoko.layers.irrigation.EdgePosition;
import com.takenoko.layers.irrigation.IrrigationLayer;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.Tile;
import com.takenoko.layers.tile.TileLayer;
import com.takenoko.vector.PositionVector;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class IrrigationPathFindingTest {
    @Nested
    @DisplayName("method getIrrigationToPlace")
    class GetIrrigationToPlace {
        @Test
        void integrationTestForIrrigationPathFinding() {
            Board board = new Board();
            PositionVector pos1 = new PositionVector(0, -1, 1);
            PositionVector pos2 = new PositionVector(1, -1, 0);
            PositionVector pos3 = new PositionVector(1, -2, 1);
            PositionVector pos4 = new PositionVector(2, -2, 0);
            PositionVector pos5 = new PositionVector(2, -3, 1);
            PositionVector pos6 = new PositionVector(3, -3, 0);

            List<PositionVector> positionVectorList =
                    List.of(pos1, pos2, pos3, pos4, pos5, pos6);

            for (PositionVector positionVector : positionVectorList) {
                board.drawTiles();
                board.placeTile(board.peekTileDeck().get(0), positionVector);
            }

            List<EdgePosition> positions =
                    IrrigationPathFinding.getShortestIrrigationPath(List.of(pos6), board);

            assertThat(positions).containsExactlyInAnyOrder(
                    new EdgePosition(pos1, pos2),
                    new EdgePosition(pos2, pos3),
                    new EdgePosition(pos3, pos4),
                    new EdgePosition(pos4, pos5),
                    new EdgePosition(pos5, pos6)
            );
        }

        @Test
        void integrationTestForIrrigationPathFinding2() {
            TileDeck tileDeck = mock(TileDeck.class);
            GameAssets gameAssets = mock(GameAssets.class);
            when(gameAssets.getTileDeck()).thenReturn(tileDeck);
            when(tileDeck.peek()).thenReturn(
                    List.of(new Tile(ImprovementType.WATERSHED))).thenReturn(
                    List.of(new Tile(ImprovementType.WATERSHED))).thenReturn(
                    List.of(new Tile()));

            Board board = new Board(
                    new TileLayer(),
                    mock(BambooLayer.class),
                    mock(Panda.class),
                    mock(Gardener.class),
                    gameAssets,
                    mock(IrrigationLayer.class)
            );
            PositionVector target = new PositionVector(-3, 4, -1);
            PositionVector pos1 = new PositionVector(-1, 0, 1);
            PositionVector pos2 = new PositionVector(-1, 1, 0);
            PositionVector pos3 = new PositionVector(-2, 1, 1);
            PositionVector pos4 = new PositionVector(-2, 2, 0);
            PositionVector pos5 = new PositionVector(-3, 2, 1);
            PositionVector pos6 = new PositionVector(-3, 3, 0);
            PositionVector pos7 = new PositionVector(-2, 3, -1);
            List<PositionVector> positionVectorList =
                    List.of(pos1, pos2, pos3, pos4, pos5, pos6, pos7, target);

            for (PositionVector positionVector : positionVectorList) {
                board.drawTiles();
                System.out.println(board.peekTileDeck().get(0));
                board.placeTile(board.peekTileDeck().get(0), positionVector);
            }

            // TODO: why doesn't it work when only specifying the target ?
            List<EdgePosition> positions =
                    IrrigationPathFinding.getShortestIrrigationPath(List.of(target), board);
            assertThat(positions)
                    .containsExactlyInAnyOrder(
                            new EdgePosition(pos1, pos2),
                            new EdgePosition(pos2, pos3),
                            new EdgePosition(pos3, pos4),
                            new EdgePosition(pos4, pos5),
                            new EdgePosition(pos4, pos6),
                            new EdgePosition(target, pos6));
        }
    }
}
