package com.takenoko.layers.irrigation;

import com.takenoko.engine.Board;
import com.takenoko.vector.PositionVector;
import com.takenoko.vector.Vector;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class IrrigationLayer {
    Map<PositionVector, Boolean> irrigation = new HashMap<>();
    Map<IrrigationChannelPosition, IrrigationChannel> irrigationChannels = new HashMap<>();
    HashSet<IrrigationChannelPosition> availableIrrigationChannelPositions = new HashSet<>();

    public void irrigate(PositionVector positionVector) {
        irrigation.put(positionVector, true);
    }

    public void placeIrrigation(
            IrrigationChannelPosition irrigationChannelPosition,
            IrrigationChannel irrigationChannel,
            Board board) {
        if (!availableIrrigationChannelPositions.contains(irrigationChannelPosition)) {
            throw new IllegalArgumentException("The irrigation channel position is not available");
        }
        irrigationChannels.put(irrigationChannelPosition, irrigationChannel);
    }

    public void updateAvailableIrrigationChannelPositions(
            PositionVector positionOfTilePlaced, Board board) {
        // an irrigation channel can be placed if there is an irrigation channel connected to it or
        // if it is connected to the pond
        List<Vector> neighbours = positionOfTilePlaced.getNeighbors();
        for (Vector neighbour : neighbours) {
            if (irrigation.containsKey(neighbour.toPositionVector())) {
                availableIrrigationChannelPositions.add(
                        new IrrigationChannelPosition(
                                positionOfTilePlaced, neighbour.toPositionVector()));
            }
        }
    }
}
