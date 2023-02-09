package com.takenoko.bot.utils.pathfinding.irrigation;

import com.takenoko.asset.IrrigationDeck;
import com.takenoko.engine.Board;
import com.takenoko.layers.irrigation.EdgePosition;
import com.takenoko.ui.ConsoleUserInterface;
import com.takenoko.vector.PositionVector;
import java.util.*;

public class IrrigationPathFinding {

    static final int ITERATION_LIMIT = 1000;
    static ConsoleUserInterface consoleUserInterface = new ConsoleUserInterface();

    private IrrigationPathFinding() {}

    private static int calculateHCost(
            Board board, List<PositionVector> tilesToIrrigate, EdgePosition placedEdgePosition) {
        if (placedEdgePosition == null) {
            return 1000000;
        }
        // get the sum of the distances between the tiles to irrigate and the placed edge position
        return tilesToIrrigate.stream()
                .filter(tile -> !board.isIrrigatedAt(tile))
                .mapToInt(
                        tile ->
                                (int)
                                        (placedEdgePosition.getLeftTilePosition().distance(tile)
                                                + placedEdgePosition
                                                        .getRightTilePosition()
                                                        .distance(tile)))
                .sum();
    }

    public static List<EdgePosition> getShortestIrrigationPath(
            List<PositionVector> tilesToIrrigate, Board board) {
        Queue<Node> open = new PriorityQueue<>(Comparator.comparingInt(Node::fCost));
        Set<Board> closed = new HashSet<>();
        open.add(new Node(board, 0, calculateHCost(board, tilesToIrrigate, null), null, null));
        while (!open.isEmpty()) {
            if (open.size() > ITERATION_LIMIT) {
                consoleUserInterface.displayError(
                        "Irrigation path finding iteration limit reached");
                return List.of();
            }

            Node current = open.poll();
            if (calculateHCost(current.board(), tilesToIrrigate, current.edgePosition()) == 0) {
                return rebuildPath(current);
            }
            if (current.board().getPlacedIrrigations().size() > IrrigationDeck.DEFAULT_SIZE) {
                closed.add(current.board());
                continue;
            }

            closed.add(current.board());
            for (EdgePosition edgePosition : current.board().getAvailableIrrigationPositions()) {
                Board copy = current.board().copy();
                copy.placeIrrigation(edgePosition);
                int g = 0;
                int h = calculateHCost(copy, tilesToIrrigate, edgePosition);
                Node child = new Node(copy, g, h, current, edgePosition);
                if (!closed.contains(copy) && !open.contains(child)) {
                    open.add(child);
                }
            }
        }
        return List.of();
    }

    private static List<EdgePosition> rebuildPath(Node node) {
        List<EdgePosition> path = new ArrayList<>();
        while (node != null) {
            if (node.edgePosition() != null) {
                path.add(0, node.edgePosition());
            }
            node = node.parent();
        }
        return path;
    }
}
