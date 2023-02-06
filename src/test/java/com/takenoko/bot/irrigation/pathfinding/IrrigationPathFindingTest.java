package com.takenoko.bot.irrigation.pathfinding;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.takenoko.engine.Board;
import com.takenoko.layers.irrigation.EdgePosition;
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
                    IrrigationPathFinding.getShortestIrrigationPath(positionVectorList, board);

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
            Board board = new Board();
            PositionVector target = new PositionVector(-2, 3, -1);
            PositionVector pos1 = new PositionVector(-1, 0, 1);
            PositionVector pos2 = new PositionVector(-1, 1, 0);
            PositionVector pos3 = new PositionVector(-2, 1, 1);
            PositionVector pos4 = new PositionVector(-2, 2, 0);
            PositionVector pos5 = new PositionVector(-3, 2, 1);
            PositionVector pos6 = new PositionVector(-3, 3, 0);
            List<PositionVector> positionVectorList =
                    List.of(pos1, pos2, pos3, pos4, pos5, pos6, target);

            for (PositionVector positionVector : positionVectorList) {
                board.drawTiles();
                board.placeTile(board.peekTileDeck().get(0), positionVector);
            }

            // TODO: why doesn't it work when only specifying the target ?
            List<EdgePosition> positions =
                    IrrigationPathFinding.getShortestIrrigationPath(positionVectorList, board);
            assertThat(positions)
                    .containsExactlyInAnyOrder(
                            new EdgePosition(pos1, pos2),
                            new EdgePosition(pos2, pos3),
                            new EdgePosition(pos3, pos4),
                            new EdgePosition(pos4, pos5),
                            new EdgePosition(pos4, pos6),
                            new EdgePosition(target, pos4));
        }
    }
}
