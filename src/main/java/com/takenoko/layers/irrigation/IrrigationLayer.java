package com.takenoko.layers.irrigation;

import com.takenoko.engine.Board;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class IrrigationLayer {
    private Map<PositionVector, Boolean> irrigation = new HashMap<>();
    private Set<EdgePosition> irrigationChannelsPositions = new HashSet<>();
    private HashSet<EdgePosition> availableEdgePositions = new HashSet<>();

    public IrrigationLayer() {
        irrigation.put(new PositionVector(0, 0, 0), true);
        (new PositionVector(0, 0, 0))
                .getNeighbors()
                .forEach(positionVector -> irrigation.put(positionVector.toPositionVector(), true));
    }

    public IrrigationLayer(IrrigationLayer irrigationLayer) {
        irrigation = new HashMap<>(irrigationLayer.irrigation);
        irrigationChannelsPositions = new HashSet<>(irrigationLayer.irrigationChannelsPositions);
        availableEdgePositions = new HashSet<>(irrigationLayer.availableEdgePositions);
    }

    public void placeIrrigation(EdgePosition edgePosition, Board board) {
        if (!availableEdgePositions.contains(edgePosition)) {
            throw new IllegalArgumentException("The irrigation channel position is not available");
        }
        irrigationChannelsPositions.add(edgePosition);
        irrigation.put(edgePosition.getLeftTilePosition(), true);
        irrigation.put(edgePosition.getRightTilePosition(), true);
        updateAvailableIrrigationChannelPositions(edgePosition, board);
    }

    private void updateAvailableIrrigationChannelPositions(EdgePosition edgePosition, Board board) {
        availableEdgePositions.remove(edgePosition);
        updateAvailableIrrigationChannelPositions(edgePosition.getNeighbours().stream(), board);
    }

    public void updateAvailableIrrigationChannelPositions(
            PositionVector positionOfTilePlaced, Board board) {
        // an irrigation channel can be placed if there is an irrigation channel connected to it or
        // if it is connected to the pond
        updateAvailableIrrigationChannelPositions(
                EdgePosition.getEdgePositions(positionOfTilePlaced).stream(), board);
    }

    private void updateAvailableIrrigationChannelPositions(
            Stream<EdgePosition> edgePositionStream, Board board) {
        edgePositionStream
                .filter(edgePosition -> !irrigationChannelsPositions.contains(edgePosition))
                .filter(
                        edgePosition ->
                                board.isTile(edgePosition.getLeftTilePosition())
                                        && board.isTile(edgePosition.getRightTilePosition()))
                .filter(
                        edgePosition ->
                                edgePosition.getNeighbours().stream()
                                        .anyMatch(
                                                o ->
                                                        irrigationChannelsPositions.contains(o)
                                                                || EdgePosition.getEdgePositions(
                                                                                new PositionVector(
                                                                                        0, 0, 0))
                                                                        .contains(o)))
                .filter(
                        edgePosition ->
                                !EdgePosition.getEdgePositions(new PositionVector(0, 0, 0))
                                        .contains(edgePosition))
                .forEach(availableEdgePositions::add);
    }

    public IrrigationLayer copy() {
        return new IrrigationLayer(this);
    }

    public Set<EdgePosition> getAvailableEdgePositions() {
        return new HashSet<>(availableEdgePositions);
    }

    public Set<EdgePosition> getIrrigationChannelsPositions() {
        return new HashSet<>(irrigationChannelsPositions);
    }

    public boolean isIrrigated(PositionVector positionVector) {
        return irrigation.get(positionVector);
    }
}
