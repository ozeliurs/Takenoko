package com.takenoko.layers.irrigation;

import com.takenoko.vector.PositionVector;
import com.takenoko.vector.Vector;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/** This record represents an irrigation channel Position */
public class EdgePosition {
    private final Set<PositionVector> positions = new HashSet<>();

    public EdgePosition(
            @NotNull PositionVector leftTilePosition, @NotNull PositionVector rightTilePosition) {
        this(Set.of(leftTilePosition, rightTilePosition));
    }

    public EdgePosition(Set<PositionVector> positions) {
        this.positions.addAll(positions);
        if (positions.size() != 2) {
            throw new IllegalArgumentException("The irrigation channel position must be adjacent");
        }
        if (getLeftTilePosition().distance(getRightTilePosition()) != 1) {
            throw new IllegalArgumentException("The irrigation channel position must be adjacent");
        }
    }

    public PositionVector getLeftTilePosition() {
        return positions.stream().findFirst().orElseThrow();
    }

    public PositionVector getRightTilePosition() {
        return positions.stream().skip(1).findFirst().orElseThrow();
    }

    public EdgePosition copy() {
        return new EdgePosition(positions);
    }

    /**
     * Return the list of all the neighbours of the irrigation channel position A neighbour is a
     * position that is connected to the irrigation channel position
     *
     * @return the list of all the neighbours of the irrigation channel position
     */
    public List<EdgePosition> getNeighbours() {
        PositionVector leftTilePosition = getLeftTilePosition();
        PositionVector rightTilePosition = getRightTilePosition();
        HashSet<Vector> neighboursInCommon = new HashSet<>(leftTilePosition.getNeighbors());
        neighboursInCommon.retainAll(rightTilePosition.getNeighbors());
        return neighboursInCommon.stream()
                .map(
                        vector ->
                                List.of(
                                        new EdgePosition(
                                                leftTilePosition, vector.toPositionVector()),
                                        new EdgePosition(
                                                rightTilePosition, vector.toPositionVector())))
                .flatMap(List::stream)
                .toList();
    }

    public static List<EdgePosition> getEdgePositions(PositionVector positionVector) {
        return positionVector.getNeighbors().stream()
                .map(vector -> new EdgePosition(positionVector, vector.toPositionVector()))
                .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgePosition that = (EdgePosition) o;
        return Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positions);
    }

    @Override
    public String toString() {
        return "IrrigationChannelPosition{" + "positions=" + positions + '}';
    }
}
