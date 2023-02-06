package com.takenoko.bot.irrigation.pathfinding;

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
            List<PositionVector> positionVectorList =
                    List.of(
                            new PositionVector(0, -1, 1),
                            new PositionVector(1, -1, 0),
                            new PositionVector(1, -2, 1),
                            new PositionVector(2, -2, 0),
                            new PositionVector(2, -3, 1),
                            new PositionVector(3, -3, 0));

            for (PositionVector positionVector : positionVectorList) {
                board.drawTiles();
                board.placeTile(board.peekTileDeck().get(0), positionVector);
            }

            List<EdgePosition> positions =
                    IrrigationPathFinding.getShortestIrrigationPath(positionVectorList, board);
            assertNotNull(positions);
        }

        @Test
        void integrationTestForIrrigationPathFinding2() {}
    }
}
