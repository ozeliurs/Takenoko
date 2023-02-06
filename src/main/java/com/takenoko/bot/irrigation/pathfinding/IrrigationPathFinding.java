package com.takenoko.bot.irrigation.pathfinding;

import com.takenoko.engine.Board;
import com.takenoko.layers.irrigation.EdgePosition;
import com.takenoko.vector.PositionVector;
import java.util.*;

public class IrrigationPathFinding {

    private IrrigationPathFinding() {}

    private static int calculateHCost(Board board, List<PositionVector> tilesToIrrigate) {
        return tilesToIrrigate.stream().filter(tile -> !board.isIrrigatedAt(tile)).toList().size();
    }

    public static List<EdgePosition> getShortestIrrigationPath(
            List<PositionVector> tilesToIrrigate, Board board) {
        Queue<Node> open = new PriorityQueue<>(Comparator.comparingInt(Node::fCost));
        Set<Board> closed = new HashSet<>();
        open.add(new Node(board, 0, calculateHCost(board, tilesToIrrigate), null, null));
        while (!open.isEmpty()) {
            Node current = open.poll();
            if (calculateHCost(current.board(), tilesToIrrigate) == 0) {
                return rebuildPath(current);
            }
            closed.add(current.board());
            for (EdgePosition edgePosition : current.board().getAvailableIrrigationPositions()) {
                Board copy = current.board().copy();
                copy.placeIrrigation(edgePosition);
                int g = current.gCost() + 1;
                int h = calculateHCost(copy, tilesToIrrigate);
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
